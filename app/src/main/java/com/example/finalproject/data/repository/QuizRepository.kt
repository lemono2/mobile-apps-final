package com.example.finalproject.data.repository

import com.example.finalproject.data.remote.safeApiCall
import com.example.finalproject.domain.model.QuizQuestion
import com.example.finalproject.domain.model.ScoreEntry
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.suspendCancellableCoroutine
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

interface QuizRepository {
    suspend fun getQuestions(): Result<List<QuizQuestion>>
    suspend fun getLeaderboard(): Result<List<ScoreEntry>>
    suspend fun submitScore(entry: ScoreEntry): Result<Unit>
}

@Singleton
class QuizRepositoryImpl @Inject constructor(
    private val database: FirebaseDatabase,
) : QuizRepository {

    override suspend fun getQuestions(): Result<List<QuizQuestion>> = safeApiCall {
        database.getReference("questions").awaitSnapshot().children.mapNotNull { child ->
            val question = child.child("question").getValue(String::class.java)
                ?: return@mapNotNull null
            QuizQuestion(
                id = child.key.orEmpty(),
                question = question,
                options = child.child("options").children
                    .mapNotNull { it.getValue(String::class.java) },
                correctIndex = child.child("correctIndex").getValue(Int::class.java) ?: 0,
                category = child.child("category").getValue(String::class.java).orEmpty(),
            )
        }
    }

    override suspend fun getLeaderboard(): Result<List<ScoreEntry>> = safeApiCall {
        database.getReference("leaderboard").awaitSnapshot().children.mapNotNull { child ->
            val name = child.child("name").getValue(String::class.java) ?: return@mapNotNull null
            ScoreEntry(
                name = name,
                score = child.child("score").getValue(Int::class.java) ?: 0,
                mode = child.child("mode").getValue(String::class.java).orEmpty(),
                timestamp = child.child("timestamp").getValue(Long::class.java) ?: 0L,
            )
        }.sortedByDescending { it.score }
    }

    override suspend fun submitScore(entry: ScoreEntry): Result<Unit> = safeApiCall {
        val value = mapOf(
            "name" to entry.name,
            "score" to entry.score,
            "mode" to entry.mode,
            "timestamp" to entry.timestamp,
        )
        database.getReference("leaderboard").push().setValueAwait(value)
    }
}

private suspend fun DatabaseReference.awaitSnapshot(): DataSnapshot =
    suspendCancellableCoroutine { cont ->
        val listener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) = cont.resume(snapshot)
            override fun onCancelled(error: DatabaseError) =
                cont.resumeWithException(error.toException())
        }
        addListenerForSingleValueEvent(listener)
        cont.invokeOnCancellation { removeEventListener(listener) }
    }

private suspend fun DatabaseReference.setValueAwait(value: Any) =
    suspendCancellableCoroutine { cont ->
        setValue(value) { error, _ ->
            if (error == null) cont.resume(Unit) else cont.resumeWithException(error.toException())
        }
    }

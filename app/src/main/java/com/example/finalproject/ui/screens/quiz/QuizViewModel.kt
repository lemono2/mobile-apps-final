package com.example.finalproject.ui.screens.quiz

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalproject.data.remote.toUserMessage
import com.example.finalproject.data.repository.QuizRepository
import com.example.finalproject.domain.model.QuizQuestion
import com.example.finalproject.domain.model.ScoreEntry
import com.example.finalproject.ui.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class QuizProgress(
    val currentIndex: Int = 0,
    val selectedIndex: Int? = null,
    val score: Int = 0,
    val isFinished: Boolean = false,
)

@HiltViewModel
class QuizViewModel @Inject constructor(
    private val repository: QuizRepository,
) : ViewModel() {

    private val _questions = MutableStateFlow<UiState<List<QuizQuestion>>>(UiState.Loading)
    val questions: StateFlow<UiState<List<QuizQuestion>>> = _questions.asStateFlow()

    private val _progress = MutableStateFlow(QuizProgress())
    val progress: StateFlow<QuizProgress> = _progress.asStateFlow()

    private val _submitted = MutableStateFlow(false)
    val submitted: StateFlow<Boolean> = _submitted.asStateFlow()

    private var loaded: List<QuizQuestion> = emptyList()

    init {
        load()
    }

    fun load() {
        viewModelScope.launch {
            _questions.value = UiState.Loading
            _progress.value = QuizProgress()
            _submitted.value = false
            repository.getQuestions().fold(
                onSuccess = { list ->
                    loaded = list
                    _questions.value = if (list.isEmpty()) UiState.Empty else UiState.Success(list)
                },
                onFailure = { _questions.value = UiState.Error(it.toUserMessage()) },
            )
        }
    }

    fun selectAnswer(index: Int) {
        val current = _progress.value
        if (current.selectedIndex != null) return
        val isCorrect = loaded.getOrNull(current.currentIndex)?.correctIndex == index
        _progress.value = current.copy(
            selectedIndex = index,
            score = if (isCorrect) current.score + 1 else current.score,
        )
    }

    fun next() {
        val current = _progress.value
        _progress.value = if (current.currentIndex + 1 >= loaded.size) {
            current.copy(isFinished = true)
        } else {
            current.copy(currentIndex = current.currentIndex + 1, selectedIndex = null)
        }
    }

    fun submitScore(name: String) {
        viewModelScope.launch {
            repository.submitScore(
                ScoreEntry(
                    name = name.trim().ifBlank { "Anonymous" },
                    score = _progress.value.score,
                    mode = "trivia",
                    timestamp = System.currentTimeMillis(),
                ),
            ).onSuccess { _submitted.value = true }
        }
    }

    fun restart() = load()
}

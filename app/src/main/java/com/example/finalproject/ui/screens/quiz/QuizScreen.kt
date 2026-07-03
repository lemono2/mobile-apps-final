package com.example.finalproject.ui.screens.quiz

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.finalproject.domain.model.QuizQuestion
import com.example.finalproject.ui.components.StateContent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuizScreen(
    modifier: Modifier = Modifier,
    viewModel: QuizViewModel = hiltViewModel(),
) {
    val questionsState by viewModel.questions.collectAsStateWithLifecycle()
    val progress by viewModel.progress.collectAsStateWithLifecycle()
    val submitted by viewModel.submitted.collectAsStateWithLifecycle()

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = { Text("Football Quiz") },
                colors = TopAppBarDefaults.topAppBarColors(
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
            )
        },
    ) { innerPadding ->
        StateContent(
            state = questionsState,
            onRetry = viewModel::load,
            modifier = Modifier.padding(innerPadding),
            emptyMessage = "No questions available.",
        ) { questions ->
            if (progress.isFinished) {
                QuizResult(
                    score = progress.score,
                    total = questions.size,
                    submitted = submitted,
                    onSubmit = viewModel::submitScore,
                    onRestart = viewModel::restart,
                )
            } else {
                QuizQuestionContent(
                    question = questions[progress.currentIndex],
                    questionNumber = progress.currentIndex + 1,
                    total = questions.size,
                    selectedIndex = progress.selectedIndex,
                    onSelect = viewModel::selectAnswer,
                    onNext = viewModel::next,
                )
            }
        }
    }
}

@Composable
private fun QuizQuestionContent(
    question: QuizQuestion,
    questionNumber: Int,
    total: Int,
    selectedIndex: Int?,
    onSelect: (Int) -> Unit,
    onNext: () -> Unit,
) {
    val answered = selectedIndex != null
    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp),
    ) {
        LinearProgressIndicator(
            progress = { questionNumber.toFloat() / total },
            modifier = Modifier
                .fillMaxWidth()
                .height(8.dp)
                .clip(RoundedCornerShape(8.dp)),
            color = MaterialTheme.colorScheme.primary,
            trackColor = MaterialTheme.colorScheme.surfaceVariant,
        )
        Text(
            text = "QUESTION $questionNumber OF $total",
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.secondary,
        )
        Card(
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant,
            ),
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text(
                text = question.question,
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.padding(20.dp),
            )
        }

        question.options.forEachIndexed { index, option ->
            val correct = index == question.correctIndex
            val container = when {
                !answered -> MaterialTheme.colorScheme.primary
                correct -> MaterialTheme.colorScheme.primary
                index == selectedIndex -> MaterialTheme.colorScheme.error
                else -> MaterialTheme.colorScheme.surfaceVariant
            }
            Button(
                onClick = { onSelect(index) },
                enabled = !answered,
                shape = RoundedCornerShape(14.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = container,
                    disabledContainerColor = container,
                    disabledContentColor = MaterialTheme.colorScheme.onPrimary,
                ),
                modifier = Modifier.fillMaxWidth().height(56.dp),
            ) { Text(option, style = MaterialTheme.typography.titleMedium) }
        }

        Spacer(Modifier.weight(1f))

        if (answered) {
            Button(
                onClick = onNext,
                shape = RoundedCornerShape(14.dp),
                modifier = Modifier.fillMaxWidth().height(52.dp),
            ) {
                Text(if (questionNumber == total) "See results" else "Next")
            }
        }
    }
}

@Composable
private fun QuizResult(
    score: Int,
    total: Int,
    submitted: Boolean,
    onSubmit: (String) -> Unit,
    onRestart: () -> Unit,
) {
    var name by rememberSaveable { mutableStateOf("") }
    Column(
        modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(20.dp),
    ) {
        Spacer(Modifier.height(16.dp))
        Text("QUIZ COMPLETE", style = MaterialTheme.typography.titleMedium, color = MaterialTheme.colorScheme.secondary)

        Box(
            modifier = Modifier
                .size(160.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.primaryContainer)
                .border(3.dp, MaterialTheme.colorScheme.primary, CircleShape),
            contentAlignment = Alignment.Center,
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "$score",
                    style = MaterialTheme.typography.displayLarge,
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Bold,
                )
                Text("out of $total", style = MaterialTheme.typography.bodyMedium)
            }
        }

        if (submitted) {
            Text(
                "Score submitted to the leaderboard",
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.secondary,
            )
        } else {
            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Your name") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
            )
            Button(
                onClick = { onSubmit(name) },
                enabled = name.isNotBlank(),
                shape = RoundedCornerShape(14.dp),
                modifier = Modifier.fillMaxWidth().height(52.dp),
            ) { Text("Submit score") }
        }

        OutlinedButton(
            onClick = onRestart,
            shape = RoundedCornerShape(14.dp),
            modifier = Modifier.fillMaxWidth().height(52.dp),
        ) {
            Text("Play again")
        }
    }
}

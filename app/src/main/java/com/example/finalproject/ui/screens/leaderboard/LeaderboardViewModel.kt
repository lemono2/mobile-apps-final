package com.example.finalproject.ui.screens.leaderboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalproject.data.remote.toUserMessage
import com.example.finalproject.data.repository.QuizRepository
import com.example.finalproject.domain.model.ScoreEntry
import com.example.finalproject.ui.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LeaderboardViewModel @Inject constructor(
    private val repository: QuizRepository,
) : ViewModel() {

    private val _state = MutableStateFlow<UiState<List<ScoreEntry>>>(UiState.Loading)
    val state: StateFlow<UiState<List<ScoreEntry>>> = _state.asStateFlow()

    init {
        load()
    }

    fun load() {
        viewModelScope.launch {
            _state.value = UiState.Loading
            repository.getLeaderboard().fold(
                onSuccess = { scores ->
                    _state.value = if (scores.isEmpty()) UiState.Empty else UiState.Success(scores)
                },
                onFailure = { _state.value = UiState.Error(it.toUserMessage()) },
            )
        }
    }
}

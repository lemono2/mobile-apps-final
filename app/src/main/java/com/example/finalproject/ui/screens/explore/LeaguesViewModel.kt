package com.example.finalproject.ui.screens.explore

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalproject.data.remote.toUserMessage
import com.example.finalproject.data.repository.ExploreRepository
import com.example.finalproject.domain.model.League
import com.example.finalproject.ui.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LeaguesViewModel @Inject constructor(
    private val repository: ExploreRepository,
) : ViewModel() {

    private val _state = MutableStateFlow<UiState<List<League>>>(UiState.Loading)
    val state: StateFlow<UiState<List<League>>> = _state.asStateFlow()

    init {
        load()
    }

    fun load() {
        viewModelScope.launch {
            _state.value = UiState.Loading
            repository.getLeagues().fold(
                onSuccess = { leagues ->
                    _state.value = if (leagues.isEmpty()) UiState.Empty else UiState.Success(leagues)
                },
                onFailure = { _state.value = UiState.Error(it.toUserMessage()) },
            )
        }
    }
}

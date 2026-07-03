package com.example.finalproject.ui.screens.explore

import android.net.Uri
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalproject.data.remote.toUserMessage
import com.example.finalproject.data.repository.ExploreRepository
import com.example.finalproject.domain.model.Team
import com.example.finalproject.ui.UiState
import com.example.finalproject.ui.navigation.ExploreArgs
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TeamsViewModel @Inject constructor(
    private val repository: ExploreRepository,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    val leagueName: String =
        Uri.decode(savedStateHandle.get<String>(ExploreArgs.LEAGUE_NAME).orEmpty())

    private val _state = MutableStateFlow<UiState<List<Team>>>(UiState.Loading)
    val state: StateFlow<UiState<List<Team>>> = _state.asStateFlow()

    init {
        load()
    }

    fun load() {
        viewModelScope.launch {
            _state.value = UiState.Loading
            repository.getTeams(leagueName).fold(
                onSuccess = { teams ->
                    _state.value = if (teams.isEmpty()) UiState.Empty else UiState.Success(teams)
                },
                onFailure = { _state.value = UiState.Error(it.toUserMessage()) },
            )
        }
    }
}

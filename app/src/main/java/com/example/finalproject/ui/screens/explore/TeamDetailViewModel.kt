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
class TeamDetailViewModel @Inject constructor(
    private val repository: ExploreRepository,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val teamId: String = savedStateHandle.get<String>(ExploreArgs.TEAM_ID).orEmpty()
    private val teamName: String =
        Uri.decode(savedStateHandle.get<String>(ExploreArgs.TEAM_NAME).orEmpty())

    private val _state = MutableStateFlow<UiState<Team>>(UiState.Loading)
    val state: StateFlow<UiState<Team>> = _state.asStateFlow()

    init {
        load()
    }

    fun load() {
        viewModelScope.launch {
            _state.value = UiState.Loading
            repository.getTeam(teamId, teamName).fold(
                onSuccess = { team ->
                    _state.value = if (team == null) UiState.Empty else UiState.Success(team)
                },
                onFailure = { _state.value = UiState.Error(it.toUserMessage()) },
            )
        }
    }
}

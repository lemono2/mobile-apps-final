package com.example.finalproject.ui.screens.explore

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.finalproject.domain.model.Team
import com.example.finalproject.ui.components.StateContent
import com.example.finalproject.ui.components.TeamBadge

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TeamDetailScreen(
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: TeamDetailViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = { Text("Team") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
            )
        },
    ) { innerPadding ->
        StateContent(
            state = state,
            onRetry = viewModel::load,
            modifier = Modifier.padding(innerPadding),
            emptyMessage = "Team details are unavailable.",
        ) { team ->
            TeamDetailContent(team)
        }
    }
}

@Composable
private fun TeamDetailContent(team: Team) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        TeamBadge(
            badgeUrl = team.badgeUrl,
            contentDescription = team.name,
            modifier = Modifier
                .fillMaxWidth()
                .height(160.dp),
        )
        Text(
            text = team.name,
            style = MaterialTheme.typography.headlineSmall,
            textAlign = TextAlign.Center,
        )
        if (team.league.isNotBlank()) {
            Text(text = team.league, style = MaterialTheme.typography.titleMedium)
        }
        if (team.stadium.isNotBlank()) {
            LabelValueRow(label = "Stadium", value = team.stadium)
        }
        if (team.description.isNotBlank()) {
            Text(
                text = team.description,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.fillMaxWidth(),
            )
        }
    }
}

@Composable
private fun LabelValueRow(label: String, value: String) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(text = label, style = MaterialTheme.typography.labelMedium)
        Text(text = value, style = MaterialTheme.typography.bodyLarge)
    }
}

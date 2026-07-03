package com.example.finalproject.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.SportsSoccer
import androidx.compose.ui.graphics.vector.ImageVector

enum class TopLevelDestination(
    val route: String,
    val label: String,
    val icon: ImageVector,
) {
    EXPLORE(route = "explore", label = "Explore", icon = Icons.Filled.Search),
    QUIZ(route = "quiz", label = "Quiz", icon = Icons.Filled.SportsSoccer),
    LEADERBOARD(route = "leaderboard", label = "Leaderboard", icon = Icons.AutoMirrored.Filled.List),
}

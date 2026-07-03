package com.example.finalproject.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.finalproject.ui.screens.leaderboard.LeaderboardScreen
import com.example.finalproject.ui.screens.quiz.QuizScreen

@Composable
fun AppNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    NavHost(
        navController = navController,
        startDestination = TopLevelDestination.EXPLORE.route,
        modifier = modifier,
    ) {
        exploreGraph(navController)
        composable(TopLevelDestination.QUIZ.route) { QuizScreen() }
        composable(TopLevelDestination.LEADERBOARD.route) { LeaderboardScreen() }
    }
}

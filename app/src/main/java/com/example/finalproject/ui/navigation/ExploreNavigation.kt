package com.example.finalproject.ui.navigation

import android.net.Uri
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.navArgument
import com.example.finalproject.ui.screens.explore.LeaguesScreen
import com.example.finalproject.ui.screens.explore.TeamDetailScreen
import com.example.finalproject.ui.screens.explore.TeamsScreen

object ExploreArgs {
    const val LEAGUE_NAME = "leagueName"
    const val TEAM_ID = "teamId"
    const val TEAM_NAME = "teamName"
}

private object ExploreRoutes {
    const val LEAGUES = "explore/leagues"
    const val TEAMS = "explore/teams/{${ExploreArgs.LEAGUE_NAME}}"
    const val TEAM_DETAIL = "explore/team/{${ExploreArgs.TEAM_ID}}/{${ExploreArgs.TEAM_NAME}}"

    fun teams(leagueName: String) = "explore/teams/${Uri.encode(leagueName)}"
    fun teamDetail(teamId: String, teamName: String) =
        "explore/team/$teamId/${Uri.encode(teamName)}"
}

fun NavGraphBuilder.exploreGraph(navController: NavHostController) {
    navigation(
        startDestination = ExploreRoutes.LEAGUES,
        route = TopLevelDestination.EXPLORE.route,
    ) {
        composable(ExploreRoutes.LEAGUES) {
            LeaguesScreen(
                onLeagueClick = { league ->
                    navController.navigate(ExploreRoutes.teams(league.name))
                },
            )
        }
        composable(
            route = ExploreRoutes.TEAMS,
            arguments = listOf(navArgument(ExploreArgs.LEAGUE_NAME) { type = NavType.StringType }),
        ) {
            TeamsScreen(
                onTeamClick = { team ->
                    navController.navigate(ExploreRoutes.teamDetail(team.id, team.name))
                },
                onBack = { navController.popBackStack() },
            )
        }
        composable(
            route = ExploreRoutes.TEAM_DETAIL,
            arguments = listOf(
                navArgument(ExploreArgs.TEAM_ID) { type = NavType.StringType },
                navArgument(ExploreArgs.TEAM_NAME) { type = NavType.StringType },
            ),
        ) {
            TeamDetailScreen(onBack = { navController.popBackStack() })
        }
    }
}

package com.example.finalproject.data.repository

import com.example.finalproject.data.mapper.toDomain
import com.example.finalproject.data.remote.SportsDbApi
import com.example.finalproject.data.remote.safeApiCall
import com.example.finalproject.domain.model.League
import com.example.finalproject.domain.model.Team
import javax.inject.Inject
import javax.inject.Singleton

interface ExploreRepository {
    suspend fun getLeagues(): Result<List<League>>
    suspend fun getTeams(leagueName: String): Result<List<Team>>
    suspend fun getTeam(teamId: String, teamName: String): Result<Team?>
}

@Singleton
class ExploreRepositoryImpl @Inject constructor(
    private val api: SportsDbApi,
) : ExploreRepository {

    override suspend fun getLeagues(): Result<List<League>> = safeApiCall {
        api.getAllLeagues().leagues
            .orEmpty()
            .filter { it.sport == "Soccer" }
            .mapNotNull { it.toDomain() }
    }

    override suspend fun getTeams(leagueName: String): Result<List<Team>> = safeApiCall {
        api.searchTeamsByLeague(leagueName).teams
            .orEmpty()
            .mapNotNull { it.toDomain() }
    }

    override suspend fun getTeam(teamId: String, teamName: String): Result<Team?> = safeApiCall {
        val matches = api.searchTeamsByName(teamName).teams.orEmpty().mapNotNull { it.toDomain() }
        matches.firstOrNull { it.id == teamId } ?: matches.firstOrNull()
    }
}

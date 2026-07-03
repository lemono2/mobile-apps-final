package com.example.finalproject.data.remote

import com.example.finalproject.data.remote.dto.LeaguesResponse
import com.example.finalproject.data.remote.dto.TeamsResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface SportsDbApi {

    @GET("all_leagues.php")
    suspend fun getAllLeagues(): LeaguesResponse

    @GET("search_all_teams.php")
    suspend fun searchTeamsByLeague(@Query("l") league: String): TeamsResponse

    @GET("searchteams.php")
    suspend fun searchTeamsByName(@Query("t") team: String): TeamsResponse
}

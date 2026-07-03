package com.example.finalproject.data.remote.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class LeaguesResponse(
    @Json(name = "leagues") val leagues: List<LeagueDto>?,
)

@JsonClass(generateAdapter = true)
data class LeagueDto(
    @Json(name = "idLeague") val id: String?,
    @Json(name = "strLeague") val name: String?,
    @Json(name = "strSport") val sport: String?,
)

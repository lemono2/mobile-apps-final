package com.example.finalproject.data.remote.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class TeamsResponse(
    @Json(name = "teams") val teams: List<TeamDto>?,
)

@JsonClass(generateAdapter = true)
data class TeamDto(
    @Json(name = "idTeam") val id: String?,
    @Json(name = "strTeam") val name: String?,
    @Json(name = "strBadge") val badge: String?,
    @Json(name = "strStadium") val stadium: String?,
    @Json(name = "strLeague") val league: String?,
    @Json(name = "strDescriptionEN") val description: String?,
)

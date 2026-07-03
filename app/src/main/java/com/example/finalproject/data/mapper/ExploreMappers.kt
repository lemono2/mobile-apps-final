package com.example.finalproject.data.mapper

import com.example.finalproject.data.remote.dto.LeagueDto
import com.example.finalproject.data.remote.dto.TeamDto
import com.example.finalproject.domain.model.League
import com.example.finalproject.domain.model.Team

fun LeagueDto.toDomain(): League? {
    val id = id ?: return null
    val name = name ?: return null
    return League(id = id, name = name)
}

fun TeamDto.toDomain(): Team? {
    val id = id ?: return null
    val name = name ?: return null
    return Team(
        id = id,
        name = name,
        badgeUrl = badge.orEmpty(),
        stadium = stadium.orEmpty(),
        league = league.orEmpty(),
        description = description.orEmpty(),
    )
}

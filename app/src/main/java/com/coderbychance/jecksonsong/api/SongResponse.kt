package com.coderbychance.jecksonsong.api

import com.coderbychance.jecksonsong.data.SongInfo

data class SongResponse(
    val resultCount: Int,
    val results: List<SongInfo>
)

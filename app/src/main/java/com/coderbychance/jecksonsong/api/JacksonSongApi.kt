package com.coderbychance.jecksonsong.api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface JacksonSongApi {
    companion object {
        const val BASE_URL = "https://itunes.apple.com/"
    }

    @GET("search")
    fun getSongList(@Query("term") term: String): Call<SongResponse>

}
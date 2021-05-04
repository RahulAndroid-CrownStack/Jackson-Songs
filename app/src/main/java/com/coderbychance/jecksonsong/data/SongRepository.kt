package com.coderbychance.jecksonsong.data

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.coderbychance.jecksonsong.api.JacksonSongApi
import com.coderbychance.jecksonsong.api.SongResponse
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

private const val TAG = "SongRepository"

@Singleton
class SongRepository @Inject constructor(private val jacksonSongApi: JacksonSongApi) {

    fun getSongsResult(query: String): MutableLiveData<SongResponse>? {
        var mutableLiveData:MutableLiveData<SongResponse>? = MutableLiveData<SongResponse>()
        jacksonSongApi.getSongList(query).enqueue(object : Callback<SongResponse> {
            override fun onResponse(call: Call<SongResponse>, response: Response<SongResponse>) {

                Log.d(TAG, "onResponse: "+Gson().toJson(response.body()))
                mutableLiveData!!.value = response.body()
            }

            override fun onFailure(call: Call<SongResponse>, t: Throwable) {
                mutableLiveData = null
                Log.e(TAG, "onFailure: ", t)
            }
        })

        return mutableLiveData
    }
}
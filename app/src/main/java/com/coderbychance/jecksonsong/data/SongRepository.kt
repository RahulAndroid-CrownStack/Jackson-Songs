package com.coderbychance.jecksonsong.data

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.coderbychance.jecksonsong.api.JacksonSongApi
import com.coderbychance.jecksonsong.api.SongResponse
import com.google.gson.Gson
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

private const val TAG = "SongRepository"

@Singleton
class SongRepository @Inject constructor(private val jacksonSongApi: JacksonSongApi) {

    fun getSongsResult(query: String): MutableLiveData<SongResponse>? {
        var mutableLiveData: MutableLiveData<SongResponse>? = MutableLiveData<SongResponse>()
        jacksonSongApi.getSongList(query).enqueue(object : Callback<SongResponse> {
            override fun onResponse(call: Call<SongResponse>, response: Response<SongResponse>) {
                mutableLiveData!!.value = response.body()
            }

            override fun onFailure(call: Call<SongResponse>, t: Throwable) {
                mutableLiveData = null
            }
        })

        return mutableLiveData
    }



    fun getSongs(query: String): Observable<SongResponse> {
        return jacksonSongApi.getSongListRx(query)
    }
}
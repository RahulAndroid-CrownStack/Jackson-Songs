package com.coderbychance.jecksonsong.ui.song_list

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.coderbychance.jecksonsong.api.SongResponse
import com.coderbychance.jecksonsong.data.SongRepository
import com.google.gson.Gson
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class SongViewModel @ViewModelInject constructor(private val songRepository: SongRepository) :
    ViewModel() {
    //aa

    var songsData: MutableLiveData<SongResponse> = MutableLiveData<SongResponse>()

    fun getSongsList(query: String) = songRepository.getSongsResult(query) as LiveData<SongResponse>

    fun getSongsObserver(query: String): MutableLiveData<SongResponse> {
        getSongsDataFromApi(query)
        return songsData
    }

    private fun getSongsDataFromApi(query: String) {
        songRepository.getSongs(query)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(getSongsObserverRx())
    }

    private fun getSongsObserverRx(): Observer<SongResponse> {
        return object : Observer<SongResponse> {
            override fun onSubscribe(d: Disposable?) {

            }
            override fun onNext(value: SongResponse?) {
                songsData.postValue(value)
            }

            override fun onError(e: Throwable?) {
                songsData.postValue(null)
            }

            override fun onComplete() {

            }
        }
    }

}


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
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class SongViewModel @ViewModelInject constructor(private val songRepository: SongRepository) :
    ViewModel() {
    //aa

    var songsData: MutableLiveData<SongResponse> = MutableLiveData<SongResponse>()

    private val disposableList: CompositeDisposable = CompositeDisposable()

    fun getSongsList(query: String) = songRepository.getSongsResult(query) as LiveData<SongResponse>

    fun getSongsObserver(query: String): MutableLiveData<SongResponse> {
        getSongsDataFromApi(query)
        return songsData
    }

    private fun getSongsDataFromApi(query: String) {
        val disposable = songRepository.getSongs(query)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({value->
                songsData.postValue(value)
            },
            {
                songsData.postValue(null)
            })
        disposableList.add(disposable)
    }


    override fun onCleared() {
        super.onCleared()
        disposableList.clear()
    }

}


package com.coderbychance.jecksonsong.ui.song_list

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.coderbychance.jecksonsong.api.SongResponse
import com.coderbychance.jecksonsong.data.SongRepository

class SongViewModel @ViewModelInject constructor(private val songRepository: SongRepository):ViewModel() {
    fun getSongsList(query: String) = songRepository.getSongsResult(query) as LiveData<SongResponse>
}
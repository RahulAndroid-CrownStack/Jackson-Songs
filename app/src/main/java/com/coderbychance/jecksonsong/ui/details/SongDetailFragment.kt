package com.coderbychance.jecksonsong.ui.details

import android.os.Bundle
import android.text.Html
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.coderbychance.jecksonsong.R
import com.coderbychance.jecksonsong.databinding.FragmentSongDetailBinding

class SongDetailFragment : Fragment(R.layout.fragment_song_detail) {
    private val args by navArgs<SongDetailFragmentArgs>()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentSongDetailBinding.bind(view)
        val songInfo = args.song



        Glide.with(requireContext()).load(songInfo.artworkUrl100)
            .placeholder(R.drawable.music_placeholder).into(binding.imageCover)

        binding.apply {
            textCollectionName.text = songInfo.collectionName
            textArtistName.text = songInfo.artistName
            textReleaseDate.text = songInfo.releaseDate
            textPrice.text = String.format("%s %s", songInfo.collectionPrice, songInfo.currency)
            textDescription.text = songInfo.description
        }

    }
}
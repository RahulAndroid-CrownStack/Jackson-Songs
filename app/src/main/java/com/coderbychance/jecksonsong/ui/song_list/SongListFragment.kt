package com.coderbychance.jecksonsong.ui.song_list

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.coderbychance.jecksonsong.R
import com.coderbychance.jecksonsong.api.SongResponse
import com.coderbychance.jecksonsong.data.SongInfo
import com.coderbychance.jecksonsong.databinding.FragmentSongListBinding
import dagger.hilt.android.AndroidEntryPoint

private const val TAG = "SongListFragment"

@AndroidEntryPoint
class SongListFragment : Fragment(R.layout.fragment_song_list),
    SongListAdapter.OnItemClickListener {

    private val viewModel by viewModels<SongViewModel>()
    private var _binding: FragmentSongListBinding? = null
    private lateinit var songListAdapter: SongListAdapter
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentSongListBinding.bind(view)

        Log.d(TAG, "onViewCreated: here")

        songListAdapter = SongListAdapter(this)
        binding.apply {
            recyclerView.adapter = songListAdapter
        }
        getSongs("Michael+jackson")
    }

    private fun getSongs(query: String) {
        val mQuery = query.replace(" ", "+")

        binding.recyclerView.visibility = View.GONE
        binding.progressBar.visibility = View.VISIBLE

        viewModel.getSongsObserver(mQuery).observe(viewLifecycleOwner) { response ->
            if (response != null && response.results.isNotEmpty()) {
               setData(response)
            }
        }

    }

    private fun setData(response: SongResponse) {
        binding.recyclerView.isVisible = (response.results.isNotEmpty())
        binding.textViewEmpty.isVisible = (response.results.isEmpty())

        binding.progressBar.visibility = View.GONE
        if (response.results.isNotEmpty()) {
            songListAdapter.submitList(response.results)
        }
    }

    override fun onItemClicked(songInfo: SongInfo) {
        val action = SongListFragmentDirections.actionSongListFragmentToSongDetailFragment(songInfo)
        findNavController().navigate(action)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
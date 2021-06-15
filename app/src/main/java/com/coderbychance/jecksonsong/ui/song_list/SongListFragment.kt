package com.coderbychance.jecksonsong.ui.song_list

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.coderbychance.jecksonsong.R
import com.coderbychance.jecksonsong.api.SongResponse
import com.coderbychance.jecksonsong.data.SongInfo
import com.coderbychance.jecksonsong.databinding.FragmentSongListBinding
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.Observable
import io.reactivex.ObservableOnSubscribe
import io.reactivex.Observer
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

private const val TAG = "SongListFragment"

@AndroidEntryPoint
class SongListFragment : Fragment(R.layout.fragment_song_list),
    SongListAdapter.OnItemClickListener {

    private val viewModel by viewModels<SongViewModel>()
    private var _binding: FragmentSongListBinding? = null
    private lateinit var songListAdapter: SongListAdapter
    private val binding get() = _binding!!

    private val compositeDisposable = CompositeDisposable()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentSongListBinding.bind(view)

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


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.search_data, menu)

        val searchItem = menu.findItem(R.id.action_search)
        val searchView = searchItem.actionView as SearchView
        createSearchOperation(searchView)
    }

    private fun createSearchOperation(searchView: SearchView) {
        val observableQueryText = Observable
            .create(ObservableOnSubscribe<String>
            { emitter ->
                searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                    override fun onQueryTextSubmit(query: String?) = false

                    override fun onQueryTextChange(newText: String?): Boolean {
                        if (!emitter.isDisposed) {
                            emitter.onNext(newText!!)
                        }
                        return false
                    }
                })
            })
            .debounce(500, TimeUnit.MILLISECONDS)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

        subscribeObserver(observableQueryText!!)
    }

    private fun subscribeObserver(observableQueryText: Observable<String>) {
        observableQueryText.subscribe(object:Observer<String>{
            override fun onSubscribe(d: Disposable) {
                compositeDisposable.add(d)
                Log.d(TAG, "onSubscribe: ")
            }

            override fun onNext(t: String) {
                if (t.trim().isNotEmpty()){
                    getSongs(t)
                }
            }

            override fun onError(e: Throwable) {
                Log.d(TAG, "onError: ${e.localizedMessage}")
            }

            override fun onComplete() {
                Log.d(TAG, "onComplete: ")
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.clear()
    }
}
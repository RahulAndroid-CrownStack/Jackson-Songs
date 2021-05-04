package com.coderbychance.jecksonsong.ui.song_list

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.coderbychance.jecksonsong.R
import com.coderbychance.jecksonsong.data.SongInfo
import com.coderbychance.jecksonsong.databinding.ItemSongListBinding


class SongListAdapter(private val listener: OnItemClickListener) :
    ListAdapter<SongInfo, SongListAdapter.SongInfoHolder>(Difference()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongInfoHolder {
        val binding =
            ItemSongListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SongInfoHolder(binding, parent.context)
    }

    override fun onBindViewHolder(holder: SongInfoHolder, position: Int) {
        val currentItem = getItem(position)
        holder.bind(currentItem)
    }

    inner class SongInfoHolder(
        private val binding: ItemSongListBinding,
        private val context: Context
    ) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val item = getItem(position)
                    if (item != null) {
                        listener.onItemClicked(item)
                    }
                }
            }
        }

        fun bind(songInfo: SongInfo) {
            binding.apply {
                Glide.with(context).load(songInfo.artworkUrl60)
                    .placeholder(R.drawable.music_placeholder).into(imagePoster)
                textAlbumName.text = songInfo.collectionName
                textPrice.text = String.format("%s %s", songInfo.collectionPrice, songInfo.currency)
                textDesc.text = songInfo.artistName
            }
        }

    }

    class Difference : DiffUtil.ItemCallback<SongInfo>() {
        override fun areItemsTheSame(oldItem: SongInfo, newItem: SongInfo) = oldItem == newItem

        override fun areContentsTheSame(oldItem: SongInfo, newItem: SongInfo) =
            oldItem.collectionId == newItem.collectionId
    }

    interface OnItemClickListener {
        fun onItemClicked(songInfo: SongInfo)
    }

}



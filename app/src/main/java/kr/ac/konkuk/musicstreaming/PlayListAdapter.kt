package kr.ac.konkuk.musicstreaming

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kr.ac.konkuk.musicstreaming.databinding.ItemMusicBinding
import kr.ac.konkuk.musicstreaming.service.MusicModel

class PlayListAdapter(private val callback: (MusicModel) -> Unit): ListAdapter<MusicModel, PlayListAdapter.ViewHolder>(diffUtil) {

    inner class ViewHolder(private val binding: ItemMusicBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(musicModel: MusicModel) {
            val trackTextView = binding.itemTrackTextView
            val artistTextView = binding.itemArtistTextView
            val coverImageView = binding.itemCoverImageView

            trackTextView.text = musicModel.track
            artistTextView.text = musicModel.artist

            Glide.with(coverImageView.context)
                .load(musicModel.coverUrl)
                .into(coverImageView)

            if (musicModel.isPlaying) {
                itemView.setBackgroundColor(Color.GRAY)
            } else {
                //투명한 색
                itemView.setBackgroundColor(Color.TRANSPARENT)
            }

            itemView.setOnClickListener {
                callback(musicModel)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemMusicBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        currentList[position].also { musicModel ->
            holder.bind(musicModel)
        }
    }

    companion object {
        val diffUtil = object: DiffUtil.ItemCallback<MusicModel>() {
            override fun areItemsTheSame(oldItem: MusicModel, newItem: MusicModel): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: MusicModel, newItem: MusicModel): Boolean {
                return oldItem == newItem
            }
        }
    }
}
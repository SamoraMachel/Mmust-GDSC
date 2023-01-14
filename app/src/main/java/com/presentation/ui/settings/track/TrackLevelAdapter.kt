package com.presentation.ui.settings.track

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.test.mmustgdsc.R
import com.test.mmustgdsc.databinding.SingleResourceLevelLayoutBinding

class TrackLevelAdapter(private var tracks : List<Pair<String, String>>) : RecyclerView.Adapter<TrackLevelAdapter.TrackLevelViewHolder>() {
    class TrackLevelViewHolder(itemView : View) : ViewHolder(itemView) {
        private val binding : SingleResourceLevelLayoutBinding = SingleResourceLevelLayoutBinding.bind(itemView)

        fun setup(title: String, description: String) {
            binding.levelTitle.text = title
            binding.levelDescription.text = description
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrackLevelViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context).inflate(R.layout.single_resource_level_layout, parent, false)
        return TrackLevelViewHolder(layoutInflater)
    }

    override fun onBindViewHolder(holder: TrackLevelViewHolder, position: Int) {
        val trackData = tracks[position]
        holder.setup(trackData.first, trackData.second)
    }

    override fun getItemCount(): Int {
        return tracks.size
    }

    fun addTrack(title: String, description: String) {
        val trackList = mutableListOf<Pair<String, String>>()
        trackList.addAll(tracks)
        trackList.add(Pair(title, description))
        tracks = trackList
        notifyItemInserted(tracks.size - 1)
    }

    fun removeTrack(title: String) {
        val trackList = mutableListOf<Pair<String, String>>()
        trackList.addAll(tracks)
        tracks.forEach {
            if (it.first == title) {
                trackList.remove(it)
                tracks = trackList
                notifyItemRemoved(tracks.indexOf(it))
                return
            }
        }
    }
}
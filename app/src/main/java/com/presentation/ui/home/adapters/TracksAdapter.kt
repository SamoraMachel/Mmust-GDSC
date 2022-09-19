package com.presentation.ui.home.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.presentation.models.TrackPresentation
import com.presentation.ui.home.TrackFragmentDirections
import com.test.mmustgdsc.R
import com.test.mmustgdsc.databinding.SingleTrackLayoutBinding


class TracksAdapter(private val tracks : List<TrackPresentation>) : RecyclerView.Adapter<TracksAdapter.ViewHolder>(){
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding = SingleTrackLayoutBinding.bind(itemView)

        fun setup(data: TrackPresentation) {
            Glide.with(binding.root.context)
                .load(data.image)
                .into(binding.trackImage)

            binding.trackTitle.text = data.title
            binding.trackDescription.text = data.description
            binding.trackCard.setOnClickListener {
                openResourceLevel(data)
            }
        }

        private fun openResourceLevel(data : TrackPresentation) {
            val navController = binding.root.findNavController()
            val action = TrackFragmentDirections.actionTrackFragmentToResourceLevelFragment(data)
            navController.navigate(action)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
         val inflater = LayoutInflater.from(parent.context).inflate(R.layout.single_track_layout, parent, false)
        return ViewHolder(inflater)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val track = tracks[position]
        holder.setup(track)
    }

    override fun getItemCount(): Int {
        return tracks.size
    }
}
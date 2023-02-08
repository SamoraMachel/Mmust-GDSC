package com.presentation.ui.home.adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.presentation.models.LevelPresentation
import com.presentation.ui.home.ResourceLevelFragmentDirections
import com.test.mmustgdsc.R
import com.test.mmustgdsc.databinding.SingleResourceLevelLayoutBinding


var color_counter = 0
class ResourceLevelAdapter(
    private val levels : List<LevelPresentation>,
    private val track : String
) : RecyclerView.Adapter<ResourceLevelAdapter.ViewHolder>() {



    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val binding = SingleResourceLevelLayoutBinding.bind(itemView)

        val _colors = arrayListOf(
            R.color.flat_purple,
            R.color.flat_yellow,
            R.color.flat_green
        )

        fun setup(level : LevelPresentation, track: String) {
            binding.levelTitle.text = level.title
            binding.levelDescription.text = level.description

            if (color_counter > 2) {
                color_counter = 0
            }
            binding.levelCard.setBackgroundColor(binding.root.resources.getColor(_colors[color_counter]))
            if (color_counter == 1) {
                binding.levelTitle.setTextColor(binding.root.resources.getColor(R.color.flat_gray))
                binding.levelDescription.setTextColor(binding.root.resources.getColor(R.color.flat_gray))
            } else {
                binding.levelTitle.setTextColor(Color.WHITE)
                binding.levelDescription.setTextColor(Color.WHITE)
            }

            color_counter += 1
//            if(level.title.contains("Beginner")) {
//                binding.levelCard.setCardBackgroundColor(binding.root.resources.getColor(R.color.flat_purple))
//
//            } else if(level.title.contains("Intermediate")) {
//                binding.levelCard.setCardBackgroundColor(binding.root.resources.getColor(R.color.flat_yellow))
//                binding.levelTitle.setTextColor(binding.root.resources.getColor(R.color.flat_gray))
//                binding.levelDescription.setTextColor(binding.root.resources.getColor(R.color.flat_gray))
//            } else if(level.title.contains("Expert")) {
//                binding.levelCard.setCardBackgroundColor(binding.root.resources.getColor(R.color.flat_green))
//                binding.levelTitle.setTextColor(Color.WHITE)
//                binding.levelDescription.setTextColor(Color.WHITE)
//            }

            binding.levelCard.setOnClickListener {
                openResource(level, track)
            }
        }

        private fun openResource(level : LevelPresentation, track : String) {
            val navController = binding.root.findNavController()
            val action = ResourceLevelFragmentDirections.actionResourceLevelFragmentToResourceFragment(level, track)
            navController.navigate(action)
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ResourceLevelAdapter.ViewHolder {
        val inflater = LayoutInflater.from(parent.context).inflate(R.layout.single_resource_level_layout, parent, false)
        return ViewHolder(inflater)
    }

    override fun onBindViewHolder(holder: ResourceLevelAdapter.ViewHolder, position: Int) {
        val level = levels[position]

        holder.setup(level, track)
    }

    override fun getItemCount(): Int {
        return levels.size
    }
}
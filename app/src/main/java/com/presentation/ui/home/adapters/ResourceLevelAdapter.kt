package com.presentation.ui.home.adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.presentation.models.LevelPresentation
import com.test.mmustgdsc.R
import com.test.mmustgdsc.databinding.SingleResourceLevelLayoutBinding

class ResourceLevelAdapter(
    private val levels : List<LevelPresentation>
) : RecyclerView.Adapter<ResourceLevelAdapter.ViewHolder>() {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val binding = SingleResourceLevelLayoutBinding.bind(itemView)

        fun setup(level : LevelPresentation) {
            binding.levelTitle.text = level.title
            binding.levelDescription.text = level.description

            if(level.title.contains("Beginner")) {
                binding.levelCard.setCardBackgroundColor(binding.root.resources.getColor(R.color.flat_purple))
                binding.levelTitle.setTextColor(Color.WHITE)
                binding.levelDescription.setTextColor(Color.WHITE)
            } else if(level.title.contains("Intermediate")) {
                binding.levelCard.setCardBackgroundColor(binding.root.resources.getColor(R.color.flat_yellow))
                binding.levelTitle.setTextColor(binding.root.resources.getColor(R.color.flat_gray))
                binding.levelDescription.setTextColor(binding.root.resources.getColor(R.color.flat_gray))
            } else if(level.title.contains("Expert")) {
                binding.levelCard.setCardBackgroundColor(binding.root.resources.getColor(R.color.flat_green))
                binding.levelTitle.setTextColor(Color.WHITE)
                binding.levelDescription.setTextColor(Color.WHITE)
            }
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

        holder.setup(level)
    }

    override fun getItemCount(): Int {
        return levels.size
    }
}
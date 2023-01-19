package com.presentation.ui.settings.track

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.presentation.models.ProfilePresentation
import com.test.mmustgdsc.R
import com.test.mmustgdsc.databinding.LeadSpinnerItemBinding

class LeadChoiceAdapter : ListAdapter<ProfilePresentation, LeadChoiceAdapter.ChoiceViewHolder>(DiffUtilCallback()) {


    class DiffUtilCallback() : DiffUtil.ItemCallback<ProfilePresentation>() {
        override fun areItemsTheSame(
            oldItem: ProfilePresentation,
            newItem: ProfilePresentation
        ): Boolean {
            return oldItem.name == newItem.name && oldItem.description == newItem.description
        }

        override fun areContentsTheSame(
            oldItem: ProfilePresentation,
            newItem: ProfilePresentation
        ): Boolean {
            return oldItem == newItem
        }
    }

    class ChoiceViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = LeadSpinnerItemBinding.bind(itemView)

        fun setupView(image: String, name: String) {
            binding.title.text = name
            Glide.with(itemView.context)
                .load(image)
                .into(binding.icon)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChoiceViewHolder {
        val inflater = LayoutInflater.from(parent.context).inflate(R.layout.lead_spinner_item, parent, false)
        return ChoiceViewHolder(inflater)
    }

    override fun onBindViewHolder(holder: ChoiceViewHolder, position: Int) {
        val leadObj = getItem(position)
        holder.setupView(leadObj.profileImage, leadObj.name)
    }
}
package com.presentation.ui.home.adapters

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.presentation.models.ResourcePresentation
import com.test.mmustgdsc.R
import com.test.mmustgdsc.databinding.SingleResourceLayoutBinding

class ResourceAdapter(
    private val resources: List<ResourcePresentation>
) : RecyclerView.Adapter<ResourceAdapter.ViewHolder>() {
    class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        val binding = SingleResourceLayoutBinding.bind(itemView)

        fun setup(resource : ResourcePresentation, action : (url: Uri, context: Context) -> Unit) {
            Glide.with(binding.root.context)
                .load(resource.image)
                .into(binding.resourceImage)
            binding.resourceTitle.text = resource.title
            binding.resourceDescription.text = resource.description
            binding.resourceCard.setOnClickListener { action(Uri.parse(resource.link), binding.root.context) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ResourceAdapter.ViewHolder {
        val inflater = LayoutInflater.from(parent.context).inflate(R.layout.single_resource_layout, parent, false)
        return ViewHolder(inflater)
    }

    override fun onBindViewHolder(holder: ResourceAdapter.ViewHolder, position: Int) {
        val resource = resources[position]
        holder.setup(resource) { url, context ->
            openView(url, context)
        }
    }

    override fun getItemCount(): Int {
        return resources.size
    }

    private fun openView(url : Uri, context: Context) {
        val intent = Intent(Intent.ACTION_VIEW, url)
        context.startActivity(intent)
    }
}
package com.presentation.ui.members.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.presentation.models.ProfilePresentation
import com.presentation.ui.members.MembersFragmentDirections
import com.app.mmustgdsc.R
import com.app.mmustgdsc.databinding.SingleMemberLayoutBinding

class MembersAdapter(
    private val members : List<ProfilePresentation>
) : RecyclerView.Adapter<MembersAdapter.ViewHolder>() {
    class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        val binding = SingleMemberLayoutBinding.bind(itemView)

        fun setup(member : ProfilePresentation) {
            Glide.with(binding.root.context)
                .load(member.profileImage)
                .into(binding.memberImage)

            binding.memberUsername.text = member.name
            binding.memberProfession.text = member.profession
            binding.memberRole.text = member.title
            binding.memberCard.setOnClickListener { openProfile(member) }
        }

        private fun openProfile(member : ProfilePresentation) {
            val navController = binding.root.findNavController()
            val action = MembersFragmentDirections.actionMembersFragmentToProfileFragment(member)
            navController.navigate(action)
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MembersAdapter.ViewHolder {
        val inflater = LayoutInflater.from(parent.context).inflate(R.layout.single_member_layout, parent, false)
        return ViewHolder(inflater)
    }

    override fun onBindViewHolder(holder: MembersAdapter.ViewHolder, position: Int) {
        val member = members[position]

        holder.setup(member)
    }

    override fun getItemCount(): Int {
        return members.size
    }
}
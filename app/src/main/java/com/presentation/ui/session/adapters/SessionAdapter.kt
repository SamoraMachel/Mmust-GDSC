package com.presentation.ui.session.adapters

import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.presentation.models.ProfilePresentation
import com.presentation.models.SessionPresentation
import com.presentation.models.TrackPresentation
import com.presentation.ui.session.viewmodels.SessionViewModel
import com.presentation.ui.states.SingleProfileUIState
import com.presentation.ui.states.TrackUIState
import com.test.mmustgdsc.R
import com.test.mmustgdsc.databinding.SingleSessionLayoutBinding
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SessionAdapter(
    private val sessions : List<TrackPresentation>,
    private val viewModel: SessionViewModel
) :   RecyclerView.Adapter<SessionAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding = SingleSessionLayoutBinding.bind(itemView)

        fun setup(session : TrackPresentation) {
            binding.sessionDay.text = session.day
            binding.sessionTime.text = session.timeRange
            binding.sessionTitle.text = session.title
        }

        fun setColor(color : Int) {
            binding.sessionDay.chipBackgroundColor = ContextCompat.getColorStateList(
                binding.root.context, color
            )
            binding.sessionTitle.setTextColor(ContextCompat.getColor(
                binding.root.context, color
            ))
        }

        fun openLoader() {
            binding.sessionLeadLoader.visibility = View.VISIBLE
        }

        fun hideLoader() {
            binding.sessionLeadLoader.visibility = View.GONE
        }

        fun loadUserProfile(lead : ProfilePresentation?) {
            hideLoader()
            if (lead != null) {
                Glide.with(binding.root.context)
                    .load(lead.profileImage)
                    .into(binding.sessionLeadImage)
                binding.sessionLeadName.text = lead.name
            } else {
                binding.sessionLeadName.text = "Error loading"
            }
        }

        fun profileLoader(viewModel : SessionViewModel, id: String) = viewModel.viewModelScope.launch {
            viewModel.getFlowLeadData(id).collect { profileState ->
                when(profileState) {
                    is SingleProfileUIState.Failure -> binding.sessionLeadName.text = "Error loading"
                    SingleProfileUIState.Loading -> openLoader()
                    SingleProfileUIState.StandBy -> Unit
                    is SingleProfileUIState.Success -> loadUserProfile(profileState.data)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context).inflate(R.layout.single_session_layout, parent, false)
        return ViewHolder(inflater)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val colorList = listOf(
            R.color.gdsc_blue, R.color.gdsc_red, R.color.gdsc_green, R.color.gdsc_yellow
        )

        val session = sessions[position]
        holder.setup(session)
        holder.setColor(colorList[position % 4])
        holder.profileLoader(viewModel, session.lead)

    }

    override fun getItemCount(): Int {
        return sessions.size
    }

}
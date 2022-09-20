package com.presentation.ui.session.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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

        fun openLoader() {
            binding.sessionLeadLoader.visibility = View.VISIBLE
        }

        fun hideLoader() {
            binding.sessionLeadLoader.visibility = View.GONE
        }

        fun loadUserProfile(lead : ProfilePresentation?) {
            if (lead != null) {
                Glide.with(binding.root.context)
                    .load(lead.profileImage)
                    .into(binding.sessionLeadImage)
                binding.sessionLeadName.text = lead.name
            } else {
                binding.sessionLeadName.text = "Error loading"
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context).inflate(R.layout.single_session_layout, parent, false)
        return ViewHolder(inflater)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val session = sessions[position]
        holder.setup(session)

        viewModel.leadProfile.observeForever { observer ->
            when(observer) {
                is SingleProfileUIState.Failure -> {
                    holder.hideLoader()
                    holder.loadUserProfile(null)
                }
                SingleProfileUIState.Loading -> {
                    holder.openLoader()
                }
                SingleProfileUIState.StandBy -> Unit
                is SingleProfileUIState.Success -> {
                    holder.hideLoader()
                    holder.loadUserProfile(observer.data)
                }
            }
        }

    }

    override fun getItemCount(): Int {
        return sessions.size
    }

}
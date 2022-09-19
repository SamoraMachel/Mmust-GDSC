package com.presentation.ui.session.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.presentation.models.TrackPresentation
import com.test.mmustgdsc.R
import com.test.mmustgdsc.databinding.SingleSessionLayoutBinding

class SessionAdapter(
    private val sessions : List<TrackPresentation>
) :   RecyclerView.Adapter<SessionAdapter.ViewHolder>() {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding = SingleSessionLayoutBinding.bind(itemView)

        fun setup(session : TrackPresentation) {
            binding.sessionDay.text = session.day
            binding.sessionTime.text = session.timeRange
            binding.sessionTitle.text = session.title
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context).inflate(R.layout.single_session_layout, parent, false)
        return ViewHolder(inflater)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val session = sessions[position]
        holder.setup(session)
    }

    override fun getItemCount(): Int {
        return sessions.size
    }
}
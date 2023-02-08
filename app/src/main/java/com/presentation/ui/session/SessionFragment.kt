package com.presentation.ui.session

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.presentation.ui.session.adapters.SessionAdapter
import com.presentation.ui.session.viewmodels.SessionViewModel
import com.presentation.ui.states.TrackListUIState
import com.app.mmustgdsc.databinding.FragmentSessionBinding


class SessionFragment : Fragment() {
    private var _binding : FragmentSessionBinding? = null
    private val binding get() = _binding!!

    private val sessionViewModel : SessionViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSessionBinding.inflate(inflater, container, false)

        binding.sessionRecyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        sessionViewModel.sessionDataList.observe(viewLifecycleOwner) { observer ->
            when(observer) {
                is TrackListUIState.Failure -> {
                    binding.sessionLoader.visibility = View.GONE
                    showSnackBar("Could not get session data.\n${observer.message}")
                }
                TrackListUIState.Loading -> binding.sessionLoader.visibility = View.VISIBLE
                TrackListUIState.StandBy -> Unit
                is TrackListUIState.Success -> {
                    binding.sessionLoader.visibility = View.GONE
                    binding.sessionRecyclerView.adapter = observer.data?.let { data -> SessionAdapter(data, sessionViewModel) }
                }
            }
        }

        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun showSnackBar(message : String)  {
        val snackbar = Snackbar.make(binding.root, message, Snackbar.LENGTH_INDEFINITE)
        snackbar.setAction("Cancel") {
            snackbar.dismiss()
        }
        snackbar.show()
    }
}
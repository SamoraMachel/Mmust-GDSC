package com.presentation.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.presentation.ui.home.adapters.TracksAdapter
import com.presentation.ui.home.viewmodels.TrackViewModel
import com.presentation.ui.states.TrackListUIState
import com.test.mmustgdsc.databinding.FragmentTrackBinding


class TrackFragment : Fragment() {
    private var _binding : FragmentTrackBinding? = null
    private val binding get() = _binding!!

    private val TAG : String = "TrackFragment"

    private val trackViewModel : TrackViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTrackBinding.inflate(inflater, container, false)

        binding.tracksRecyclerView.layoutManager = GridLayoutManager(requireContext(), 2)

        trackViewModel.trackDataList.observe(viewLifecycleOwner) { trackState ->
            when(trackState) {
                is TrackListUIState.Failure -> {
                    binding.tracksLoader.visibility = View.GONE
                    showSnackBar("Could not fetch tracks.\n${trackState.message}")
                }
                TrackListUIState.Loading -> {
                    binding.tracksLoader.visibility = View.VISIBLE
                }
                TrackListUIState.StandBy -> { Unit }
                is TrackListUIState.Success -> {
                    binding.tracksLoader.visibility = View.GONE
                    val trackAdapter = trackState.data?.let { data -> TracksAdapter(data) }
                    binding.tracksRecyclerView.adapter = trackAdapter
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
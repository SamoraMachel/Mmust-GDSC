package com.presentation.ui.settings.track

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.data.models.Track
import com.google.android.material.snackbar.Snackbar
import com.presentation.models.TrackPresentation
import com.presentation.ui.settings.resource.TrackActionViewModel
import com.presentation.ui.states.BooleanUIState
import com.test.mmustgdsc.R
import com.test.mmustgdsc.databinding.ActivityAddTrackBinding

class AddTrackActivity : AppCompatActivity() {
    private lateinit var binding : ActivityAddTrackBinding

    private val viewModel : TrackActionViewModel by viewModels()
    private val leadId : String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddTrackBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    private fun collectData() : TrackPresentation {
        return TrackPresentation(
            binding.trackTitle.text.toString(),
            binding.trackDescription.text.toString(),
            viewModel.uploadedImageLink,
            viewModel.tracksMap,
            leadId,
            binding.trackDay.text.toString(),
            binding.trackTimeRange.text.toString()
        )
    }

    private fun uploadTrack(trackPresentation: TrackPresentation) {
        viewModel.uploadTrack(trackPresentation)
    }

    private fun uploadListener() {
        viewModel.trackUpload.observe(this) { state_listener ->
            when(state_listener) {
                is BooleanUIState.Failure -> {
                    state_listener.message?.let { showSnackBar(it) }
                }
                BooleanUIState.Loading -> {}
                BooleanUIState.StandBy -> {}
                is BooleanUIState.Success -> {
                    finish()
                }
            }
        }
    }

    private fun showSnackBar(message : String)  {
        val snackbar = Snackbar.make(binding.root, message, Snackbar.LENGTH_INDEFINITE)
        snackbar.setAction("Cancel") {
            snackbar.dismiss()
        }
        snackbar.show()
    }
}
package com.presentation.ui.settings.track

import android.content.Context
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import com.presentation.models.TrackPresentation
import com.presentation.ui.states.BooleanUIState
import com.presentation.ui.states.ProgressUIState
import com.presentation.ui.utils.UploadActivity
import com.test.mmustgdsc.R
import com.test.mmustgdsc.databinding.ActivityAddTrackBinding
import com.test.mmustgdsc.databinding.TrackLevelInsertionDialogBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddTrackActivity : UploadActivity() {
    private lateinit var binding : ActivityAddTrackBinding

    private val viewModel : TrackActionViewModel by viewModels()
    private val leadId : String = ""

    private lateinit var trackLevelAdapter: TrackLevelAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddTrackBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.addTrackImageSelect.setOnClickListener {
            requestGallery()
        }

        trackLevelAdapter = TrackLevelAdapter(viewModel.tracksMap.toList())

        binding.trackLevelRecyclerView.adapter = trackLevelAdapter
        binding.trackLevelRecyclerView.layoutManager =
            LinearLayoutManager(applicationContext, LinearLayoutManager.VERTICAL, false)

        binding.addLevelLayoutButton.setOnClickListener {
            openLevelDialogue()
        }

        uploadTrackImageListener()
        uploadTrackListener()
    }

    private fun collectData() : TrackPresentation {
        return TrackPresentation(
            binding.trackTitle.text.toString(),
            binding.trackDescription.text.toString(),
            imageLink,
            viewModel.tracksMap,
            leadId,
            binding.trackDay.text.toString(),
            binding.trackTimeRange.text.toString()
        )
    }

    private fun uploadTrack(trackPresentation: TrackPresentation) {
        viewModel.uploadTrack(trackPresentation)
    }

    private fun uploadTrackListener() {
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

    private fun uploadTrackImageListener() {
        viewModel.trackImageUploaded.observe(this) { state_listener ->
            when(state_listener) {
                is ProgressUIState.Failure -> {
                    state_listener.message?.let { showSnackBar(it) }
                }
                is ProgressUIState.Success -> {
                    imageLink = state_listener.data.data.toString()
                }
                else -> {

                }
            }
        }
    }

    fun openLevelDialogue() {
        val dialogue = MaterialAlertDialogBuilder(this, R.style.RoundShapeTheme)
        val view = layoutInflater.inflate(R.layout.track_level_insertion_dialog, null, false)
        dialogue.setView(view)

        val dialogueBinding = TrackLevelInsertionDialogBinding.bind(view)
        dialogueBinding.saveButton.setOnClickListener {
            addTrackLevel(
                dialogueBinding.dialogueLevelTitle.text.toString(),
                dialogueBinding.dialogueLevelDescription.text.toString()
            )
            Toast.makeText(applicationContext, "Level added successfully", Toast.LENGTH_SHORT).show()
            dialogueBinding.dialogueLevelTitle.setText("")
            dialogueBinding.dialogueLevelDescription.setText("")
        }

        dialogue.show()
    }

    private fun addTrackLevel(title: String, description: String) {
        viewModel.addTrackToList(title, description)
        trackLevelAdapter.addTrack(title, description)
    }

    private fun removeTrackLevel(title: String) {
        viewModel.removeTrackFromList(title)
        trackLevelAdapter.removeTrack(title)
    }

    private fun showSnackBar(message : String)  {
        val snackbar = Snackbar.make(binding.root, message, Snackbar.LENGTH_INDEFINITE)
        snackbar.setAction("Cancel") {
            snackbar.dismiss()
        }
        snackbar.show()
    }

    override fun previewImage(uri: Uri) {
        Glide.with(applicationContext)
            .load(uri)
            .into(binding.trackImage)
    }


}
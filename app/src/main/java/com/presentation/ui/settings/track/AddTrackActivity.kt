package com.presentation.ui.settings.track

import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import com.presentation.mappers.toPresentation
import com.presentation.models.ProfilePresentation
import com.presentation.models.TrackPresentation
import com.presentation.ui.states.BooleanUIState
import com.presentation.ui.states.ProfileListUIState
import com.presentation.ui.states.ProgressUIState
import com.presentation.ui.utils.UploadActivity
import com.app.mmustgdsc.R
import com.app.mmustgdsc.databinding.ActivityAddTrackBinding
import com.app.mmustgdsc.databinding.TrackLevelInsertionDialogBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddTrackActivity : UploadActivity() {
    private lateinit var binding : ActivityAddTrackBinding

    private val viewModel : TrackActionViewModel by viewModels()
    private var leadId : String = ""
    lateinit var listOfLeads : List<ProfilePresentation>

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

        binding.trackLead.threshold = 4
        leadsListListener()

        binding.uploadTrack.setOnClickListener {
            if (imageToUpload == null) {
                showSnackBar("Track image has not been selected")
            } else {
                viewModel.uploadImageToFirebase(applicationContext, imageToUpload!!)
            }
        }

        uploadTrackImageListener()
        uploadTrackListener()
    }

    private fun leadsListListener() {
        viewModel.leadsList.observe(this) { state_listener ->
            when(state_listener) {
                is ProfileListUIState.Failure -> {
                    showSnackBar(state_listener.message ?: "Could not fetch the list of leads")
                }
                is ProfileListUIState.Success -> {
                    state_listener.data?.let {profileList ->
                        listOfLeads = profileList.map {
                            it.toPresentation()
                        } 
                    }
                    val leadListNames: MutableList<String> = mutableListOf()
                    state_listener.data?.forEach {
                        leadListNames.add(it.name)
                    }
                    val leadChoiceAdapter = ArrayAdapter(applicationContext, android.R.layout.simple_dropdown_item_1line, leadListNames)
                    binding.trackLead.setAdapter(leadChoiceAdapter)

                }
                else -> {

                }
            }
        }
    }

    private fun uploadTrackImageListener() {
        viewModel.trackImageUploaded.observe(this) { state_listener ->
            when(state_listener) {
                is ProgressUIState.Failure -> {
                    toggleLoadingVisibility(false)
                    state_listener.message?.let { showSnackBar(it) }
                }
                is ProgressUIState.Loading -> {
                    toggleLoadingVisibility(true, "Uploading Image")
                }
                ProgressUIState.StandBy -> {

                }
                is ProgressUIState.Success -> {
                    imageLink = state_listener.data.data!!
                    try {
                        val trackData = collectData()
                        uploadTrack(trackData)
                    } catch (e: Exception) {
                        e.message?.let { showSnackBar(it) }
                    }
                }
            }
        }
    }

    private fun toggleLoadingVisibility(visible : Boolean, message: String? = null) {
        if(visible) {
            binding.progressFrameLayout.visibility = View.VISIBLE
        } else {
            binding.progressFrameLayout.visibility = View.GONE
        }
        binding.progressText.text = message
    }

    @Throws(Exception::class)
    private fun collectData() : TrackPresentation {
        listOfLeads.forEach {
            if (it.name == binding.trackLead.text.toString()) {
                leadId = it.userId.toString()
                return@forEach
            }
        }

        if (leadId == "") {
            throw Exception("Lead not selected")
        }
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
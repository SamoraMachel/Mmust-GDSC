package com.presentation.ui.settings.resource

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import androidx.activity.viewModels
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.presentation.models.ResourcePresentation
import com.presentation.ui.states.BooleanUIState
import com.presentation.ui.states.ProgressUIState
import com.presentation.ui.states.TrackListUIState
import com.presentation.ui.utils.UploadActivity
import com.test.mmustgdsc.R
import com.test.mmustgdsc.databinding.ActivityAddResourceBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddResourceActivity : UploadActivity() {
    private lateinit var binding : ActivityAddResourceBinding

    private val viewModel: AddResourceViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddResourceBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_add_resource)

        binding.addResourceImageEdit.setOnClickListener {
            requestGallery()
        }

        binding.uploadResource.setOnClickListener {
            try {
                uploadImageToFirebase()
            } catch (e: Exception) {
                e.message?.let { showSnackBar(it) }
            }
        }

        uploadResourceListener()
        imageUploadListener()
    }

    override fun previewImage(uri: Uri) {
        Glide.with(applicationContext)
            .load(uri)
            .into(binding.addResourceImage)
    }


    private fun captureData(): ResourcePresentation {
        return ResourcePresentation(
            binding.addResourceTitle.text.toString(),
            binding.addResourceLink.text.toString(),
            binding.addResourceTrack.text.toString(),
            binding.addResourceDescription.text.toString(),
            binding.addResourceLevel.text.toString(),
            imageLink
        )
    }

    private fun trackListListener() {
        viewModel.trackDataList.observe(this) {stateListener: TrackListUIState ->
            when(stateListener) {
                is TrackListUIState.Failure -> {
                    showSnackBar(stateListener.message.toString())
                }
                TrackListUIState.Loading -> {}
                TrackListUIState.StandBy -> {}
                is TrackListUIState.Success -> {

                    binding.addResourceTrack.setAdapter(
                        ArrayAdapter(applicationContext,
                            android.R.layout.simple_dropdown_item_1line,
                            stateListener.data ?: listOf()
                        )
                    )
                }
            }

        }
    }

    @Throws(Exception::class)
    private fun uploadImageToFirebase() {
        if(imageToUpload == null) {
            throw Exception("Image not selected")
        }
        imageToUpload?.let { viewModel.uploadImageToFirebase(applicationContext, it) }
    }

    private fun imageUploadListener() {
        viewModel.resourceImageUploaded.observe(this) { stateListener ->
            when(stateListener) {
                is ProgressUIState.Failure -> {
                    toggleLoadingVisibility(false)
                    stateListener.message?.let { showSnackBar(it) }
                }
                is ProgressUIState.Loading -> {
                    toggleLoadingVisibility(true, "Uploading Image")
                }
                ProgressUIState.StandBy -> {

                }
                is ProgressUIState.Success -> {
                    imageLink = stateListener.data.data.toString()
                    viewModel.uploadResource(captureData())
                }
            }
        }
    }

    private fun uploadResourceListener() {
        viewModel.resourceUpload.observe(this) { stateListener: BooleanUIState ->
            when(stateListener) {
                is BooleanUIState.Failure -> {
                    toggleLoadingVisibility(false)
                    stateListener.message?.let { showSnackBar(it) }
                }
                BooleanUIState.Loading -> {
                    toggleLoadingVisibility(true, "Uploading Resource")
                }
                BooleanUIState.StandBy -> {}
                is BooleanUIState.Success -> {
                    toggleLoadingVisibility(false)
                    showSnackBar("Resource Uploaded Successfully", Snackbar.LENGTH_SHORT)
                }
            }
        }
    }

    private fun showSnackBar(message : String, length: Int = Snackbar.LENGTH_INDEFINITE)  {
        val snackbar = Snackbar.make(binding.root, message, length)
        snackbar.setAction("Cancel") {
            snackbar.dismiss()
        }
        snackbar.show()
    }

    private fun toggleLoadingVisibility(visible : Boolean, message: String? = null) {
        if(visible) {
            binding.resourceProgressLayout.visibility = View.VISIBLE
        } else {
            binding.resourceProgressLayout.visibility = View.GONE
        }
        binding.progressText.text = message
    }
}
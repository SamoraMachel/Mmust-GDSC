package com.presentation.ui.auth

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import com.bumptech.glide.Glide
import com.presentation.ui.auth.viewmodels.LoginViewModel
import com.presentation.ui.states.ProgressUIState
import com.presentation.ui.states.StringUIState
import com.test.mmustgdsc.R
import com.test.mmustgdsc.databinding.FragmentProfileSetupBinding


class ProfileSetupFragment : Fragment() {
    private val GALLERY_REQUEST = 400
    private val GALLERY_IMAGE_REQUEST = 40

    private var profileImageToUpload : Uri? = null

    private var _binding : FragmentProfileSetupBinding? = null
    private val binding get() = _binding!!

    private val viewModel : LoginViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProfileSetupBinding.inflate(inflater, container, false)

        binding.profileImageEdit.setOnClickListener {
            requestGallery()
        }

        binding.buttonProceed.setOnClickListener {
            profileImageToUpload?.let {
                viewModel.uploadProfileImage(it)
            }
        }

        uploadPhotoListener()
        return binding.root
    }

    private fun requestGallery() {
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                android.Manifest.permission.READ_EXTERNAL_STORAGE) ==
                PackageManager.PERMISSION_GRANTED
        ) {
            openGallery()
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(
                    arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),
                    GALLERY_REQUEST
                )
            }
        }
    }



    private fun uploadPhotoListener() {
        viewModel.profileImageUploaded.observe(viewLifecycleOwner) { state_listener ->
            when(state_listener) {
                is ProgressUIState.Failure -> {
                    binding.buttonProceed.visibility = View.VISIBLE
                    binding.loadingLayout.visibility = View.GONE
                    binding.circularProgressBar.visibility = View.INVISIBLE
                    Toast.makeText(requireContext(), state_listener.message, Toast.LENGTH_LONG).show()
                }
                is ProgressUIState.Loading -> {
                    binding.buttonProceed.visibility = View.GONE
                    binding.loadingLayout.visibility = View.VISIBLE
                    binding.loadingText.text = "Uploading Image ${state_listener.data.progress}"
                }
                ProgressUIState.StandBy -> {

                }
                is ProgressUIState.Success -> {
                    Toast.makeText(requireContext(), "Uploaded Successfully", Toast.LENGTH_LONG).show()
                    binding.buttonProceed.visibility = View.VISIBLE
                    binding.loadingLayout.visibility = View.GONE
                }
            }
        }
    }

    private fun openGallery() {
        val intent = Intent()
        intent.type = "image/*"
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, false)
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), GALLERY_IMAGE_REQUEST)
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == GALLERY_IMAGE_REQUEST && resultCode == Activity.RESULT_OK) {
            val imageurl  = data?.data
            imageurl?.let { uri ->
                profileImageToUpload = uri
                Glide.with(this)
                    .load(uri)
                    .into(binding.profileImage)
            }
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode) {
            GALLERY_REQUEST -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    openGallery()
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
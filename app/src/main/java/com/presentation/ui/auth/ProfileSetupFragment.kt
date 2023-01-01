package com.presentation.ui.auth

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Message
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.presentation.models.ProfilePresentation
import com.presentation.models.RegistrationPresentation
import com.presentation.ui.auth.viewmodels.LoginViewModel
import com.presentation.ui.home.HomeActivity
import com.presentation.ui.states.AuthenticationUIState
import com.presentation.ui.states.ProgressUIState
import com.presentation.ui.states.StringUIState
import com.test.mmustgdsc.R
import com.test.mmustgdsc.databinding.FragmentProfileSetupBinding


class ProfileSetupFragment : Fragment() {
    private val GALLERY_REQUEST = 400
    private val GALLERY_IMAGE_REQUEST = 40

    private var profileImageToUpload : Uri? = null
    private var profileImageLink : String = ""

    private var _binding : FragmentProfileSetupBinding? = null
    private val binding get() = _binding!!

    private val args : ProfileSetupFragmentArgs by navArgs()

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
                viewModel.uploadProfileImage(requireContext(), it)
            }
        }

        uploadPhotoListener()
        registrationListener()
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

    private fun registrationListener() {
        viewModel.userRegistered.observe(viewLifecycleOwner) { state_listener ->
            when(state_listener) {
                is AuthenticationUIState.Failure -> {
                    state_listener.message?.let { showSnackBar(it) }
                    toggleLoadingVisibility(false)
                }
                AuthenticationUIState.Loading -> {
                    binding.buttonProceed.visibility = View.GONE
                    toggleLoadingVisibility(true, "Registering the User")
                }
                AuthenticationUIState.StandBy -> {

                }
                is AuthenticationUIState.Success -> {
                    toggleLoadingVisibility(false)
                    navigateToHomeActivity()
                }
            }
        }
    }

    private fun captureData() : RegistrationPresentation {
        val profile = ProfilePresentation(
            profileImageLink,
            binding.profileFullName.text.toString(),
            args.profileTItle,
            binding.profileProfession.text.toString(),
            binding.profileAbout.text.toString(),
            binding.profileInstagram.text.toString(),
            binding.profileTwitter.text.toString(),
            binding.profileLinkedin.text.toString(),
            binding.profileGithub.text.toString(),
            binding.profileBehance.text.toString(),
            binding.profileDribble.text.toString(),
            binding.profileInterest.text.toString().split(',')
        )

        return RegistrationPresentation(
            args.registrationEmail,
            args.registrationPassword,
            profile
        )
    }



    private fun uploadPhotoListener() {
        viewModel.profileImageUploaded.observe(viewLifecycleOwner) { state_listener ->
            when(state_listener) {
                is ProgressUIState.Failure -> {
                    binding.buttonProceed.visibility = View.VISIBLE
                    toggleLoadingVisibility(false)
                    Toast.makeText(requireContext(), state_listener.message, Toast.LENGTH_LONG).show()
                }
                is ProgressUIState.Loading -> {
                    binding.buttonProceed.visibility = View.GONE
                    toggleLoadingVisibility(true, "Uploading Image")
                }
                ProgressUIState.StandBy -> {

                }
                is ProgressUIState.Success -> {
                    profileImageLink = state_listener.data.data!!
                    viewModel.registerUser(captureData())
                }
            }
        }
    }

    private fun toggleLoadingVisibility(visible : Boolean, message: String? = null) {
        if(visible) {
            binding.loadingLayout.visibility = View.VISIBLE
        } else {
            binding.loadingLayout.visibility = View.GONE
        }
        binding.loadingText.text = message
    }

    private fun showSnackBar(message : String)  {
        val snackbar = Snackbar.make(binding.root, message, Snackbar.LENGTH_INDEFINITE)
        snackbar.setAction("Cancel") {
            snackbar.dismiss()
        }
        snackbar.show()
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

    private fun navigateToHomeActivity() {
        val intent = Intent(requireContext(), HomeActivity::class.java)
        startActivity(intent)
        requireActivity().finish()
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
package com.presentation.ui.auth

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
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
import com.presentation.ui.utils.UploadFragment
import com.app.mmustgdsc.databinding.FragmentProfileSetupBinding


class ProfileSetupFragment : UploadFragment() {
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
            if (imageToUpload != null) {
                viewModel.uploadProfileImage(requireContext(), imageToUpload!!)
            } else {
                showSnackBar("Profile photo not selected", Snackbar.LENGTH_SHORT)
            }
        }

        uploadPhotoListener()
        registrationListener()
        return binding.root
    }

    private fun registrationListener() {
        viewModel.userRegistered.observe(viewLifecycleOwner) { state_listener ->
            when(state_listener) {
                is AuthenticationUIState.Failure -> {
      600dp              state_listener.message?.let { showSnackBar(it) }
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
            imageLink,
            binding.profileFullName.text.toString(),
            args.profileTitle,
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
                    imageLink = state_listener.data.data!!
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

    private fun showSnackBar(message : String, length: Int = Snackbar.LENGTH_INDEFINITE)  {
        val snackbar = Snackbar.make(binding.root, message, length)
        snackbar.setAction("Cancel") {
            snackbar.dismiss()
        }
        snackbar.show()
    }

    override fun previewImage(uri: Uri) {
        Glide.with(this)
            .load(uri)
            .into(binding.profileImage)
    }


    private fun navigateToHomeActivity() {
        val intent = Intent(requireContext(), HomeActivity::class.java)
        startActivity(intent)
        requireActivity().finish()
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
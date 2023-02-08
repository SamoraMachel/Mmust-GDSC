package com.presentation.ui.auth

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import com.presentation.ui.auth.viewmodels.LoginViewModel
import com.presentation.ui.home.HomeActivity
import com.presentation.ui.states.AuthenticationUIState
import com.presentation.ui.states.ProfileCreatedState
import com.app.mmustgdsc.R
import com.app.mmustgdsc.databinding.FragmentLoginBinding
import com.app.mmustgdsc.databinding.ProfileAdminPasswordDialogueBinding

class LoginFragment : Fragment() {
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private val viewModel : LoginViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)

        binding.buttonLogin.setOnClickListener {
            if(checkPassword() && checkEmail()) {
                viewModel.loginUser(
                    binding.loginEmail.text.toString(),
                    binding.loginPassword.text.toString()
                )

            }
        }

        binding.buttonRegister.setOnClickListener {
            if (checkPassword() && checkEmail()) {
                navigateToProfileFragment(binding.loginEmail.text.toString(), binding.loginPassword.text.toString())
            }
        }

        binding.adminImageView.setOnClickListener {
            openAdminPasswordDialogue()
        }

        loginListener()
        checkProfileListener()
        return binding.root
    }

    private fun openAdminPasswordDialogue() {
        var navigationValue : Boolean = false
        val dialogue = MaterialAlertDialogBuilder(requireContext(), R.style.RoundShapeTheme)
        val view = layoutInflater.inflate(R.layout.profile_admin_password_dialogue, null, false)
        dialogue.setView(view)
        val _dialogue = dialogue.show()

        val dialogueBinding = ProfileAdminPasswordDialogueBinding.bind(view)

        dialogueBinding.openButton.setOnClickListener {
            if (checkPassword() && checkEmail()) {
                navigationValue = navigateToLeadProfileFragment(
                    binding.loginEmail.text.toString(),
                    binding.loginPassword.text.toString(),
                    dialogueBinding.adminPassword.text.toString()
                )


                if (!navigationValue) {
                    dialogueBinding.adminPassword.setText("")
                    dialogueBinding.adminPassword.error = "Wrong password!"
                } else {
                    _dialogue?.dismiss()
                }
            }
        }


    }



    private fun loginListener() {
        viewModel.userLoggedIn.observe(viewLifecycleOwner) { state_listener ->
            when(state_listener) {
                is AuthenticationUIState.Failure -> {
                    binding.loadingLayout.visibility = View.GONE
                    state_listener.message?.let { showSnackBar(it) }
                }
                AuthenticationUIState.Loading -> {
                    binding.loadingLayout.visibility = View.VISIBLE
                    binding.loadingText.text = "Logging In..."
                }
                AuthenticationUIState.StandBy -> {

                }
                is AuthenticationUIState.Success -> {
                    navigateToHomeActivity()
                    binding.loadingLayout.visibility = View.GONE
                }
            }
        }
    }

    private fun checkProfileListener() {
        viewModel.profileCreatedState.observe(viewLifecycleOwner) { state_lister ->
            when(state_lister) {
                is ProfileCreatedState.Failure -> {
                    binding.loadingLayout.visibility = View.GONE
                    Toast.makeText(requireContext(), state_lister.message, Toast.LENGTH_LONG).show()
                }
                ProfileCreatedState.Loading -> {
                    binding.loadingLayout.visibility = View.VISIBLE
                    binding.loadingText.text = "Checking for user profile..."
                }
                ProfileCreatedState.StandBy -> {

                }
                is ProfileCreatedState.Success -> {
                    binding.loadingLayout.visibility = View.GONE
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun navigateToHomeActivity() {
        val intent = Intent(requireContext(), HomeActivity::class.java)
        startActivity(intent)
        requireActivity().finish()
    }

    private fun checkEmail() : Boolean {
        return if(binding.loginEmail.text?.isBlank() == true || binding.loginEmail.text?.isEmpty() == true) {
            binding.loginEmail.error = "Email cannot be empty"
            false
        } else true
    }

    private fun checkPassword() : Boolean {
        return if(binding.loginPassword.text?.isBlank() == true || binding.loginPassword.text?.isEmpty() == true) {
            binding.loginPassword.error = "Password cannot be empty"
            false
        } else true
    }

    private fun navigateToProfileFragment(email : String, password : String) {
        val navController = findNavController()
        val action = LoginFragmentDirections.actionLoginFragmentToProfileSetupFragment(email, password)
        navController.navigate(action)
    }

    private fun navigateToLeadProfileFragment(email: String, password: String, adminPass: String) : Boolean {
        if (adminPass != "GDSCLead") {
            showSnackBar("Wrong Password")
            return false
        }
        val navController = findNavController()
        val action = LoginFragmentDirections.actionLoginFragmentToProfileSetupFragment(email, password)
        action.profileTitle = "Lead"
        navController.navigate(action)
        return true
    }

    private fun showSnackBar(message : String, length: Int = Snackbar.LENGTH_INDEFINITE)  {
        val snackbar = Snackbar.make(binding.root, message, length)
        snackbar.setAction("Cancel") {
            snackbar.dismiss()
        }
        snackbar.show()
    }
}
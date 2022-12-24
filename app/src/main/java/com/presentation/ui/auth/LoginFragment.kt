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
import com.presentation.ui.auth.viewmodels.LoginViewModel
import com.presentation.ui.home.HomeActivity
import com.presentation.ui.states.AuthenticationUIState
import com.presentation.ui.states.ProfileCreatedState
import com.test.mmustgdsc.R
import com.test.mmustgdsc.databinding.FragmentLoginBinding

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
    ): View? {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)

        binding.buttonLogin.setOnClickListener {
            if(checkPassword() && checkEmail()) {
                viewModel.loginUser(
                    binding.loginEmail.text.toString(),
                    binding.loginPassword.text.toString()
                )

            }
        }

        loginListener()
        checkProfileListener()
        return binding.root
    }

    private fun loginListener() {
        viewModel.userLoggedIn.observe(viewLifecycleOwner) { state_listener ->
            when(state_listener) {
                is AuthenticationUIState.Failure -> {
                    binding.loadingLayout.visibility = View.GONE
                    Toast.makeText(requireContext(), state_listener.message, Toast.LENGTH_LONG)
                        .show()
                }
                AuthenticationUIState.Loading -> {
                    binding.loadingLayout.visibility = View.VISIBLE
                    binding.loadingText.text = "Logging In..."
                }
                AuthenticationUIState.StandBy -> {

                }
                is AuthenticationUIState.Success -> {
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

    private fun navigateToProfileFragment() {
        val navController = findNavController()
        val action = LoginFragmentDirections.actionLoginFragmentToProfileSetupFragment()
        navController.navigate(action)
    }
}
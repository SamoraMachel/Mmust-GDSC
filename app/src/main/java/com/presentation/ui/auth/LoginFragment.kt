package com.presentation.ui.auth

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.presentation.ui.home.HomeActivity
import com.test.mmustgdsc.R
import com.test.mmustgdsc.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)

        binding.buttonLogin.setOnClickListener {
            if(checkPassword() && checkEmail())
                loginUser()
        }

        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun loginUser() {
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
}
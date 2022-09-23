package com.presentation.ui.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.presentation.ui.auth.viewmodels.LoginViewModel
import com.test.mmustgdsc.R
import com.test.mmustgdsc.databinding.FragmentProfileSetupBinding


class ProfileSetupFragment : Fragment() {

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

        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
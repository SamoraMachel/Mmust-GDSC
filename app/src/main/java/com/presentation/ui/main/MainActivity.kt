package com.presentation.ui.main

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.presentation.ui.home.HomeActivity
import com.presentation.ui.main.viewmodels.MainActivityViewModel
import com.app.mmustgdsc.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var  mainActivityViewModel: MainActivityViewModel
    private val TAG = this::class.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        mainActivityViewModel = ViewModelProvider(this)[MainActivityViewModel::class.java]

        setContentView(binding.root)
    }

    private fun navigateToHomeActivity() {
        val intent = Intent(applicationContext, HomeActivity::class.java)
        startActivity(intent)
        finish()
    }
}
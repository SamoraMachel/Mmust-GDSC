package com.presentation.ui.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.presentation.ui.home.HomeActivity
import com.presentation.ui.main.viewmodels.MainActivityViewModel
import com.test.mmustgdsc.R
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.HiltAndroidApp

@AndroidEntryPoint
class SplashScreenActivity : AppCompatActivity() {
    private lateinit var splashViewModel : MainActivityViewModel

    private val TAG = this::class.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        splashViewModel =  ViewModelProvider(this)[com.presentation.ui.main.viewmodels.MainActivityViewModel::class.java]

        splashViewModel.userLoggedIn.observe(this) { loggedInState ->
            when(loggedInState) {
                is MainActivityViewModel.LoggedInState.Completed -> {
                    if (loggedInState.value) {
                        navigateToHomeActivity()
                    } else {
                        navigateToAuthActivity()
                    }
                }
                else -> {

                }
            }
        }

    }

    private fun navigateToAuthActivity() {
        val intent = Intent(applicationContext, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun navigateToHomeActivity() {
        val intent = Intent(applicationContext, HomeActivity::class.java)
        startActivity(intent)
        finish()
    }
}
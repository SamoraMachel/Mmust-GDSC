package com.presentation.ui.home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.bumptech.glide.Glide
import com.presentation.ui.home.viewmodels.HomeActivityViewModel
import com.presentation.ui.home.viewmodels.ProfileLinkState
import com.presentation.ui.home.viewmodels.TitleState
import com.presentation.ui.settings.ManagementActivity
import com.app.mmustgdsc.R
import com.app.mmustgdsc.databinding.ActivityHomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding

    private lateinit var homeViewModel : HomeActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        homeViewModel = ViewModelProvider(this)[HomeActivityViewModel::class.java]

        homeViewModel.profileLink.observe(this) { state_listener ->
            when(state_listener) {
                is ProfileLinkState.Completed -> {
                    Glide.with(applicationContext)
                        .load(state_listener.link)
                        .circleCrop()
                        .into(binding.userProfileImage)

                    Toast.makeText(applicationContext, state_listener.link, Toast.LENGTH_LONG).show()
                }
                else -> {

                }
            }
        }

        homeViewModel.userTitle.observe(this) { state_listener ->
            when(state_listener) {
                is TitleState.Completed -> {
                    settingScreenListener()
                    if (state_listener.title.equals("Lead")) {
                        settingScreenListener()
                    }
                }
                else -> {

                }
            }
        }



        // setup bottom navigation
        val navController = findNavController(R.id.fragmentContainerView)
        binding.bottomNavigationView.setupWithNavController(navController = navController)
    }

    private fun settingScreenListener() {
        binding.userProfileImage.setOnClickListener {
            val intent = Intent(this, ManagementActivity::class.java)
            startActivity(intent)
        }
    }
}   
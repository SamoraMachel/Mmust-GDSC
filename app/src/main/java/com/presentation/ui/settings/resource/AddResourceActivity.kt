package com.presentation.ui.settings.resource

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.presentation.ui.utils.UploadActivity
import com.test.mmustgdsc.R
import com.test.mmustgdsc.databinding.ActivityAddResourceBinding

class AddResourceActivity : UploadActivity() {
    private lateinit var binding : ActivityAddResourceBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddResourceBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_add_resource)

        binding.addResourceImageEdit.setOnClickListener {
            requestGallery()
        }
    }

    override fun previewImage(uri: Uri) {
        Glide.with(applicationContext)
            .load(uri)
            .into(binding.addResourceImage)
    }
}
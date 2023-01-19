package com.presentation.ui.settings.resource

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.presentation.ui.states.ProgressUIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class AddResourceViewModel @Inject constructor(

): ViewModel() {

    private val _resourceImageUploaded : MutableStateFlow<ProgressUIState> = MutableStateFlow(
        ProgressUIState.StandBy)
    val resourceImageUploaded get()=  _resourceImageUploaded.asLiveData()
}
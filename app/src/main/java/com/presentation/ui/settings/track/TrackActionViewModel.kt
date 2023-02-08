package com.presentation.ui.settings.track

import android.content.Context
import android.net.Uri
import androidx.lifecycle.*
import com.domain.models.ObserverDto
import com.domain.usecases.AddTrackUseCase
import com.domain.usecases.LeadProfileUseCase
import com.domain.usecases.UploadToFirebaseUseCase
import com.presentation.mappers.toDto
import com.presentation.mappers.toPresentation
import com.presentation.models.TrackPresentation
import com.presentation.ui.states.BooleanUIState
import com.presentation.ui.states.ProfileListUIState
import com.presentation.ui.states.ProgressUIState
import com.presentation.ui.utils.FileUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TrackActionViewModel @Inject constructor(
    private val addTrackUseCase: AddTrackUseCase,
    private val uploadToFirebaseUseCase: UploadToFirebaseUseCase,
    private val leadProfileUseCase: LeadProfileUseCase
) : ViewModel() {

    private val _trackUpload : MutableLiveData<BooleanUIState> = MutableLiveData(BooleanUIState.StandBy)
    val trackUpload : LiveData<BooleanUIState> get() = _trackUpload

    private val _trackImageUploaded : MutableStateFlow<ProgressUIState> = MutableStateFlow(ProgressUIState.StandBy)
    val trackImageUploaded get() = _trackImageUploaded.asLiveData()

    private val _tracksMap : MutableMap<String, String> = mutableMapOf()
    val tracksMap : Map<String, String> get() = _tracksMap

    private val _leadsList : MutableLiveData<ProfileListUIState> = MutableLiveData(ProfileListUIState.StandBy)
    val leadsList get() = _leadsList

    init {
        fetchLeads()
    }


    fun uploadTrack(trackPresentation: TrackPresentation) = viewModelScope.launch {
        addTrackUseCase(trackPresentation.toDto()).collect { state_observer: ObserverDto<Boolean> ->
            when(state_observer) {
                is ObserverDto.Failure -> {
                    _trackUpload.value = BooleanUIState.Failure(state_observer.message)
                }
                is ObserverDto.Loading -> {
                    _trackUpload.value = BooleanUIState.Loading
                }
                is ObserverDto.Success -> {
                    _trackUpload.value = BooleanUIState.Success(state_observer.data)
                }
            }
        }
    }

    fun uploadImageToFirebase(context: Context, fileUri: Uri) = viewModelScope.launch {
        val imageFile = FileUtils.getFileFromUri(context, fileUri)
        uploadToFirebaseUseCase.uploadTrackIcons(imageFile).collect { observer ->
            when(observer) {
                is ObserverDto.Failure -> {
                    _trackUpload.value = BooleanUIState.StandBy
                    _trackImageUploaded.value = ProgressUIState.Failure(observer.message)
                }
                is ObserverDto.Loading -> {
                    _trackUpload.value = BooleanUIState.Loading
                    _trackImageUploaded.value = observer.data?.toPresentation()
                        ?.let { ProgressUIState.Loading(it) }!!
                }
                is ObserverDto.Success -> {
                    _trackImageUploaded.value = observer.data?.toPresentation()
                        ?.let { ProgressUIState.Success(it) }!!
                }
            }

        }
    }

    private fun fetchLeads() = viewModelScope.launch {
        leadProfileUseCase().collect { observer ->
            when(observer) {
                is ObserverDto.Failure -> {
                    _leadsList.value = ProfileListUIState.Failure(observer.message)
                }
                is ObserverDto.Loading -> {
                    _leadsList.value = ProfileListUIState.Loading
                }
                is ObserverDto.Success -> {
                    _leadsList.value = ProfileListUIState.Success(observer.data)
                }
            }
        }
    }

    fun addTrackToList(title: String, description: String) {
        _tracksMap[title] = description
    }

    fun removeTrackFromList(title: String) {
        _tracksMap.remove(title)
    }
}
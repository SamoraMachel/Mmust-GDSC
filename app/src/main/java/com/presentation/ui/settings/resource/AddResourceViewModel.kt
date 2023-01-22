package com.presentation.ui.settings.resource

import android.content.Context
import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.domain.models.ObserverDto
import com.domain.usecases.AddResourceUseCase
import com.domain.usecases.GetTrackByNameUseCase
import com.domain.usecases.TrackUseCase
import com.domain.usecases.UploadToFirebaseUseCase
import com.presentation.mappers.toDto
import com.presentation.mappers.toPresentation
import com.presentation.models.ProgressiveDataPresentation
import com.presentation.models.ResourcePresentation
import com.presentation.ui.states.BooleanUIState
import com.presentation.ui.states.ProgressUIState
import com.presentation.ui.states.TrackListUIState
import com.presentation.ui.states.TrackUIState
import com.presentation.ui.utils.FileUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddResourceViewModel @Inject constructor(
    private val addResourceUseCase: AddResourceUseCase,
    private val uploadToFirebaseUseCase: UploadToFirebaseUseCase,
    private val fetchTracksUseCase: TrackUseCase
): ViewModel() {
    private val _resourceUpload: MutableLiveData<BooleanUIState> = MutableLiveData(
        BooleanUIState.StandBy)
    val resourceUpload: LiveData<BooleanUIState> get() = _resourceUpload

    private val _resourceImageUploaded : MutableStateFlow<ProgressUIState> = MutableStateFlow(
        ProgressUIState.StandBy)
    val resourceImageUploaded get()=  _resourceImageUploaded.asLiveData()

    private val _tracksDataList : MutableStateFlow<TrackListUIState> = MutableStateFlow(
        TrackListUIState.StandBy)
    val trackDataList : LiveData<TrackListUIState> get() = _tracksDataList.asLiveData()

    init {
        getTracks()
    }
    fun uploadImageToFirebase(context: Context, fileUri: Uri) = viewModelScope.launch {
        val imageFile = FileUtils.getFileFromUri(context, fileUri)
        uploadToFirebaseUseCase.uploadResourceImage(imageFile).collect { observer ->
            when(observer) {
                is ObserverDto.Failure -> {
                    _resourceImageUploaded.value = ProgressUIState.Failure(observer.message)
                }
                is ObserverDto.Loading -> {
                    _resourceImageUploaded.value = ProgressUIState.Loading(
                        ProgressiveDataPresentation(0, null)
                    )
                }
                is ObserverDto.Success -> {
                    _resourceImageUploaded.value = observer.data?.toPresentation()
                        ?.let { ProgressUIState.Success(it) }!!
                    }
                }
            }
    }

    private fun getTracks() = viewModelScope.launch {
        fetchTracksUseCase().collect { observer ->
            when(observer) {
                is ObserverDto.Loading -> _tracksDataList.value  = TrackListUIState.Loading
                is ObserverDto.Failure -> _tracksDataList.value = TrackListUIState.Failure(observer.message)
                is ObserverDto.Success -> {
                    _tracksDataList.value = TrackListUIState.Success( observer.data?.map { it.toPresentation() })
                }
            }
        }
    }


    fun uploadResource(resource: ResourcePresentation) = viewModelScope.launch {
        addResourceUseCase(resource.toDto()).collect { observer ->
            when(observer) {
                is ObserverDto.Failure -> _resourceUpload.value = BooleanUIState.Failure(observer.message)
                is ObserverDto.Loading -> _resourceUpload.value = BooleanUIState.Loading
                is ObserverDto.Success -> _resourceUpload.value = BooleanUIState.Success(observer.data)
            }
        }
    }

}
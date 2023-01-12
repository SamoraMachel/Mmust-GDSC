package com.presentation.ui.settings.resource

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.domain.models.ObserverDto
import com.domain.usecases.AddTrackUseCase
import com.presentation.mappers.toDto
import com.presentation.models.TrackPresentation
import com.presentation.ui.states.BooleanUIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TrackActionViewModel @Inject constructor(
    private val addTrackUseCase: AddTrackUseCase
) : ViewModel() {

    private val _trackUpload : MutableLiveData<BooleanUIState> = MutableLiveData(BooleanUIState.StandBy)
    val trackUpload : LiveData<BooleanUIState> get() = _trackUpload

    val uploadedImageLink : String = ""

    private val _tracksMap : MutableMap<String, String> = mutableMapOf()
    val tracksMap : Map<String, String> get() = _tracksMap

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

    fun addTrackToList(title: String, description: String) {
        _tracksMap[title] = description
    }

    fun removeTrackFromList(title: String) {
        _tracksMap.remove(title)
    }
}
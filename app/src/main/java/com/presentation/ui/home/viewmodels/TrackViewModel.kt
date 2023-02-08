package com.presentation.ui.home.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.domain.models.ObserverDto
import com.domain.usecases.TrackUseCase
import com.presentation.mappers.toPresentation
import com.presentation.ui.states.TrackListUIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TrackViewModel @Inject constructor(
    private val fetchTracksUseCase: TrackUseCase
) : ViewModel() {
    private val _tracksDataList : MutableStateFlow<TrackListUIState> = MutableStateFlow(TrackListUIState.StandBy)
    val trackDataList : LiveData<TrackListUIState> get() = _tracksDataList.asLiveData()

    init {
        getTracks()
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

}
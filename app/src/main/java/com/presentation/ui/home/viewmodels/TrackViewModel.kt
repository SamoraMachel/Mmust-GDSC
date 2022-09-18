package com.presentation.ui.home.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.domain.models.ObserverDto
import com.domain.usecases.TrackUseCase
import com.presentation.mappers.toPresentation
import com.presentation.models.TrackPresentation
import com.presentation.ui.states.TrackUIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TrackViewModel @Inject constructor(
    private val fetchTracksUseCase: TrackUseCase
) : ViewModel() {
    private val _tracksDataList : MutableStateFlow<TrackUIState> = MutableStateFlow(TrackUIState.StandBy)
    val trackDataList : LiveData<TrackUIState> get() = _tracksDataList.asLiveData()

    init {
        getTracks()
    }

    private fun getTracks() = viewModelScope.launch {
        fetchTracksUseCase().collect { observer ->
            when(observer) {
                is ObserverDto.Loading -> _tracksDataList.value  = TrackUIState.Loading
                is ObserverDto.Failure -> _tracksDataList.value = TrackUIState.Failure(observer.message)
                is ObserverDto.Success -> {
                    val trackPresentation = mutableListOf<TrackPresentation>()
                    _tracksDataList.value = TrackUIState.Success( observer.data?.map { it.toPresentation() })
                }
            }
        }
    }

}
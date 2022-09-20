package com.presentation.ui.session.viewmodels

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
import kotlinx.coroutines.launch

@HiltViewModel
class SessionViewModel(
    private val fetchSessionsUseCase : TrackUseCase
) : ViewModel() {
    private val _sessionDataList : MutableStateFlow<TrackUIState> = MutableStateFlow(TrackUIState.StandBy)
    val sessionDataList get() = _sessionDataList.asLiveData()

    init {
        getSessions()
    }

    private fun getSessions() = viewModelScope.launch {
        fetchSessionsUseCase().collect { observer ->
            when(observer) {
                is ObserverDto.Loading -> _sessionDataList.value  = TrackUIState.Loading
                is ObserverDto.Failure -> _sessionDataList.value = TrackUIState.Failure(observer.message)
                is ObserverDto.Success -> {
                    _sessionDataList.value = TrackUIState.Success( observer.data?.map { it.toPresentation() })
                }
            }
        }
    }
}
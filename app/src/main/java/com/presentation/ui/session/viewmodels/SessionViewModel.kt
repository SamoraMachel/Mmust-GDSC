package com.presentation.ui.session.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.domain.models.ObserverDto
import com.domain.usecases.FetchSessionUseCase
import com.domain.usecases.FetchSpecificLeadUseCase
import com.domain.usecases.TrackUseCase
import com.presentation.mappers.toPresentation
import com.presentation.ui.states.SessionUIState
import com.presentation.ui.states.SingleProfileUIState
import com.presentation.ui.states.TrackUIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SessionViewModel @Inject constructor(
    private val fetchSessionsUseCase : TrackUseCase,
    private val fetchSpecificLeadUseCase: FetchSpecificLeadUseCase
) : ViewModel() {
    private val _sessionDataList : MutableStateFlow<TrackUIState> = MutableStateFlow(TrackUIState.StandBy)
    val sessionDataList get() = _sessionDataList.asLiveData()

    private val _leadProfile : MutableStateFlow<SingleProfileUIState> = MutableStateFlow(SingleProfileUIState.StandBy)
    val leadProfile : LiveData<SingleProfileUIState> get() = _leadProfile.asLiveData()

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

    fun getLeadData(id : String) = viewModelScope.launch {
        fetchSpecificLeadUseCase(id).collect { observer ->
            when(observer) {
                is ObserverDto.Failure -> _leadProfile.value = SingleProfileUIState.Failure(observer.message)
                is ObserverDto.Loading -> _leadProfile.value = SingleProfileUIState.Loading
                is ObserverDto.Success -> {
                    _leadProfile.value = SingleProfileUIState.Success(
                        observer.data?.toPresentation()
                    )
                }
            }
        }
    }

}
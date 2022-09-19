package com.presentation.ui.home.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.domain.models.ObserverDto
import com.domain.usecases.FetchSpecificLeadUseCase
import com.domain.usecases.MemberProfileUseCase
import com.domain.usecases.ResourceLevelUseCase
import com.domain.usecases.ResourceUseCase
import com.presentation.mappers.toPresentation
import com.presentation.models.ProfilePresentation
import com.presentation.ui.states.ResourceUIState
import com.presentation.ui.states.SingleProfileUIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ResourceViewModel @Inject constructor(
    private val fetchSpecificLead : FetchSpecificLeadUseCase,
    private val fetchResourcesByLevel : ResourceLevelUseCase
) : ViewModel(){
    private val _leadProfile : MutableStateFlow<SingleProfileUIState> = MutableStateFlow(SingleProfileUIState.StandBy)
    val leadProfile : LiveData<SingleProfileUIState> get() = _leadProfile.asLiveData()

    private val _resourceDataList : MutableStateFlow<ResourceUIState> = MutableStateFlow(ResourceUIState.StandBy)
    val resourceDataList : LiveData<ResourceUIState> get() = _resourceDataList.asLiveData()

    fun getLeadData(id : String) = viewModelScope.launch {
        fetchSpecificLead(id).collect { observer ->
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

    fun getResourceByLevel(level : String, track: String) = viewModelScope.launch {
        Log.d("resourceViewModel", "viewModelBeingCalled")
        fetchResourcesByLevel(level, track).collect { observer ->
            when(observer) {
                is ObserverDto.Failure -> _resourceDataList.value = ResourceUIState.Failure(observer.message)
                is ObserverDto.Loading -> _resourceDataList.value = ResourceUIState.Loading
                is ObserverDto.Success -> {
                    Log.d("resourceViewModel", "getResourceByLevel: ${observer.data}")
                    _resourceDataList.value = ResourceUIState.Success(
                        observer.data?.map { data ->
                            data.toPresentation()
                        }
                    )
                }
            }
        }
    }

}
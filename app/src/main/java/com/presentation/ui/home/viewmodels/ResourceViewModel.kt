package com.presentation.ui.home.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.domain.models.ObserverDto
import com.domain.usecases.FetchSpecificLeadUseCase
import com.domain.usecases.MemberProfileUseCase
import com.presentation.mappers.toPresentation
import com.presentation.models.ProfilePresentation
import com.presentation.ui.states.SingleProfileUIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ResourceViewModel @Inject constructor(
    private val fetchSpecificLead : FetchSpecificLeadUseCase
) : ViewModel(){
    private val _leadProfile : MutableStateFlow<SingleProfileUIState> = MutableStateFlow(SingleProfileUIState.StandBy)
    val leadProfile : LiveData<SingleProfileUIState> get() = _leadProfile.asLiveData()

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
}
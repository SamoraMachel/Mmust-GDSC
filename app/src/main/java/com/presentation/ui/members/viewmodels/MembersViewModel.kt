package com.presentation.ui.members.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.domain.models.ObserverDto
import com.domain.usecases.LeadProfileUseCase
import com.domain.usecases.MemberProfileUseCase
import com.presentation.mappers.toPresentation
import com.presentation.ui.states.MemberUIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MembersViewModel @Inject constructor(
    private val fetchLeadProfileUseCase: LeadProfileUseCase,
    private val fetchMemberProfileUseCase: MemberProfileUseCase
) : ViewModel() {
    private val _leadDataList : MutableStateFlow<MemberUIState> = MutableStateFlow(MemberUIState.StandBy)
    val leadDataList : LiveData<MemberUIState> get() = _leadDataList.asLiveData()

    init {
        getLeads()
        getMembers()
    }

    private fun getLeads() = viewModelScope.launch{
        fetchLeadProfileUseCase().collect { observer ->
            when(observer) {
                is ObserverDto.Failure -> _leadDataList.value = MemberUIState.Failure(observer.message)
                is ObserverDto.Loading -> _leadDataList.value = MemberUIState.Loading
                is ObserverDto.Success -> {
                    val memberPresentation = observer.data?.map { data -> data.toPresentation() }
                    _leadDataList.value = MemberUIState.Success(memberPresentation)
                }
            }
        }
    }

    private fun getMembers() = viewModelScope.launch {
        fetchMemberProfileUseCase().collect { observer ->
            when(observer) {
                is ObserverDto.Failure -> _leadDataList.value = MemberUIState.Failure(observer.message)
                is ObserverDto.Loading -> _leadDataList.value = MemberUIState.Loading
                is ObserverDto.Success -> {
                    val memberPresentation = observer.data?.map { data -> data.toPresentation() }
                    _leadDataList.value = MemberUIState.Success(memberPresentation)
                }
            }
        }
    }
}
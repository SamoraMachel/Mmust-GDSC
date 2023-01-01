package com.presentation.ui.home.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.PreferenceKeys
import com.domain.usecases.GetStringDataStoreUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeActivityViewModel @Inject constructor(
    private val getStringDataStoreUseCase: GetStringDataStoreUseCase
) : ViewModel() {

    private val _profileLink : MutableLiveData<ProfileLinkState> = MutableLiveData(ProfileLinkState.StandBy)
    val profileLink : LiveData<ProfileLinkState> get() = _profileLink

    private val _userTitle : MutableLiveData<TitleState> = MutableLiveData(TitleState.StandBy)
    val userTitle : LiveData<TitleState> get() = _userTitle


    init {
        getProfileLink()
        getUserTitle()
    }

    private fun getProfileLink() = viewModelScope.launch {
        getStringDataStoreUseCase(PreferenceKeys.USER_PROFILE_IMAGE).collect {
            _profileLink.value = ProfileLinkState.Completed(it)
        }
    }

    private fun getUserTitle() = viewModelScope.launch {
        getStringDataStoreUseCase(PreferenceKeys.USER_TITLE).collect {
            _userTitle.value = TitleState.Completed(it)
        }
    }

}

sealed class ProfileLinkState {
    object StandBy : ProfileLinkState()
    class Completed(val link : String) : ProfileLinkState()
}

sealed class TitleState {
    object StandBy : TitleState()
    class Completed(val title : String) : TitleState()
}
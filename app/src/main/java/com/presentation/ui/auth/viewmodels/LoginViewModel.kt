package com.presentation.ui.auth.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.domain.models.LoginDto
import com.domain.models.ObserverDto
import com.domain.repository.UserDataStore
import com.domain.usecases.*
import com.presentation.models.ProfilePresentation
import com.presentation.ui.states.AuthenticationUIState
import com.presentation.ui.states.ProfileCreatedState
import com.presentation.ui.states.SingleProfileUIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val registrationUseCase: RegistrationUseCase,
    private val saveLoginDataStoreUseCase: SaveLoginDataStoreUseCase,
    private val profileEmailUseCase: FetchProfileEmailUseCase,
    private val saveProfileUserDataStore: SaveProfileDataStoreUseCase
): ViewModel() {
    private val _userLoggedIn : MutableStateFlow<AuthenticationUIState> = MutableStateFlow(AuthenticationUIState.StandBy)
    val userLoggedIn get() = _userLoggedIn.asLiveData()

    private val _profileCreated : MutableStateFlow<ProfileCreatedState> = MutableStateFlow(ProfileCreatedState.StandBy)
    val profileCreatedState get() = _profileCreated.asLiveData()

    fun loginUser(email: String, password: String) = viewModelScope.launch {
        val loginDetails = LoginDto(email, password)
        loginUseCase(loginDetails).collect{ observer ->
            when(observer) {
                is ObserverDto.Failure -> {
                    saveLoginDataStoreUseCase(false)
                    _userLoggedIn.value = AuthenticationUIState.Failure(observer.message)
                }
                is ObserverDto.Loading -> _userLoggedIn.value = AuthenticationUIState.Loading
                is ObserverDto.Success -> {
                    if(observer.data == true) {
                        saveLoginDataStoreUseCase(true)
                        _userLoggedIn.value = AuthenticationUIState.Success(true)
                    } else {
                        saveLoginDataStoreUseCase(false)
                        _userLoggedIn.value = AuthenticationUIState.Success(false)
                    }
                }
            }
        }
    }

    fun checkProfileCreated(email : String) = viewModelScope.launch {
        profileEmailUseCase(email).collect { observer ->
            when(observer) {
                is ObserverDto.Failure -> _profileCreated.value = ProfileCreatedState.Failure(observer.message)
                is ObserverDto.Loading -> _profileCreated.value = ProfileCreatedState.Loading
                is ObserverDto.Success -> {
                    if((observer.data ?: false) == true) {
                        _profileCreated.value = ProfileCreatedState.Success(true)
                        saveProfileUserDataStore(true)
                    } else {
                        _profileCreated.value = ProfileCreatedState.Success(false)
                        saveProfileUserDataStore(false)
                    }
                }
            }
        }
    }
}
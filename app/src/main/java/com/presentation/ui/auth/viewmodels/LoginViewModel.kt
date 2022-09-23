package com.presentation.ui.auth.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.domain.models.LoginDto
import com.domain.models.ObserverDto
import com.domain.repository.UserDataStore
import com.domain.usecases.LoginUseCase
import com.domain.usecases.RegistrationUseCase
import com.presentation.ui.states.AuthenticationUIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val registrationUseCase: RegistrationUseCase,
    private val userDataStore : UserDataStore
): ViewModel() {
    private val _userLoggedIn : MutableStateFlow<AuthenticationUIState> = MutableStateFlow(AuthenticationUIState.StandBy)
    val userLoggedIn get() = _userLoggedIn.asLiveData()

    fun loginUser(email: String, password: String) = viewModelScope.launch {
        val loginDetails = LoginDto(email, password)
        loginUseCase(loginDetails).collect{ observer ->
            when(observer) {
                is ObserverDto.Failure -> AuthenticationUIState.Failure(observer.message)
                is ObserverDto.Loading -> AuthenticationUIState.Loading
                is ObserverDto.Success -> {
                    if((observer.data ?: false) == true) {
                        userDataStore.saveUserLoggedInState(true)
                        AuthenticationUIState.Success(true)
                    } else {
                        userDataStore.saveUserLoggedInState(false)
                    }
                }
            }
        }
    }



}
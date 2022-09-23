package com.presentation.ui.states

sealed class AuthenticationUIState {
    class Success(val data : Boolean = false) : AuthenticationUIState()
    class Failure(val message : String?) : AuthenticationUIState()
    object Loading : AuthenticationUIState()
    object StandBy : AuthenticationUIState()
}
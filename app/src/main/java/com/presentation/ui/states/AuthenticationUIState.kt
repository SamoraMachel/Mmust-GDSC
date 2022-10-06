package com.presentation.ui.states

sealed class AuthenticationUIState {
    class Success(val data : Boolean = false) : AuthenticationUIState()
    class Failure(val message : String?) : AuthenticationUIState()
    object Loading : AuthenticationUIState()
    object StandBy : AuthenticationUIState()
}

sealed class ProfileCreatedState {
    class Success(val data : Boolean) : ProfileCreatedState()
    class Failure(val message: String?) : ProfileCreatedState()
    object Loading : ProfileCreatedState()
    object StandBy : ProfileCreatedState()
}
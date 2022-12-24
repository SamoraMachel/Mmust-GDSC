package com.presentation.ui.states

sealed class StringUIState {
    class Success(val data : String? = null) : StringUIState()
    class Failure(val message : String?) : StringUIState()
    object Loading : StringUIState()
    object StandBy : StringUIState()
}
package com.presentation.ui.states

sealed class BooleanUIState {
    class Success(val data : Boolean? = null) : BooleanUIState()
    class Failure(val message : String?) : BooleanUIState()
    object Loading : BooleanUIState()
    object StandBy : BooleanUIState()
}
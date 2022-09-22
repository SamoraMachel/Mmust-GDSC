package com.presentation.ui.states

import com.presentation.models.ProfilePresentation

sealed class SingleProfileUIState {
    class Success(val data : ProfilePresentation?) : SingleProfileUIState()
    class Failure(val message : String?) : SingleProfileUIState()
    object Loading : SingleProfileUIState()
    object StandBy : SingleProfileUIState()
}
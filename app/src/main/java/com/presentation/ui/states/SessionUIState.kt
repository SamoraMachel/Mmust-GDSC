package com.presentation.ui.states

import com.presentation.models.SessionPresentation

sealed class SessionUIState {
    class Success(val data : List<SessionPresentation>?) : SessionUIState()
    class Failure(val message : String?) : SessionUIState()
    object Loading : SessionUIState()
    object StandBy : SessionUIState()
}

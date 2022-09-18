package com.presentation.ui.states

import com.presentation.models.ProfilePresentation


sealed class MemberUIState {
    class Success(val data : List<ProfilePresentation>?) : MemberUIState()
    class Failure(val message : String?) : MemberUIState()
    object Loading : MemberUIState()
    object StandBy : MemberUIState()
}
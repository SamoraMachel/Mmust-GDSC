package com.presentation.ui.states

import com.domain.models.ProfileDto

sealed class ProfileListUIState {
    object StandBy: ProfileListUIState()
    object Loading: ProfileListUIState()
    class Success(val data: List<ProfileDto>?): ProfileListUIState()
    class Failure(val message: String?): ProfileListUIState()
}
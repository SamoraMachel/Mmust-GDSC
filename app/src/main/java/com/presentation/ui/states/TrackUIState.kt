package com.presentation.ui.states

import com.presentation.models.TrackPresentation

sealed class TrackUIState {
    class Success(val data : List<TrackPresentation>?) : TrackUIState()
    class Failure(val message : String?) : TrackUIState()
    object Loading : TrackUIState()
    object StandBy : TrackUIState()
}
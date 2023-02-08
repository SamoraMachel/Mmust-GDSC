package com.presentation.ui.states

import com.presentation.models.TrackPresentation

sealed class TrackListUIState {
    class Success(val data : List<TrackPresentation>?) : TrackListUIState()
    class Failure(val message : String?) : TrackListUIState()
    object Loading : TrackListUIState()
    object StandBy : TrackListUIState()
}

sealed class TrackUIState {
    class Success(val data: TrackPresentation?): TrackUIState()
    class Failure(val message: String?): TrackUIState()
    object Loading: TrackUIState()
    object StandBy: TrackUIState()
}
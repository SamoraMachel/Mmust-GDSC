package com.presentation.ui.states

import com.presentation.models.ProgressiveDataPresentation

sealed class ProgressUIState {
    class Success(val data : ProgressiveDataPresentation<String>) : ProgressUIState()
    class Failure(val message : String?) : ProgressUIState()
    class Loading(val data : ProgressiveDataPresentation<String>) : ProgressUIState()
    object StandBy : ProgressUIState()
}
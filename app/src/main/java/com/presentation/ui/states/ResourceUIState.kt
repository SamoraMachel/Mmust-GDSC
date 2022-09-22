package com.presentation.ui.states

import com.presentation.models.ResourcePresentation

sealed class ResourceUIState {
    class Success(val data : List<ResourcePresentation>?) : ResourceUIState()
    class Failure(val message : String?) : ResourceUIState()
    object Loading : ResourceUIState()
    object StandBy : ResourceUIState()
}
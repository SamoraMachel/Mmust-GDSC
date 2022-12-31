package com.presentation.ui.main.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.app.PreferenceKeys
import com.domain.usecases.GetBooleanDataStoreUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val getBooleanDataStoreUseCase: GetBooleanDataStoreUseCase,
): ViewModel() {
    private val TAG = this::class.simpleName

    private var _userLoggedIn : Boolean = false
    val userLoggedIn get() = _userLoggedIn

    private val _userHasProfile : MutableStateFlow<Boolean> = MutableStateFlow(false)
    val userHasProfile : LiveData<Boolean> get() = _userHasProfile.asLiveData()

    fun checkUserLoggedIn() = viewModelScope.launch  {
        getBooleanDataStoreUseCase(PreferenceKeys.IS_USER_LOGGED_IN).collect {
            _userLoggedIn = it
            Log.d(TAG, "checkUserLoggedIn: value - $it")
        }
    }

    fun checkHasProfile() = viewModelScope.launch {
        getBooleanDataStoreUseCase(PreferenceKeys.PROFILE_AVAILABLE).collect {
            _userHasProfile.value = it
        }
    }
}
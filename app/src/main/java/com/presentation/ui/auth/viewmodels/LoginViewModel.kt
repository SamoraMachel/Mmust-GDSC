package com.presentation.ui.auth.viewmodels

import android.content.Context
import android.net.Uri
import androidx.core.net.toFile
import androidx.datastore.preferences.core.Preferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.app.PreferenceKeys
import com.domain.models.LoginDto
import com.domain.models.ObserverDto
import com.domain.models.ProgressiveDataDto
import com.domain.models.RegistrationDto
import com.domain.repository.UserDataStore
import com.domain.usecases.*
import com.presentation.mappers.toDto
import com.presentation.mappers.toPresentation
import com.presentation.models.ProfilePresentation
import com.presentation.models.RegistrationPresentation
import com.presentation.ui.states.*
import com.presentation.ui.utils.FileUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

@HiltViewModel
class
LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val registrationUseCase: RegistrationUseCase,
    private val saveLoginDataStoreUseCase: SaveLoginDataStoreUseCase,
    private val profileEmailUseCase: FetchProfileEmailUseCase,
    private val saveProfileUserDataStore: SaveProfileDataStoreUseCase,
    private val saveStringDataStoreUseCase: SaveStringDataStoreUseCase,
    private val uploadToFirebaseUseCase: UploadToFirebaseUseCase
): ViewModel() {
    private val _userLoggedIn : MutableStateFlow<AuthenticationUIState> = MutableStateFlow(AuthenticationUIState.StandBy)
    val userLoggedIn get() = _userLoggedIn.asLiveData()

    private val _userRegistered : MutableStateFlow<AuthenticationUIState> = MutableStateFlow(AuthenticationUIState.StandBy)
    val userRegistered get() = _userRegistered.asLiveData()

    private val _profileCreated : MutableStateFlow<ProfileCreatedState> = MutableStateFlow(ProfileCreatedState.StandBy)
    val profileCreatedState get() = _profileCreated.asLiveData()

    private val _profileImageUploaded : MutableStateFlow<ProgressUIState> = MutableStateFlow(ProgressUIState.StandBy)
    val profileImageUploaded get() = _profileImageUploaded.asLiveData()


    fun loginUser(email: String, password: String) = viewModelScope.launch {
        val loginDetails = LoginDto(email, password)
        loginUseCase(loginDetails).collect{ observer ->
            when(observer) {
                is ObserverDto.Failure -> {
                    saveLoginDataStoreUseCase(false)
                    _userLoggedIn.value = AuthenticationUIState.Failure(observer.message)
                }
                is ObserverDto.Loading -> _userLoggedIn.value = AuthenticationUIState.Loading
                is ObserverDto.Success -> {
                    _userLoggedIn.value = AuthenticationUIState.Success(observer.data?:false)
                    if(observer.data == true) {
                        saveLoginDataStoreUseCase(true)
                        checkProfileCreated(email)
                    } else {
                        saveLoginDataStoreUseCase(false)
                    }
                }
            }
        }
    }

    fun registerUser(registrationPresentation: RegistrationPresentation) = viewModelScope.launch {
        registrationUseCase(registrationPresentation.toDto()).collect { observer ->
            when(observer) {
                is ObserverDto.Failure -> {
                    saveLoginDataStoreUseCase(false)
                    _userRegistered.value = AuthenticationUIState.Failure(observer.message)
                }
                is ObserverDto.Loading -> _userRegistered.value = AuthenticationUIState.Loading
                is ObserverDto.Success -> {
                    _userRegistered.value = AuthenticationUIState.Success(observer.data?:false)
                    if(observer.data == true) {
                        saveLoginDataStoreUseCase(true)
                        saveProfileUserDataStore(true)
                        saveStringDataStoreUseCase(registrationPresentation.profile.profileImage, PreferenceKeys.USER_PROFILE_IMAGE)
                        saveStringDataStoreUseCase(registrationPresentation.profile.name, PreferenceKeys.USER_FULL_NAME)
                        saveStringDataStoreUseCase(registrationPresentation.profile.title, PreferenceKeys.USER_TITLE)

                    } else {
                        saveProfileUserDataStore(false)
                        saveLoginDataStoreUseCase(false)

                    }
                }
            }
        }
    }

    fun checkProfileCreated(email : String) = viewModelScope.launch {
        profileEmailUseCase(email).collect { observer ->
            when(observer) {
                is ObserverDto.Failure -> _profileCreated.value = ProfileCreatedState.Failure(observer.message)
                is ObserverDto.Loading -> _profileCreated.value = ProfileCreatedState.Loading
                is ObserverDto.Success -> {
                    if((observer.data ?: false) == true) {
                        _profileCreated.value = ProfileCreatedState.Success(true)
                        saveProfileUserDataStore(true)
                        saveStringDataStoreUseCase(observer.data!!.profileImage, PreferenceKeys.USER_PROFILE_IMAGE)
                        saveStringDataStoreUseCase(observer.data.name, PreferenceKeys.USER_FULL_NAME)
                        saveStringDataStoreUseCase(observer.data.title, PreferenceKeys.USER_TITLE)
                    } else {
                        _profileCreated.value = ProfileCreatedState.Success(false)
                        saveProfileUserDataStore(false)

                    }
                }
            }
        }
    }

    fun uploadProfileImage(context : Context, fileUri : Uri) = viewModelScope.launch {
        val imageFile = FileUtils.getFileFromUri(context, fileUri)
        uploadToFirebaseUseCase.uploadProfileImage(imageFile).collect { observer ->
            when(observer) {
                is ObserverDto.Failure -> _profileImageUploaded.value = ProgressUIState.Failure(observer.message)
                is ObserverDto.Loading -> {
                    _profileImageUploaded.value = observer.data?.toPresentation()
                        ?.let { ProgressUIState.Loading(it) }!!
                }
                is ObserverDto.Success -> {
                    _profileImageUploaded.value = observer.data?.toPresentation()
                        ?.let { ProgressUIState.Success(it) }!!
                }
            }
        }
    }
}
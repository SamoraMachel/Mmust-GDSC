package com.data.repository

import com.domain.models.LoginDto
import com.domain.models.ObserverDto
import com.domain.models.RegistrationDto
import com.domain.repository.AuthenticationRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.io.IOException
import javax.inject.Inject

class AuthenticationRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val firebaseFirestore: FirebaseFirestore
) : AuthenticationRepository {
    override suspend fun loginUser(loginModel: LoginDto): StateFlow<ObserverDto<Boolean>> {
        val loginState : MutableStateFlow<ObserverDto<Boolean>> = MutableStateFlow(ObserverDto.Loading())
        try {
            firebaseAuth.signInWithEmailAndPassword(loginModel.email, loginModel.password)
                .addOnSuccessListener {
                    suspend {
                        loginState.emit(ObserverDto.Success(true))
                    }
                }
                .addOnFailureListener {
                    suspend {
                        loginState.emit(ObserverDto.Failure(false, it.message))
                    }
                }

        } catch (error : IOException) {
            loginState.emit(ObserverDto.Failure(true, "Network Error: Kindly check your internet"))
        } catch (error : Exception) {
            loginState.emit(ObserverDto.Failure(false, error.message))
        }
        return loginState
    }

    override suspend fun registerUser(regModel: RegistrationDto): StateFlow<ObserverDto<Boolean>> {
        val registrationState : MutableStateFlow<ObserverDto<Boolean>> = MutableStateFlow(ObserverDto.Loading())
        try {
            firebaseAuth.signInWithEmailAndPassword(regModel.email, regModel.password)
                .addOnSuccessListener {
                    val userProfile = hashMapOf(
                        "id"        to it.user,
                        "fullName"  to regModel.fullName,
                        "profileImage" to regModel.profileImage,
                        "interests" to regModel.interests,
                        "title"     to regModel.title,
                        "profession" to regModel.profession,
                        "twitter"   to regModel.twitter,
                        "linkedIn"  to regModel.linkedin,
                        "github"    to regModel.github,
                        "behance"   to regModel.behance,
                        "dribble"   to regModel.dribble
                    )

                    firebaseFirestore.collection("profiles")
                        .add(userProfile)
                        .addOnSuccessListener {
                            suspend {
                                registrationState.emit(ObserverDto.Success(true))
                            }
                        }
                        .addOnFailureListener {
                            suspend {
                                registrationState.emit(ObserverDto.Failure(false, it.message))
                            }
                        }
                }
                .addOnFailureListener {
                    suspend {
                        registrationState.emit(ObserverDto.Failure(false, it.message))
                    }
                }

        } catch (error : IOException) {
            registrationState.emit(ObserverDto.Failure(true, "Network Error: Kindly check your internet"))
        } catch (error : Exception) {
            registrationState.emit(ObserverDto.Failure(false, error.message))
        }
        return registrationState
    }

    override suspend fun logoutUser(): StateFlow<ObserverDto<Boolean>> {
        val logoutState : MutableStateFlow<ObserverDto<Boolean>> = MutableStateFlow(ObserverDto.Loading())
        try {
            firebaseAuth.signOut()
            logoutState.emit(ObserverDto.Success(true))
        } catch (error : IOException) {
            logoutState.emit(ObserverDto.Failure(true, "Network Error: Kindly check your internet"))
        } catch (error : Exception) {
            logoutState.emit(ObserverDto.Failure(false, error.message))
        }
        return logoutState
    }
}
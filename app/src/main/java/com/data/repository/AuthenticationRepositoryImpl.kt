package com.data.repository

import com.domain.models.LoginDto
import com.domain.models.ObserverDto
import com.domain.models.RegistrationDto
import com.domain.repository.AuthenticationRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

class AuthenticationRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val firebaseFirestore: FirebaseFirestore
) : AuthenticationRepository {
    override suspend fun loginUser(loginModel: LoginDto): Flow<ObserverDto<Boolean>> = channelFlow {
        try {
            send(ObserverDto.Loading())
            firebaseAuth.signInWithEmailAndPassword(loginModel.email, loginModel.password)
                .addOnSuccessListener {
                    launch {
                        send(ObserverDto.Success(true))
                    }
                }
                .addOnFailureListener {
                    launch {
                        send(ObserverDto.Failure(false, it.message))
                    }
                }

        } catch (error : IOException) {
            send(ObserverDto.Failure(true, "Network Error: Kindly check your internet"))
        } catch (error : Exception) {
            send(ObserverDto.Failure(false, error.message))
        }
        awaitClose()
    }

    override suspend fun registerUser(regModel: RegistrationDto): Flow<ObserverDto<Boolean>> = channelFlow {
        try {
            send(ObserverDto.Loading())
            firebaseAuth.createUserWithEmailAndPassword(regModel.email, regModel.password)
                .addOnSuccessListener {
                    val userProfile = hashMapOf(
                        "email"     to regModel.email,
                        "fullName"  to regModel.profile.name,
                        "profileImage" to regModel.profile.profileImage,
                        "interests" to regModel.profile.interests,
                        "title"     to regModel.profile.title,
                        "profession" to regModel.profile.profession,
                        "twitter"   to regModel.profile.twitter,
                        "linkedIn"  to regModel.profile.linkedin,
                        "github"    to regModel.profile.github,
                        "behance"   to regModel.profile.behance,
                        "dribble"   to regModel.profile.dribble
                    )

                    firebaseFirestore.collection("profiles")
                        .add(userProfile)
                        .addOnSuccessListener {
                            launch {
                                send(ObserverDto.Success(true))
                            }
                        }
                        .addOnFailureListener {
                            launch {
                                send(ObserverDto.Failure(false, it.message))
                            }
                        }
                }
                .addOnFailureListener {
                    launch {
                        send(ObserverDto.Failure(false, it.message))
                    }
                }

        } catch (error : IOException) {
            send(ObserverDto.Failure(true, "Network Error: Kindly check your internet"))
        } catch (error : Exception) {
            send(ObserverDto.Failure(false, error.message))
        }
        awaitClose()
    }

    override suspend fun logoutUser(): Flow<ObserverDto<Boolean>> = flow {
        emit(ObserverDto.Loading())
        try {
            firebaseAuth.signOut()
            emit(ObserverDto.Success(true))
        } catch (error : IOException) {
            emit(ObserverDto.Failure(true, "Network Error: Kindly check your internet"))
        } catch (error : Exception) {
            emit(ObserverDto.Failure(false, error.message))
        }
    }
}
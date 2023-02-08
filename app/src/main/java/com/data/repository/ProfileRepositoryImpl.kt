package com.data.repository

import android.system.Os
import com.domain.models.ObserverDto
import com.domain.models.ProfileDto
import com.domain.repository.ProfileRepository
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

class ProfileRepositoryImpl @Inject constructor(
    private val firebaseFirestore: FirebaseFirestore
): ProfileRepository{
    override suspend fun getProfiles(): Flow<ObserverDto<List<ProfileDto>>> = channelFlow {
        try {
            firebaseFirestore.collection("profiles")
                .get()
                .addOnSuccessListener { snapshot : QuerySnapshot ->
                    val profileList : MutableList<ProfileDto> = mutableListOf()
                    snapshot.documents.forEach { document ->
                        val profile = ProfileDto(
                            profileImage = document["profileImage"] as String,
                            name = document["name"] as String,
                            title = document["title"] as String,
                            profession = document["profession"] as String,
                            description = document["description"] as String?,
                            instagram = document["instagram"] as String?,
                            twitter = document["twitter"] as String?,
                            linkedin = document["linkedIn"] as String?,
                            github = document["github"] as String?,
                            behance = document["behance"] as String?,
                            dribble = document["dribble"] as String?,
                            interests = document["interests"] as List<String>
                        )
                        profile.userId = document.id
                        profileList.add(profile)
                    }
                    launch {
                        send(ObserverDto.Success(profileList))
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

    override suspend fun getLeads(): Flow<ObserverDto<List<ProfileDto>>> = channelFlow {
        try {
            send(ObserverDto.Loading())
            firebaseFirestore.collection("profiles")
                .whereEqualTo("title", "Lead")
                .get()
                .addOnSuccessListener { snapshot : QuerySnapshot ->
                    val profileList : MutableList<ProfileDto> = mutableListOf()
                    snapshot.documents.forEach { document ->
                        val profile = ProfileDto(
                            profileImage = document["profileImage"] as String,
                            name = document["name"] as String,
                            title = document["title"] as String,
                            profession = document["profession"] as String,
                            description = document["description"] as String,
                            instagram = document["instagram"] as String?,
                            twitter = document["twitter"] as String?,
                            linkedin = document["linkedIn"] as String?,
                            github = document["github"] as String?,
                            behance = document["behance"] as String?,
                            dribble = document["dribble"] as String?,
                            interests = document["interests"] as List<String>
                        )
                        profile.userId = document.id
                        profileList.add(profile)
                    }
                    launch {
                        send(ObserverDto.Success(profileList))
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

    override suspend fun getLead(id: String): Flow<ObserverDto<ProfileDto>> = channelFlow {
        try {
            send(ObserverDto.Loading())
            firebaseFirestore.collection("profiles").document(id)
                .get()
                .addOnSuccessListener { snapshot : DocumentSnapshot ->
                    val profile = ProfileDto(
                        profileImage = snapshot["profileImage"] as String,
                        name = snapshot["name"] as String,
                        title = snapshot["title"] as String,
                        profession = snapshot["profession"] as String,
                        description = snapshot["description"] as String,
                        instagram = snapshot["instagram"] as String?,
                        twitter = snapshot["twitter"] as String?,
                        linkedin = snapshot["linkedIn"] as String?,
                        github = snapshot["github"] as String?,
                        behance = snapshot["behance"] as String?,
                        dribble = snapshot["dribble"] as String?,
                        interests = snapshot["interests"] as List<String>
                    )
                    profile.userId = snapshot.id

                    launch {
                        send(ObserverDto.Success(profile))
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

    override suspend fun getMember(): Flow<ObserverDto<List<ProfileDto>>> = channelFlow {

        try {
            firebaseFirestore.collection("profiles")
                .whereEqualTo("title", "Member")
                .get()
                .addOnSuccessListener { snapshot : QuerySnapshot ->
                    val profileList : MutableList<ProfileDto> = mutableListOf()
                    snapshot.documents.forEach { document ->
                        val profile = ProfileDto(
                            profileImage = document["profileImage"] as String,
                            name = document["name"] as String,
                            title = document["title"] as String,
                            profession = document["profession"] as String,
                            description = document["description"] as String,
                            instagram = document["instagram"] as String?,
                            twitter = document["twitter"] as String,
                            linkedin = document["linkedIn"] as String,
                            github = document["github"] as String,
                            behance = document["behance"] as String,
                            dribble = document["dribble"] as String,
                            interests = document["interests"] as List<String>
                        )
                        profile.userId = document.id
                        profileList.add(profile)
                    }
                    launch {
                        send(ObserverDto.Success(profileList))
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

    override suspend fun getMemberByProfession(profession: String): Flow<ObserverDto<List<ProfileDto>>> = channelFlow {

        try {
            firebaseFirestore.collection("profiles")
                .whereEqualTo("profession", profession)
                .get()
                .addOnSuccessListener { snapshot : QuerySnapshot ->
                    val profileList : MutableList<ProfileDto> = mutableListOf()
                    snapshot.documents.forEach { document ->
                        val profile = ProfileDto(
                            profileImage = document["profileImage"] as String,
                            name = document["name"] as String,
                            title = document["title"] as String,
                            profession = document["profession"] as String,
                            description = document["description"] as String,
                            instagram = document["instagram"] as String?,
                            twitter = document["twitter"] as String,
                            linkedin = document["linkedIn"] as String,
                            github = document["github"] as String,
                            behance = document["behance"] as String,
                            dribble = document["dribble"] as String,
                            interests = document["interests"] as List<String>
                        )
                        profile.userId = document.id
                        profileList.add(profile)
                    }
                    launch {
                        send(ObserverDto.Success(profileList))
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

    override suspend fun getProfileByEmail(email: String): Flow<ObserverDto<ProfileDto?>> = channelFlow {
        try {
            send(ObserverDto.Loading())
            firebaseFirestore.collection("profiles")
                .whereEqualTo("email", email)
                .get()
                .addOnSuccessListener { snapshot : QuerySnapshot? ->
                    if (snapshot != null && snapshot.documents.isNotEmpty()) {
                        val document = snapshot.documents[0]
                        val profile = ProfileDto(
                            profileImage = document["profileImage"] as String,
                            name = document["name"] as String,
                            title = document["title"] as String,
                            profession = document["profession"] as String,
                            description = document["description"] as String,
                            instagram = document["instagram"] as String?,
                            twitter = document["twitter"] as String?,
                            linkedin = document["linkedIn"] as String?,
                            github = document["github"] as String?,
                            behance = document["behance"] as String?,
                            dribble = document["dribble"] as String?,
                            interests = document["interests"] as List<String>
                        )
                        profile.userId = document.id

                        launch {
                            send(ObserverDto.Success(profile))
                        }
                    } else {
                        launch {
                            send(ObserverDto.Success(null))
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
}
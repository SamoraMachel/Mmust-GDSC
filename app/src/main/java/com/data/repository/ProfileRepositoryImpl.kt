package com.data.repository

import android.system.Os
import com.domain.models.ObserverDto
import com.domain.models.ProfileDto
import com.domain.repository.ProfileRepository
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.io.IOException
import javax.inject.Inject

class ProfileRepositoryImpl @Inject constructor(
    private val firebaseFirestore: FirebaseFirestore
): ProfileRepository{
    override suspend fun getProfiles(): StateFlow<ObserverDto<List<ProfileDto>>> {
        val profileState : MutableStateFlow<ObserverDto<List<ProfileDto>>> = MutableStateFlow(ObserverDto.Loading())

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
                            description = document["description"] as String,
                            twitter = document["twitter"] as String,
                            linkedin = document["linkedIn"] as String,
                            github = document["github"] as String,
                            behance = document["behance"] as String,
                            dribble = document["dribble"] as String
                        )
                        profileList.add(profile)
                    }
                    suspend {
                        profileState.emit(ObserverDto.Success(profileList))
                    }
                }
                .addOnFailureListener {
                    suspend {
                        profileState.emit(ObserverDto.Failure(false, it.message))
                    }
                }
        } catch (error : IOException) {
            profileState.emit(ObserverDto.Failure(true, "Network Error: Kindly check your internet"))
        } catch (error : Exception) {
            profileState.emit(ObserverDto.Failure(false, error.message))
        }
        return profileState
    }

    override suspend fun getLeads(): StateFlow<ObserverDto<List<ProfileDto>>> {
        val profileState : MutableStateFlow<ObserverDto<List<ProfileDto>>> = MutableStateFlow(ObserverDto.Loading())

        try {
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
                            twitter = document["twitter"] as String,
                            linkedin = document["linkedIn"] as String,
                            github = document["github"] as String,
                            behance = document["behance"] as String,
                            dribble = document["dribble"] as String
                        )
                        profileList.add(profile)
                    }
                    suspend {
                        profileState.emit(ObserverDto.Success(profileList))
                    }
                }
                .addOnFailureListener {
                    suspend {
                        profileState.emit(ObserverDto.Failure(false, it.message))
                    }
                }
        } catch (error : IOException) {
            profileState.emit(ObserverDto.Failure(true, "Network Error: Kindly check your internet"))
        } catch (error : Exception) {
            profileState.emit(ObserverDto.Failure(false, error.message))
        }
        return profileState
    }

    override suspend fun getMember(): StateFlow<ObserverDto<List<ProfileDto>>> {
        val profileState : MutableStateFlow<ObserverDto<List<ProfileDto>>> = MutableStateFlow(ObserverDto.Loading())

        try {
            firebaseFirestore.collection("profiles")
                .whereEqualTo("profession", "Members")
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
                            twitter = document["twitter"] as String,
                            linkedin = document["linkedIn"] as String,
                            github = document["github"] as String,
                            behance = document["behance"] as String,
                            dribble = document["dribble"] as String
                        )
                        profileList.add(profile)
                    }
                    suspend {
                        profileState.emit(ObserverDto.Success(profileList))
                    }
                }
                .addOnFailureListener {
                    suspend {
                        profileState.emit(ObserverDto.Failure(false, it.message))
                    }
                }
        } catch (error : IOException) {
            profileState.emit(ObserverDto.Failure(true, "Network Error: Kindly check your internet"))
        } catch (error : Exception) {
            profileState.emit(ObserverDto.Failure(false, error.message))
        }
        return profileState
    }

    override suspend fun getMemberByProfession(profession: String): StateFlow<ObserverDto<List<ProfileDto>>> {
        val profileState : MutableStateFlow<ObserverDto<List<ProfileDto>>> = MutableStateFlow(ObserverDto.Loading())

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
                            twitter = document["twitter"] as String,
                            linkedin = document["linkedIn"] as String,
                            github = document["github"] as String,
                            behance = document["behance"] as String,
                            dribble = document["dribble"] as String
                        )
                        profileList.add(profile)
                    }
                    suspend {
                        profileState.emit(ObserverDto.Success(profileList))
                    }
                }
                .addOnFailureListener {
                    suspend {
                        profileState.emit(ObserverDto.Failure(false, it.message))
                    }
                }
        } catch (error : IOException) {
            profileState.emit(ObserverDto.Failure(true, "Network Error: Kindly check your internet"))
        } catch (error : Exception) {
            profileState.emit(ObserverDto.Failure(false, error.message))
        }
        return profileState
    }
}
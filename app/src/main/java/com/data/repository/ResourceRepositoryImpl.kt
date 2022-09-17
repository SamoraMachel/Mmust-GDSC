package com.data.repository

import com.domain.models.ObserverDto
import com.domain.models.ResourceDto
import com.domain.models.SessionDto
import com.domain.repository.ResourceRepository
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.io.IOException
import javax.inject.Inject

class ResourceRepositoryImpl @Inject constructor(
    private val firebaseFirestore: FirebaseFirestore
): ResourceRepository{
    override suspend fun getResources(): StateFlow<ObserverDto<List<ResourceDto>>> {
        val resourceState : MutableStateFlow<ObserverDto<List<ResourceDto>>> = MutableStateFlow(ObserverDto.Loading())

        try {
            firebaseFirestore.collection("sessions")
                .get()
                .addOnSuccessListener { snapshot : QuerySnapshot ->
                    val resourceList : MutableList<ResourceDto> = mutableListOf()
                    snapshot.documents.forEach { document ->
                        val resource = ResourceDto(
                            title = document["title"] as String,
                            link = document["link"] as String,
                            description = document["description"] as String,
                            level = document["level"] as String,
                            image = document["image"] as String?,
                            isVideo = document["isVideo"] as Boolean
                        )
                        resourceList.add(resource)
                    }
                    suspend {
                        resourceState.emit(ObserverDto.Success(resourceList))
                    }
                }
                .addOnFailureListener {
                    suspend {
                        resourceState.emit(ObserverDto.Failure(false, it.message))
                    }
                }
        } catch (error : IOException) {
            resourceState.emit(ObserverDto.Failure(true, "Network Error: Kindly check your internet"))
        } catch (error : Exception) {
            resourceState.emit(ObserverDto.Failure(false, error.message))
        }
        return resourceState
    }

    override suspend fun getResourceByLevel(level: String): StateFlow<ObserverDto<List<ResourceDto>>> {
        val resourceState : MutableStateFlow<ObserverDto<List<ResourceDto>>> = MutableStateFlow(ObserverDto.Loading())

        try {
            firebaseFirestore.collection("sessions")
                .whereEqualTo("level", level)
                .get()
                .addOnSuccessListener { snapshot : QuerySnapshot ->
                    val resourceList : MutableList<ResourceDto> = mutableListOf()
                    snapshot.documents.forEach { document ->
                        val resource = ResourceDto(
                            title = document["title"] as String,
                            link = document["link"] as String,
                            description = document["description"] as String,
                            level = document["level"] as String,
                            image = document["image"] as String?,
                            isVideo = document["isVideo"] as Boolean
                        )
                        resourceList.add(resource)
                    }
                    suspend {
                        resourceState.emit(ObserverDto.Success(resourceList))
                    }
                }
                .addOnFailureListener {
                    suspend {
                        resourceState.emit(ObserverDto.Failure(false, it.message))
                    }
                }
        } catch (error : IOException) {
            resourceState.emit(ObserverDto.Failure(true, "Network Error: Kindly check your internet"))
        } catch (error : Exception) {
            resourceState.emit(ObserverDto.Failure(false, error.message))
        }
        return resourceState
    }
}
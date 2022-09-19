package com.data.repository

import android.util.Log
import com.domain.models.LevelDto
import com.domain.models.ObserverDto
import com.domain.models.ResourceDto
import com.domain.repository.ResourceRepository
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

class ResourceRepositoryImpl @Inject constructor(
    private val firebaseFirestore: FirebaseFirestore
): ResourceRepository{
    override suspend fun getResources(): Flow<ObserverDto<List<ResourceDto>>> = channelFlow {
        try {
            send(ObserverDto.Loading())
            firebaseFirestore.collection("resources")
                .get()
                .addOnSuccessListener { snapshot : QuerySnapshot ->
                    val resourceList : MutableList<ResourceDto> = mutableListOf()
                    snapshot.documents.forEach { document ->
                        val resource = ResourceDto(
                            title = document["title"] as String,
                            link = document["link"] as String,
                            track_title = document["track_title"] as String,
                            description = document["description"] as String,
                            level = document["level"] as String,
                            image = document["image"] as String?,
                            isVideo = document["isVideo"] as Boolean
                        )
                        resourceList.add(resource)
                    }
                    launch {
                        Log.d("ResourceRepository", "getResources: ${resourceList}")
                        send(ObserverDto.Success(resourceList))
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

    override suspend fun getResourceByLevel(level: String, track: String): Flow<ObserverDto<List<ResourceDto>>> = channelFlow {
        try {
            send(ObserverDto.Loading())
            firebaseFirestore.collection("resources")
                .whereEqualTo("track_title", track)
                .whereEqualTo("level", level)
                .get()
                .addOnSuccessListener { snapshot : QuerySnapshot ->
                    val resourceList : MutableList<ResourceDto> = mutableListOf()
                    snapshot.documents.forEach { document ->
                        val resource = ResourceDto(
                            title = document["title"] as String,
                            link = document["link"] as String,
                            track_title = document["track_title"] as String,
                            description = document["description"] as String,
                            level = document["level"] as String,
                            image = document["image"] as String?,
                            isVideo = document["isVideo"] as Boolean
                        )
                        resourceList.add(resource)
                    }
                    launch {
                        send(ObserverDto.Success(resourceList))
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

    override suspend fun getLevels(): Flow<ObserverDto<List<LevelDto>>> = channelFlow {
        try {
            send(ObserverDto.Loading())
            firebaseFirestore.collection("resources")
                .get()
                .addOnSuccessListener { snapshot : QuerySnapshot ->
                    val levelList : MutableList<LevelDto> = mutableListOf()
                    snapshot.documents.forEach { document ->
                        val level = LevelDto(
                            title = document["title"] as String,
                            description = document["description"] as String
                        )
                        levelList.add(level)
                    }
                    launch {
                        send(ObserverDto.Success(levelList))
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
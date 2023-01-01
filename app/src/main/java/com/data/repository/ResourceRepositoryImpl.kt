package com.data.repository

import android.util.Log
import com.app.Constants
import com.domain.models.LevelDto
import com.domain.models.ObserverDto
import com.domain.models.ResourceDto
import com.domain.repository.ResourceRepository
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
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
            firebaseFirestore.collection(Constants.RESOURCE_STRING)
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
            firebaseFirestore.collection(Constants.RESOURCE_STRING)
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
            firebaseFirestore.collection(Constants.RESOURCE_STRING)
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

    override suspend fun addResource(resource: ResourceDto): Flow<ObserverDto<Boolean>> = channelFlow {
        try {
            send(ObserverDto.Loading())

            val resourceData = hashMapOf(
                "description" to resource.description,
                "image" to resource.image,
                "isVideo" to resource.isVideo,
                "level" to resource.level,
                "link" to resource.link,
                "title" to resource.title,
                "track_title" to resource.track_title
            )

            firebaseFirestore.collection(Constants.RESOURCE_STRING)
                .add(resourceData)
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

    override suspend fun editResource(
        id: String,
        resource: ResourceDto
    ): Flow<ObserverDto<Boolean>> = channelFlow{
        try {
            send(ObserverDto.Loading())

            val resourceData : MutableMap<String, Any?> = mutableMapOf(
                "description" to resource.description,
                "image" to resource.image,
                "isVideo" to resource.isVideo,
                "level" to resource.level,
                "link" to resource.link,
                "title" to resource.title,
                "track_title" to resource.track_title
            )

            firebaseFirestore.collection(Constants.RESOURCE_STRING).document(id)
                .update(resourceData)
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

    override suspend fun deleteResource(id: String): Flow<ObserverDto<Boolean>> = channelFlow{
        try {
            send(ObserverDto.Loading())

            firebaseFirestore.collection(Constants.RESOURCE_STRING).document(id)
                .delete()
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
}
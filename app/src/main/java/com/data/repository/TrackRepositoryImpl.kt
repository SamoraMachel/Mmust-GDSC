package com.data.repository

import android.util.Log
import com.app.Constants
import com.domain.models.ObserverDto
import com.domain.models.ProfileDto
import com.domain.models.SessionDto
import com.domain.models.TrackDto
import com.domain.repository.ProfileRepository
import com.domain.repository.TrackRepository
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.*
import java.io.IOException
import javax.inject.Inject

class TrackRepositoryImpl @Inject constructor(
    private val firebaseFirestore: FirebaseFirestore,
    private val profileRepository: ProfileRepository
) : TrackRepository{
    private val TAG : String = "TrackRepository"

    override suspend fun getTracks(): Flow<ObserverDto<List<TrackDto>>> = channelFlow {
        val trackList : MutableList<TrackDto> = mutableListOf()
        try {
            send(ObserverDto.Loading())
            firebaseFirestore.collection(Constants.TRACKS_STRING)
                .get()
                .addOnSuccessListener { snapshot : QuerySnapshot ->
                    snapshot.documents.forEach { document ->
                        val track = TrackDto(
                            title = document["title"] as String,
                            description = document["description"] as String,
                            image = document["image"] as String,
                            levels = document["levels"] as Map<String, String>,
                            lead = document["lead"] as String,
                            day = document["day"] as String,
                            timeRange = document["timeRange"] as String
                        )
                        trackList.add(track)

                        launch {
                            send(ObserverDto.Success(trackList))
                        }
                    }
                }
                .addOnFailureListener { error ->
                    launch {
                        send(ObserverDto.Failure(false, error.message))
                    }
                }

        } catch (error : IOException) {
            send(ObserverDto.Failure(true, "Network Error: Kindly check your internet"))
            Log.d(TAG, "IOError -> getTracks: ${error.message}")
        } catch (error : Exception) {
            send(ObserverDto.Failure(false, error.message))
            Log.d(TAG, "General Error -> getTracks: ${error.message}")
        }
        awaitClose()
    }

    override suspend fun getTrackByName(name: String): Flow<ObserverDto<TrackDto>> = channelFlow {
        try {
            send(ObserverDto.Loading())

            firebaseFirestore.collection(Constants.TRACKS_STRING)
                .whereEqualTo("title", name)
                .get()
                .addOnSuccessListener { snapshot: QuerySnapshot ->
                    if (snapshot != null && snapshot.documents.isNotEmpty()) {
                        val document = snapshot.documents[0]
                        val track = TrackDto(
                            title = document["title"] as String,
                            description = document["description"] as String,
                            image = document["image"] as String,
                            levels = document["levels"] as Map<String, String>,
                            lead = document["lead"] as String,
                            day = document["day"] as String,
                            timeRange = document["timeRange"] as String
                        )

                        launch {
                            send(ObserverDto.Success(track))
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
            Log.d(TAG, "IOError -> getTracks: ${error.message}")
        } catch (error : Exception) {
            send(ObserverDto.Failure(false, error.message))
            Log.d(TAG, "General Error -> getTracks: ${error.message}")
        }
        awaitClose()
    }

    override suspend fun getSessions(): Flow<ObserverDto<List<SessionDto>>> = channelFlow {
        val sessionsList : MutableList<SessionDto> = mutableListOf()

        try {
            send(ObserverDto.Loading())

            firebaseFirestore.collection(Constants.TRACKS_STRING)
                .get()
                .addOnSuccessListener { snapshot : QuerySnapshot ->
                    snapshot.documents.forEach { document ->
                        val track = TrackDto(
                            title = document["title"] as String,
                            description = document["description"] as String,
                            image = document["image"] as String,
                            levels = document["levels"] as Map<String, String>,
                            lead = document["lead"] as String,
                            day = document["day"] as String,
                            timeRange = document["timeRange"] as String
                        )

                        Log.d("TrackRespository", "getSessions: TrackSnapshot -> ${track} ")

                        launch {
                            profileRepository.getLead(track.lead).collect { observer ->
                                when(observer) {
                                    is ObserverDto.Failure -> send(ObserverDto.Failure(false, observer.message))
                                    is ObserverDto.Loading -> send(ObserverDto.Loading())
                                    is ObserverDto.Success -> {
                                        observer.data?.let { profile ->
                                            sessionsList.add(
                                                SessionDto(track, profile)
                                            )
                                        }
                                    }
                                }
                            }
                            send(ObserverDto.Success(sessionsList))
                            Log.d("TrackRepository", "getSessions: ProfileSnapshot -> ${sessionsList}")
                        }

                        }
                        launch {
                            send(ObserverDto.Success(sessionsList))
                        }

                    }
                .addOnFailureListener { error ->
                    launch {
                        send(ObserverDto.Failure(false, error.message))
                    }
                }
        } catch (error : IOException) {
            send(ObserverDto.Failure(true, "Network Error: Kindly check your internet"))
            Log.d(TAG, "IOError -> getTracks: ${error.message}")
        } catch (error : Exception) {
            send(ObserverDto.Failure(false, error.message))
            Log.d(TAG, "General Error -> getTracks: ${error.message}")
        }
        awaitClose()
    }

    override suspend fun addTrack(track: TrackDto): Flow<ObserverDto<Boolean>> = channelFlow {
        try {
            send(ObserverDto.Loading())

            val trackData = hashMapOf(
                "day" to track.day,
                "lead" to track.lead,
                "levels" to track.levels,
                "timeRange" to track.timeRange,
                "title" to track.title,
                "image" to track.image,
                "description" to track.description
            )

            firebaseFirestore.collection(Constants.TRACKS_STRING)
                .add(trackData)
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

    override suspend fun editTrack(id: String, track: TrackDto): Flow<ObserverDto<Boolean>> = channelFlow {
        try {
            send(ObserverDto.Loading())

            val trackData = hashMapOf(
                "day" to track.day,
                "lead" to track.lead,
                "levels" to track.levels,
                "timeRange" to track.timeRange,
                "title" to track.title,
                "image" to track.image,
                "description" to track.description
            )

            firebaseFirestore.collection(Constants.TRACKS_STRING).document(id)
                .update(trackData)
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

    override suspend fun deleteTrack(id: String): Flow<ObserverDto<Boolean>> = channelFlow {
        try {
            send(ObserverDto.Loading())

            firebaseFirestore.collection(Constants.TRACKS_STRING).document(id)
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
package com.data.repository

import android.util.Log
import com.domain.models.ObserverDto
import com.domain.models.TrackDto
import com.domain.repository.TrackRepository
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.*
import java.io.IOException
import javax.inject.Inject

class TrackRepositoryImpl @Inject constructor(
    private val firebaseFirestore: FirebaseFirestore
) : TrackRepository{
    private val TAG : String = "TrackRepository"

    override suspend fun getTracks(): Flow<ObserverDto<List<TrackDto>>> = channelFlow {
        val trackList : MutableList<TrackDto> = mutableListOf()
        try {
            send(ObserverDto.Loading())
            firebaseFirestore.collection("tracks")
                .get()
                .addOnSuccessListener { snapshot : QuerySnapshot ->
                    snapshot.documents.forEach { document ->
                        val track = TrackDto(
                            title = document["title"] as String,
                            description = document["description"] as String,
                            image = document["image"] as String,
                            levels = document["levels"] as Map<String, String>,
                            lead = document["lead"] as String,
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
}
package com.data.repository

import com.domain.models.ObserverDto
import com.domain.models.SessionDto
import com.domain.models.TrackDto
import com.domain.repository.TrackRepository
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.io.IOException
import javax.inject.Inject

class TrackRepositoryImpl @Inject constructor(
    private val firebaseFirestore: FirebaseFirestore
) : TrackRepository{
    override suspend fun getTracks(): StateFlow<ObserverDto<List<TrackDto>>> {
        val trackState : MutableStateFlow<ObserverDto<List<TrackDto>>> = MutableStateFlow(ObserverDto.Loading())

        try {
            firebaseFirestore.collection("tracks")
                .get()
                .addOnSuccessListener { snapshot : QuerySnapshot ->
                    val trackList : MutableList<TrackDto> = mutableListOf()
                    snapshot.documents.forEach { document ->
                        val track = TrackDto(
                            title = document["title"] as String,
                            description = document["description"] as String,
                            image = document["image"] as String
                        )
                        trackList.add(track)
                    }
                    suspend {
                        trackState.emit(ObserverDto.Success(trackList))
                    }
                }
                .addOnFailureListener {
                    suspend {
                        trackState.emit(ObserverDto.Failure(false, it.message))
                    }
                }
        } catch (error : IOException) {
            trackState.emit(ObserverDto.Failure(true, "Network Error: Kindly check your internet"))
        } catch (error : Exception) {
            trackState.emit(ObserverDto.Failure(false, error.message))
        }
        return trackState
    }
}
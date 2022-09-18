package com.data.repository

import com.domain.models.ObserverDto
import com.domain.models.EventDto
import com.domain.repository.EventRepository
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.io.IOException
import javax.inject.Inject

class EventRepositoryImpl @Inject constructor(
    private val firebaseFirestore: FirebaseFirestore
) : EventRepository {
    override suspend fun getUpcomingSession(): Flow<ObserverDto<List<EventDto>>> {
        val sessionState : MutableStateFlow<ObserverDto<List<EventDto>>> = MutableStateFlow(ObserverDto.Loading())

        try {
            firebaseFirestore.collection("sessions")
                .whereGreaterThan("date", "")
                .get()
                .addOnSuccessListener { snapshot : QuerySnapshot ->
                    val sessionList : MutableList<EventDto> = mutableListOf()
                    snapshot.documents.forEach { document ->
                        val session = EventDto(
                            title = document["title"] as String,
                            description = document["description"] as String,
                            link = document["link"] as String,
                            date = document["date"] as String,
                        )
                        sessionList.add(session)
                    }
                    suspend {
                        sessionState.emit(ObserverDto.Success(sessionList))
                    }
                }
                .addOnFailureListener {
                    suspend {
                        sessionState.emit(ObserverDto.Failure(false, it.message))
                    }
                }
        } catch (error : IOException) {
            sessionState.emit(ObserverDto.Failure(true, "Network Error: Kindly check your internet"))
        } catch (error : Exception) {
            sessionState.emit(ObserverDto.Failure(false, error.message))
        }
        return sessionState
    }

    override suspend fun getPastSession(): Flow<ObserverDto<List<EventDto>>> {
        val sessionState : MutableStateFlow<ObserverDto<List<EventDto>>> = MutableStateFlow(ObserverDto.Loading())

        try {
            firebaseFirestore.collection("sessions")
                .whereLessThan("date", "")
                .get()
                .addOnSuccessListener { snapshot : QuerySnapshot ->
                    val sessionList : MutableList<EventDto> = mutableListOf()
                    snapshot.documents.forEach { document ->
                        val session = EventDto(
                            title = document["title"] as String,
                            description = document["description"] as String,
                            link = document["link"] as String,
                            date = document["date"] as String,
                        )
                        sessionList.add(session)
                    }
                    suspend {
                        sessionState.emit(ObserverDto.Success(sessionList))
                    }
                }
                .addOnFailureListener {
                    suspend {
                        sessionState.emit(ObserverDto.Failure(false, it.message))
                    }
                }
        } catch (error : IOException) {
            sessionState.emit(ObserverDto.Failure(true, "Network Error: Kindly check your internet"))
        } catch (error : Exception) {
            sessionState.emit(ObserverDto.Failure(false, error.message))
        }
        return sessionState
    }
}
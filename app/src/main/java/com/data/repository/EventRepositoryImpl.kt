package com.data.repository

import com.app.Constants
import com.domain.models.ObserverDto
import com.domain.models.EventDto
import com.domain.repository.EventRepository
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

class EventRepositoryImpl @Inject constructor(
    private val firebaseFirestore: FirebaseFirestore
) : EventRepository {
    override suspend fun getUpcomingSession(): Flow<ObserverDto<List<EventDto>>> = channelFlow {
        try {
            send(ObserverDto.Loading())
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
                    launch {
                        send(ObserverDto.Success(sessionList))
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
    }

    override suspend fun getPastSession(): Flow<ObserverDto<List<EventDto>>> = channelFlow {
        try {
            send(ObserverDto.Loading())
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
                    launch {
                        send(ObserverDto.Success(sessionList))
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
    }

    override suspend fun addEvent(event: EventDto): Flow<ObserverDto<Boolean>> = channelFlow {
        try {
            send(ObserverDto.Loading())

            val eventData : MutableMap<String, Any?> = mutableMapOf(
                "title" to event.title,
                "date" to event.date,
                "link" to event.link,
                "description" to event.description
            )

            firebaseFirestore.collection(Constants.EVENTS_STRING)
                .add(eventData)
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

    override suspend fun editEvent(id: String, event: EventDto): Flow<ObserverDto<Boolean>> = channelFlow {
        try {
            send(ObserverDto.Loading())

            val eventData : MutableMap<String, Any?> = mutableMapOf(
                "title" to event.title,
                "date" to event.date,
                "link" to event.link,
                "description" to event.description
            )

            firebaseFirestore.collection(Constants.EVENTS_STRING).document(id)
                .update(eventData)
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

    override suspend fun deleteEvent(id: String): Flow<ObserverDto<Boolean>> = channelFlow {
        try {
            send(ObserverDto.Loading())

            firebaseFirestore.collection(Constants.EVENTS_STRING).document(id)
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
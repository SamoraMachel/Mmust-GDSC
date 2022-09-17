package com.data.repository

import com.domain.models.ObserverDto
import com.domain.models.ProfileDto
import com.domain.models.SessionDto
import com.domain.repository.SessionRepository
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.google.type.DateTime
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.io.IOException
import javax.inject.Inject

class SessionRepositoryImpl @Inject constructor(
    private val firebaseFirestore: FirebaseFirestore
) : SessionRepository {
    override suspend fun getUpcomingSession(): StateFlow<ObserverDto<List<SessionDto>>> {
        val sessionState : MutableStateFlow<ObserverDto<List<SessionDto>>> = MutableStateFlow(ObserverDto.Loading())

        try {
            firebaseFirestore.collection("sessions")
                .whereGreaterThan("date", "")
                .get()
                .addOnSuccessListener { snapshot : QuerySnapshot ->
                    val sessionList : MutableList<SessionDto> = mutableListOf()
                    snapshot.documents.forEach { document ->
                        val session = SessionDto(
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

    override suspend fun getPastSession(): StateFlow<ObserverDto<List<SessionDto>>> {
        val sessionState : MutableStateFlow<ObserverDto<List<SessionDto>>> = MutableStateFlow(ObserverDto.Loading())

        try {
            firebaseFirestore.collection("sessions")
                .whereLessThan("date", "")
                .get()
                .addOnSuccessListener { snapshot : QuerySnapshot ->
                    val sessionList : MutableList<SessionDto> = mutableListOf()
                    snapshot.documents.forEach { document ->
                        val session = SessionDto(
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
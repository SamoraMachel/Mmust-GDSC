package com.data.repository

import android.util.Log
import androidx.core.net.toUri
import com.domain.models.ObserverDto
import com.domain.models.ProgressiveDataDto
import com.domain.repository.FirebaseUtilsFunctions
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import java.io.File
import java.io.IOException
import javax.inject.Inject

class FirebaseUtilsFunctionsImpl @Inject constructor(
    private val firebaseStorage: FirebaseStorage
) : FirebaseUtilsFunctions {
    private val TAG = "FirebaseUtils"
    override suspend fun uploadFile(file : File, uploadLocation : String): Flow<ObserverDto<ProgressiveDataDto<String>>> = channelFlow<ObserverDto<ProgressiveDataDto<String>>> {
        Log.d(TAG, "uploadFile: Starting Upload")
        val progress = ProgressiveDataDto<String>()
        send(ObserverDto.Loading(data = progress))
        val storageRef = firebaseStorage.getReference().storage.reference
        val fileReference = storageRef.child(
            uploadLocation + System.currentTimeMillis().toString() + "." + file.extension
        )
        try {
            fileReference.putFile(file.toUri())
                .addOnProgressListener { taskSnapshot ->
                    val percentageOfProgress = (taskSnapshot.bytesTransferred / taskSnapshot.totalByteCount) * 100
                    progress.progress = percentageOfProgress.toInt()
                    launch {
                        send(ObserverDto.Loading(progress))
                    }
                }
                .addOnSuccessListener { taskSnapshot ->
                    progress.data = fileReference.downloadUrl.toString()
                    launch {
                        send(ObserverDto.Success(progress))
                    }
                }
                .addOnFailureListener {
                    launch {
                        send(ObserverDto.Failure(false, it.message))
                    }
                }
        }  catch (error : IOException) {
            send(ObserverDto.Failure(true, "Network Error: Kindly check your internet"))
        } catch (error : Exception) {
            send(ObserverDto.Failure(false, error.message))
        }
        awaitClose()
    }

}
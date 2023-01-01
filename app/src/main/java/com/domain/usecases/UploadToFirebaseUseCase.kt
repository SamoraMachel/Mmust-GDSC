package com.domain.usecases

import com.domain.models.ObserverDto
import com.domain.models.ProgressiveDataDto
import com.domain.repository.FirebaseUtilsFunctions
import kotlinx.coroutines.flow.Flow
import java.io.File
import javax.inject.Inject

class UploadToFirebaseUseCase @Inject constructor(
    private val firebaseUtilsFunctions: FirebaseUtilsFunctions
) {
    suspend operator fun invoke(file: File, uploadLocation : String): Flow<ObserverDto<ProgressiveDataDto<String>>> {
        return firebaseUtilsFunctions.uploadFile(file, uploadLocation)
    }

    suspend fun uploadProfileImage(file: File): Flow<ObserverDto<ProgressiveDataDto<String>>> {
        return firebaseUtilsFunctions.uploadFile(file, "profiles/")
    }

    suspend fun uploadResourceImage(file: File): Flow<ObserverDto<ProgressiveDataDto<String>>> {
        return firebaseUtilsFunctions.uploadFile(file, "resources/")
    }

    suspend fun uploadResourceFiles(file: File): Flow<ObserverDto<ProgressiveDataDto<String>>> {
        return firebaseUtilsFunctions.uploadFile(file, "resources/files/")
    }

    suspend fun uploadTrackIcons(file: File): Flow<ObserverDto<ProgressiveDataDto<String>>> {
        return firebaseUtilsFunctions.uploadFile(file, "icons")
    }
}
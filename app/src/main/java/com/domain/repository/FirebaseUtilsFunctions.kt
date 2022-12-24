package com.domain.repository

import com.domain.models.ObserverDto
import com.domain.models.ProgressiveDataDto
import kotlinx.coroutines.flow.Flow
import java.io.File

interface FirebaseUtilsFunctions {
    suspend fun uploadFile(file : File, uploadLocation : String = "resources/files/") : Flow<ObserverDto<ProgressiveDataDto<String>>>
}
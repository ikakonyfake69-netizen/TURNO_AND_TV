package com.example.turno_and_tv.core.sync

import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.coroutines.tasks.await

class FcmTokenManager {

    suspend fun obtenerToken(): String? {
        return try {
            FirebaseMessaging
                .getInstance()
                .token
                .await()
        } catch (e: Exception) {
            null
        }
    }
}
package com.example.turno_and_tv.core.data

import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class DispositivoRepository(
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()
) {

    private val dispositivosCollection =
        firestore.collection("dispositivos")

    suspend fun guardarToken(
        dispositivoId: String,
        token: String
    ) {

        val datos = hashMapOf(
            "dispositivoId" to dispositivoId,
            "token" to token
        )

        dispositivosCollection
            .document(dispositivoId)
            .set(datos)
            .await()
    }
}
package com.example.turno_and_tv.core.data

import com.example.turno_and_tv.core.model.EstadoTurno
import com.example.turno_and_tv.core.model.Paciente
import com.example.turno_and_tv.core.model.Turno
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

class FirestoreTurnoRepository(
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()
) : TurnoRepository {

    private val turnosCollection = firestore.collection("turnos")

    override suspend fun crearTurno(turno: Turno) {

        val datosTurno = hashMapOf(
            "id" to turno.id,
            "numero" to turno.numero,
            "pacienteId" to turno.paciente.id,
            "pacienteNombre" to turno.paciente.nombre,
            "motivo" to turno.motivo,
            "estado" to turno.estado.name,
            "dispositivoId" to turno.dispositivoId
        )

        turnosCollection
            .document(turno.id)
            .set(datosTurno)
            .await()
    }

    override fun observarTurnos(): Flow<List<Turno>> = callbackFlow {

        val listener = turnosCollection
            .orderBy("numero", Query.Direction.ASCENDING)
            .addSnapshotListener { snapshot, error ->

                if (error != null) {
                    close(error)
                    return@addSnapshotListener
                }

                val turnos = snapshot?.documents?.mapNotNull { documento ->

                    try {

                        val id =
                            documento.getString("id")
                                ?: documento.id

                        val numero =
                            documento.getLong("numero")
                                ?.toInt()
                                ?: 0

                        val pacienteId =
                            documento.getString("pacienteId")
                                ?: ""

                        val pacienteNombre =
                            documento.getString("pacienteNombre")
                                ?: ""

                        val motivo =
                            documento.getString("motivo")
                                ?: ""

                        val estadoTexto =
                            documento.getString("estado")
                                ?: EstadoTurno.ESPERANDO.name

                        val dispositivoId =
                            documento.getString("dispositivoId")
                                ?: ""

                        val estado = try {

                            EstadoTurno.valueOf(
                                estadoTexto
                            )

                        } catch (e: Exception) {

                            EstadoTurno.ESPERANDO
                        }

                        Turno(
                            id = id,
                            numero = numero,
                            paciente = Paciente(
                                id = pacienteId,
                                nombre = pacienteNombre
                            ),
                            motivo = motivo,
                            estado = estado,
                            dispositivoId = dispositivoId
                        )

                    } catch (e: Exception) {

                        null
                    }
                } ?: emptyList()

                trySend(turnos)
            }

        awaitClose {
            listener.remove()
        }
    }

    override suspend fun actualizarEstadoTurno(
        turnoId: String,
        nuevoEstado: EstadoTurno
    ) {

        turnosCollection
            .document(turnoId)
            .update(
                "estado",
                nuevoEstado.name
            )
            .await()
    }

    override suspend fun eliminarTurno(
        turnoId: String
    ) {

        turnosCollection
            .document(turnoId)
            .delete()
            .await()
    }
}
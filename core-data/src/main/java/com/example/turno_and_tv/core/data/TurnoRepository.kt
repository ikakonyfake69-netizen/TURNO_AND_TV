package com.example.turno_and_tv.core.data

import com.example.turno_and_tv.core.model.EstadoTurno
import com.example.turno_and_tv.core.model.Turno
import kotlinx.coroutines.flow.Flow

interface TurnoRepository {

    suspend fun crearTurno(turno: Turno)

    fun observarTurnos(): Flow<List<Turno>>

    suspend fun actualizarEstadoTurno(
        turnoId: String,
        nuevoEstado: EstadoTurno
    )

    suspend fun eliminarTurno(
        turnoId: String
    )
}
package com.example.turno_and_tv.model

data class Turno(
    val id: String = "",
    val numero: Int = 0,
    val paciente: Paciente = Paciente(),
    val motivo: String = "",
    val estado: EstadoTurno = EstadoTurno.ESPERANDO
)
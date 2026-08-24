package com.example.turno_and_tv.core.model

data class Turno(
    val id: String = "",
    val numero: Int = 0,
    val paciente: Paciente = Paciente(),
    val motivo: String = "",
    val estado: EstadoTurno = EstadoTurno.ESPERANDO,
    val dispositivoId: String = ""
)
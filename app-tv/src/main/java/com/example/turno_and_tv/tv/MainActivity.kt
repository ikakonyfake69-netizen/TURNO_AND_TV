package com.example.turno_and_tv.tv

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.tv.material3.Card
import androidx.tv.material3.ExperimentalTvMaterial3Api
import androidx.tv.material3.Text
import com.example.turno_and_tv.core.data.FirestoreTurnoRepository
import com.example.turno_and_tv.core.model.EstadoTurno
import com.example.turno_and_tv.tv.ui.theme.TurnoMedTVTheme

class MainActivity : ComponentActivity() {

    @OptIn(ExperimentalTvMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {

            val repository = remember {
                FirestoreTurnoRepository()
            }

            val turnos by repository
                .observarTurnos()
                .collectAsState(initial = emptyList())

            val turnoAtendiendo = turnos.firstOrNull {
                it.estado == EstadoTurno.ATENDIENDO
            }

            val turnoLlamado = turnos.firstOrNull {
                it.estado == EstadoTurno.LLAMADO
            }

            val siguienteTurno = turnos
                .filter {
                    it.estado == EstadoTurno.ESPERANDO
                }
                .minByOrNull {
                    it.numero
                }

            TurnoMedTVTheme {

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(48.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {

                    Text(
                        text = "TurnoMed",
                        fontSize = 48.sp
                    )

                    Text(
                        text = "Sala de espera",
                        fontSize = 28.sp
                    )

                    Spacer(
                        modifier = Modifier.height(40.dp)
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(30.dp)
                    ) {

                        Card(
                            onClick = {},
                            modifier = Modifier.weight(1f)
                        ) {

                            Column(
                                modifier = Modifier.padding(32.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {

                                Text(
                                    text = "EN ATENCIÓN",
                                    fontSize = 24.sp
                                )

                                Spacer(
                                    modifier = Modifier.height(16.dp)
                                )

                                if (turnoAtendiendo != null) {

                                    Text(
                                        text = "#${turnoAtendiendo.numero}",
                                        fontSize = 52.sp
                                    )

                                    Text(
                                        text = turnoAtendiendo.paciente.nombre,
                                        fontSize = 28.sp
                                    )

                                } else {

                                    Text(
                                        text = "Sin turno",
                                        fontSize = 28.sp
                                    )
                                }
                            }
                        }

                        Card(
                            onClick = {},
                            modifier = Modifier.weight(1f)
                        ) {

                            Column(
                                modifier = Modifier.padding(32.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {

                                Text(
                                    text = "TURNO LLAMADO",
                                    fontSize = 24.sp
                                )

                                Spacer(
                                    modifier = Modifier.height(16.dp)
                                )

                                if (turnoLlamado != null) {

                                    Text(
                                        text = "#${turnoLlamado.numero}",
                                        fontSize = 52.sp
                                    )

                                    Text(
                                        text = turnoLlamado.paciente.nombre,
                                        fontSize = 28.sp
                                    )

                                } else {

                                    Text(
                                        text = "Sin turno",
                                        fontSize = 28.sp
                                    )
                                }
                            }
                        }
                    }

                    Spacer(
                        modifier = Modifier.height(30.dp)
                    )

                    Text(
                        text = "SIGUIENTE TURNO",
                        fontSize = 22.sp
                    )

                    if (siguienteTurno != null) {

                        Text(
                            text = "#${siguienteTurno.numero} - ${siguienteTurno.paciente.nombre}",
                            fontSize = 32.sp
                        )

                    } else {

                        Text(
                            text = "No hay turnos en espera",
                            fontSize = 28.sp
                        )
                    }
                }
            }
        }
    }
}
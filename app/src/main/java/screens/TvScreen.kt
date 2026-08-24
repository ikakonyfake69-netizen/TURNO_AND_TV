package screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.turno_and_tv.model.Turno

@Composable
fun TvScreen(
    turnoAtendiendo: Turno?,
    turnoLlamado: Turno?,
    siguienteTurno: Turno?
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(40.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Text(
            text = "TurnoMed",
            fontSize = 36.sp
        )

        Spacer(
            modifier = Modifier.height(12.dp)
        )

        Text(
            text = "Sala de espera",
            fontSize = 22.sp
        )

        Spacer(
            modifier = Modifier.height(32.dp)
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(24.dp)
        ) {

            Card(
                modifier = Modifier.weight(1f)
            ) {

                Column(
                    modifier = Modifier.padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Text(
                        text = "EN ATENCIÓN",
                        fontSize = 20.sp
                    )

                    Spacer(
                        modifier = Modifier.height(12.dp)
                    )

                    if (turnoAtendiendo != null) {

                        Text(
                            text = "#${turnoAtendiendo.numero}",
                            fontSize = 42.sp
                        )

                        Text(
                            text = turnoAtendiendo.paciente.nombre,
                            fontSize = 24.sp
                        )

                    } else {

                        Text(
                            text = "Sin turno",
                            fontSize = 24.sp
                        )
                    }
                }
            }

            Card(
                modifier = Modifier.weight(1f)
            ) {

                Column(
                    modifier = Modifier.padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Text(
                        text = "TURNO LLAMADO",
                        fontSize = 20.sp
                    )

                    Spacer(
                        modifier = Modifier.height(12.dp)
                    )

                    if (turnoLlamado != null) {

                        Text(
                            text = "#${turnoLlamado.numero}",
                            fontSize = 42.sp
                        )

                        Text(
                            text = turnoLlamado.paciente.nombre,
                            fontSize = 24.sp
                        )

                    } else {

                        Text(
                            text = "Sin turno",
                            fontSize = 24.sp
                        )
                    }
                }
            }
        }

        Spacer(
            modifier = Modifier.height(28.dp)
        )

        Card(
            modifier = Modifier.fillMaxWidth()
        ) {

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Text(
                    text = "SIGUIENTE TURNO",
                    fontSize = 20.sp
                )

                Spacer(
                    modifier = Modifier.height(12.dp)
                )

                if (siguienteTurno != null) {

                    Text(
                        text = "#${siguienteTurno.numero}",
                        fontSize = 34.sp
                    )

                    Text(
                        text = siguienteTurno.paciente.nombre,
                        fontSize = 22.sp
                    )

                } else {

                    Text(
                        text = "No hay turnos en espera",
                        fontSize = 22.sp
                    )
                }
            }
        }
    }
}
package screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.turno_and_tv.model.Turno

@Composable
fun HomeScreen(
    turnoLlamado: Turno?,
    turnoAtendiendo: Turno?,
    onNuevoTurnoClick: () -> Unit,
    onListaTurnosClick: () -> Unit,
    onLlamarSiguienteClick: () -> Unit,
    onIniciarAtencionClick: () -> Unit,
    onFinalizarAtencionClick: () -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = "TurnoMed"
        )

        Spacer(
            modifier = Modifier.height(8.dp)
        )

        Text(
            text = "Panel principal"
        )

        Spacer(
            modifier = Modifier.height(24.dp)
        )

        // TURNO LLAMADO
        if (turnoLlamado != null) {

            Card {

                Column(
                    modifier = Modifier.padding(16.dp)
                ) {

                    Text(
                        text = "Turno llamado"
                    )

                    Spacer(
                        modifier = Modifier.height(4.dp)
                    )

                    Text(
                        text = "#${turnoLlamado.numero}"
                    )

                    Text(
                        text = turnoLlamado.paciente.nombre
                    )

                    Text(
                        text = "Motivo: ${turnoLlamado.motivo}"
                    )

                    Text(
                        text = "Estado: ${turnoLlamado.estado}"
                    )
                }
            }

        } else {

            Text(
                text = "No hay ningún turno llamado"
            )
        }

        Spacer(
            modifier = Modifier.height(12.dp)
        )

        // TURNO QUE ESTÁ SIENDO ATENDIDO
        if (turnoAtendiendo != null) {

            Card {

                Column(
                    modifier = Modifier.padding(16.dp)
                ) {

                    Text(
                        text = "En atención"
                    )

                    Spacer(
                        modifier = Modifier.height(4.dp)
                    )

                    Text(
                        text = "#${turnoAtendiendo.numero}"
                    )

                    Text(
                        text = turnoAtendiendo.paciente.nombre
                    )

                    Text(
                        text = "Estado: ${turnoAtendiendo.estado}"
                    )
                }
            }
        }

        Spacer(
            modifier = Modifier.height(24.dp)
        )

        Button(
            onClick = onNuevoTurnoClick
        ) {
            Text("Nuevo turno")
        }

        Spacer(
            modifier = Modifier.height(10.dp)
        )

        Button(
            onClick = onListaTurnosClick
        ) {
            Text("Lista de espera")
        }

        Spacer(
            modifier = Modifier.height(10.dp)
        )

        Button(
            onClick = onLlamarSiguienteClick
        ) {
            Text("Llamar siguiente")
        }

        Spacer(
            modifier = Modifier.height(10.dp)
        )

        Button(
            onClick = onIniciarAtencionClick,
            enabled = turnoLlamado != null
        ) {
            Text("Iniciar atención")
        }

        Spacer(
            modifier = Modifier.height(10.dp)
        )

        Button(
            onClick = onFinalizarAtencionClick,
            enabled = turnoAtendiendo != null
        ) {
            Text("Finalizar atención")
        }
    }
}
package screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
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
    turnoActual: Turno?,
    onNuevoTurnoClick: () -> Unit,
    onListaTurnosClick: () -> Unit,
    onAtenderSiguienteClick: () -> Unit
) {

    Column(
        modifier = Modifier.fillMaxSize(),
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

        if (turnoActual != null) {

            Card {

                Column {

                    Text(
                        text = "Turno actual"
                    )

                    Text(
                        text = "#${turnoActual.numero}"
                    )

                    Text(
                        text = turnoActual.paciente.nombre
                    )

                    Text(
                        text = "Estado: ${turnoActual.estado}"
                    )
                }
            }

            Spacer(
                modifier = Modifier.height(24.dp)
            )
        } else {

            Text(
                text = "No hay ningún turno llamado"
            )

            Spacer(
                modifier = Modifier.height(24.dp)
            )
        }

        Button(
            onClick = onNuevoTurnoClick
        ) {
            Text("Nuevo turno")
        }

        Spacer(
            modifier = Modifier.height(12.dp)
        )

        Button(
            onClick = onListaTurnosClick
        ) {
            Text("Lista de espera")
        }

        Spacer(
            modifier = Modifier.height(12.dp)
        )

        Button(
            onClick = onAtenderSiguienteClick
        ) {
            Text("Atender siguiente")
        }
    }
}
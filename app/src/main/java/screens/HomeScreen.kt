package screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun HomeScreen(
    onNuevoTurnoClick: () -> Unit,
    onListaTurnosClick: () -> Unit
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
    }
}
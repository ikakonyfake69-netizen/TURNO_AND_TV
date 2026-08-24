package screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.turno_and_tv.core.model.EstadoTurno
import com.example.turno_and_tv.core.model.Turno

@Composable
fun ListaTurnosScreen(
    turnos: List<Turno>,
    onVolverClick: () -> Unit
) {

    val turnosActivos = turnos.filter {
        it.estado != EstadoTurno.FINALIZADO
    }

    val turnosFinalizados = turnos.filter {
        it.estado == EstadoTurno.FINALIZADO
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {

        Text(
            text = "Lista de turnos"
        )

        Spacer(
            modifier = Modifier.height(20.dp)
        )

        LazyColumn(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            item {
                Text(
                    text = "Turnos activos"
                )
            }

            if (turnosActivos.isEmpty()) {

                item {
                    Text(
                        text = "No hay turnos activos."
                    )
                }

            } else {

                items(turnosActivos) { turno ->

                    Card(
                        modifier = Modifier.fillMaxWidth()
                    ) {

                        Column(
                            modifier = Modifier.padding(16.dp)
                        ) {

                            Text(
                                text = "Turno #${turno.numero}"
                            )

                            Text(
                                text = "Paciente: ${turno.paciente.nombre}"
                            )

                            Text(
                                text = "Motivo: ${turno.motivo}"
                            )

                            Text(
                                text = "Estado: ${turno.estado}"
                            )
                        }
                    }
                }
            }

            item {

                Spacer(
                    modifier = Modifier.height(12.dp)
                )

                HorizontalDivider()

                Spacer(
                    modifier = Modifier.height(16.dp)
                )

                Text(
                    text = "Historial de turnos"
                )
            }

            if (turnosFinalizados.isEmpty()) {

                item {
                    Text(
                        text = "No hay turnos finalizados."
                    )
                }

            } else {

                items(turnosFinalizados) { turno ->

                    Card(
                        modifier = Modifier.fillMaxWidth()
                    ) {

                        Column(
                            modifier = Modifier.padding(16.dp)
                        ) {

                            Text(
                                text = "Turno #${turno.numero}"
                            )

                            Text(
                                text = "Paciente: ${turno.paciente.nombre}"
                            )

                            Text(
                                text = "Motivo: ${turno.motivo}"
                            )

                            Text(
                                text = "Estado: FINALIZADO"
                            )
                        }
                    }
                }
            }
        }

        Spacer(
            modifier = Modifier.height(16.dp)
        )

        Button(
            onClick = onVolverClick,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Volver")
        }
    }
}
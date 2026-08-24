package navigation

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.turno_and_tv.core.data.FirestoreTurnoRepository
import com.example.turno_and_tv.core.model.EstadoTurno
import com.example.turno_and_tv.core.model.Paciente
import com.example.turno_and_tv.core.model.Turno
import kotlinx.coroutines.launch
import screens.HomeScreen
import screens.ListaTurnosScreen
import screens.LoginScreen
import screens.NuevoTurnoScreen
import screens.TvScreen
import java.util.UUID

@Composable
fun AppNavigation() {

    val navController = rememberNavController()

    val context = LocalContext.current

    val repository = remember {
        FirestoreTurnoRepository()
    }

    val coroutineScope = rememberCoroutineScope()

    val turnos by repository
        .observarTurnos()
        .collectAsState(initial = emptyList())

    NavHost(
        navController = navController,
        startDestination = "login"
    ) {

        composable("login") {

            LoginScreen(
                onLoginClick = {
                    navController.navigate("home")
                }
            )
        }

        composable("home") {

            val turnoLlamado = turnos.firstOrNull {
                it.estado == EstadoTurno.LLAMADO
            }

            val turnoAtendiendo = turnos.firstOrNull {
                it.estado == EstadoTurno.ATENDIENDO
            }

            HomeScreen(
                turnoLlamado = turnoLlamado,
                turnoAtendiendo = turnoAtendiendo,

                onNuevoTurnoClick = {
                    navController.navigate("nuevo_turno")
                },

                onListaTurnosClick = {
                    navController.navigate("lista_turnos")
                },

                onLlamarSiguienteClick = {

                    val yaHayTurnoLlamado = turnos.any {
                        it.estado == EstadoTurno.LLAMADO
                    }

                    if (!yaHayTurnoLlamado) {

                        val siguienteTurno = turnos
                            .filter {
                                it.estado == EstadoTurno.ESPERANDO
                            }
                            .minByOrNull {
                                it.numero
                            }

                        if (siguienteTurno != null) {

                            coroutineScope.launch {

                                repository.actualizarEstadoTurno(
                                    turnoId = siguienteTurno.id,
                                    nuevoEstado = EstadoTurno.LLAMADO
                                )
                            }
                        }
                    }
                },

                onIniciarAtencionClick = {

                    val yaHayTurnoAtendiendo = turnos.any {
                        it.estado == EstadoTurno.ATENDIENDO
                    }

                    if (!yaHayTurnoAtendiendo) {

                        val turnoLlamado = turnos.firstOrNull {
                            it.estado == EstadoTurno.LLAMADO
                        }

                        if (turnoLlamado != null) {

                            coroutineScope.launch {

                                repository.actualizarEstadoTurno(
                                    turnoId = turnoLlamado.id,
                                    nuevoEstado = EstadoTurno.ATENDIENDO
                                )
                            }
                        }
                    }
                },

                onFinalizarAtencionClick = {

                    val turnoAtendiendo = turnos.firstOrNull {
                        it.estado == EstadoTurno.ATENDIENDO
                    }

                    if (turnoAtendiendo != null) {

                        coroutineScope.launch {

                            repository.actualizarEstadoTurno(
                                turnoId = turnoAtendiendo.id,
                                nuevoEstado = EstadoTurno.FINALIZADO
                            )
                        }
                    }
                },

                onVerTvClick = {
                    navController.navigate("tv")
                }
            )
        }

        composable("nuevo_turno") {

            NuevoTurnoScreen(

                onRegistrarTurno = { nombre, motivo ->

                    val numeroTurno =
                        (turnos.maxOfOrNull { it.numero } ?: 0) + 1

                    val idTurno =
                        UUID.randomUUID().toString()

                    val paciente = Paciente(
                        id = UUID.randomUUID().toString(),
                        nombre = nombre
                    )

                    val preferences =
                        context.getSharedPreferences(
                            "turnomed_preferences",
                            Context.MODE_PRIVATE
                        )

                    val dispositivoId =
                        preferences.getString(
                            "dispositivo_id",
                            ""
                        ) ?: ""

                    val nuevoTurno = Turno(
                        id = idTurno,
                        numero = numeroTurno,
                        paciente = paciente,
                        motivo = motivo,
                        estado = EstadoTurno.ESPERANDO,
                        dispositivoId = dispositivoId
                    )

                    coroutineScope.launch {

                        repository.crearTurno(
                            nuevoTurno
                        )

                        navController.navigate("lista_turnos") {

                            popUpTo("nuevo_turno") {
                                inclusive = true
                            }
                        }
                    }
                },

                onVolverClick = {
                    navController.popBackStack()
                }
            )
        }

        composable("lista_turnos") {

            ListaTurnosScreen(
                turnos = turnos,

                onVolverClick = {
                    navController.popBackStack()
                }
            )
        }

        composable("tv") {

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

            TvScreen(
                turnoAtendiendo = turnoAtendiendo,
                turnoLlamado = turnoLlamado,
                siguienteTurno = siguienteTurno
            )
        }
    }
}
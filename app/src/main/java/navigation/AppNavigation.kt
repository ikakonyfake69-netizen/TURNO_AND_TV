package navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.turno_and_tv.model.EstadoTurno
import com.example.turno_and_tv.model.Paciente
import com.example.turno_and_tv.model.Turno
import screens.HomeScreen
import screens.ListaTurnosScreen
import screens.LoginScreen
import screens.NuevoTurnoScreen
import screens.TvScreen

@Composable
fun AppNavigation() {

    val navController = rememberNavController()

    val turnos = remember {
        mutableStateListOf<Turno>()
    }

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

                        val indiceSiguiente = turnos.indexOfFirst {
                            it.estado == EstadoTurno.ESPERANDO
                        }

                        if (indiceSiguiente != -1) {

                            val siguienteTurno = turnos[indiceSiguiente]

                            turnos[indiceSiguiente] = siguienteTurno.copy(
                                estado = EstadoTurno.LLAMADO
                            )
                        }
                    }
                },

                onIniciarAtencionClick = {

                    val yaHayTurnoAtendiendo = turnos.any {
                        it.estado == EstadoTurno.ATENDIENDO
                    }

                    if (!yaHayTurnoAtendiendo) {

                        val indiceLlamado = turnos.indexOfFirst {
                            it.estado == EstadoTurno.LLAMADO
                        }

                        if (indiceLlamado != -1) {

                            val turno = turnos[indiceLlamado]

                            turnos[indiceLlamado] = turno.copy(
                                estado = EstadoTurno.ATENDIENDO
                            )
                        }
                    }
                },

                onFinalizarAtencionClick = {

                    val indiceAtendiendo = turnos.indexOfFirst {
                        it.estado == EstadoTurno.ATENDIENDO
                    }

                    if (indiceAtendiendo != -1) {

                        val turno = turnos[indiceAtendiendo]

                        turnos[indiceAtendiendo] = turno.copy(
                            estado = EstadoTurno.FINALIZADO
                        )
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

                    val numeroTurno = turnos.size + 1

                    val paciente = Paciente(
                        id = numeroTurno.toString(),
                        nombre = nombre
                    )

                    val nuevoTurno = Turno(
                        id = numeroTurno.toString(),
                        numero = numeroTurno,
                        paciente = paciente,
                        motivo = motivo,
                        estado = EstadoTurno.ESPERANDO
                    )

                    turnos.add(nuevoTurno)

                    navController.navigate("lista_turnos")
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

            val siguienteTurno = turnos.firstOrNull {
                it.estado == EstadoTurno.ESPERANDO
            }

            TvScreen(
                turnoAtendiendo = turnoAtendiendo,
                turnoLlamado = turnoLlamado,
                siguienteTurno = siguienteTurno
            )
        }
    }
}
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

            HomeScreen(
                onNuevoTurnoClick = {
                    navController.navigate("nuevo_turno")
                },

                onListaTurnosClick = {
                    navController.navigate("lista_turnos")
                },

                onAtenderSiguienteClick = {

                    val indiceSiguiente = turnos.indexOfFirst {
                        it.estado == EstadoTurno.ESPERANDO
                    }

                    if (indiceSiguiente != -1) {

                        val turnoActual = turnos[indiceSiguiente]

                        turnos[indiceSiguiente] = turnoActual.copy(
                            estado = EstadoTurno.LLAMADO
                        )
                    }
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
    }
}
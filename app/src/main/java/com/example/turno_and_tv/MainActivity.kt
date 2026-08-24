package com.example.turno_and_tv

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.example.turno_and_tv.core.data.DispositivoRepository
import com.example.turno_and_tv.core.sync.FcmTokenManager
import com.example.turno_and_tv.ui.theme.Turno_and_TVTheme
import kotlinx.coroutines.launch
import navigation.AppNavigation
import java.util.UUID

class MainActivity : ComponentActivity() {

    private val notificationPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) {
            // Aquí podemos manejar después
            // si el usuario acepta o rechaza las notificaciones.
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        solicitarPermisoNotificaciones()

        guardarTokenFcm()

        setContent {

            Turno_and_TVTheme {

                AppNavigation()

            }
        }
    }

    private fun solicitarPermisoNotificaciones() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {

            val permisoConcedido =
                ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.POST_NOTIFICATIONS
                ) == PackageManager.PERMISSION_GRANTED

            if (!permisoConcedido) {

                notificationPermissionLauncher.launch(
                    Manifest.permission.POST_NOTIFICATIONS
                )
            }
        }
    }

    private fun guardarTokenFcm() {

        lifecycleScope.launch {

            val tokenManager = FcmTokenManager()

            val dispositivoRepository =
                DispositivoRepository()

            val token =
                tokenManager.obtenerToken()

            if (token != null) {

                val preferences =
                    getSharedPreferences(
                        "turnomed_preferences",
                        MODE_PRIVATE
                    )

                var dispositivoId =
                    preferences.getString(
                        "dispositivo_id",
                        null
                    )

                if (dispositivoId == null) {

                    dispositivoId =
                        UUID.randomUUID().toString()

                    preferences
                        .edit()
                        .putString(
                            "dispositivo_id",
                            dispositivoId
                        )
                        .apply()
                }

                dispositivoRepository.guardarToken(
                    dispositivoId = dispositivoId,
                    token = token
                )
            }
        }
    }
}
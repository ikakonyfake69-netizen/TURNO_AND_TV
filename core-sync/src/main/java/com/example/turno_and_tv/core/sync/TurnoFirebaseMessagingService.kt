package com.example.turno_and_tv.core.sync

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class TurnoFirebaseMessagingService : FirebaseMessagingService() {

    companion object {
        private const val CHANNEL_ID = "turnomed_notificaciones"
        private const val CHANNEL_NAME = "Notificaciones TurnoMed"
        private const val CHANNEL_DESCRIPTION =
            "Avisos relacionados con los turnos de los pacientes"
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)

        val titulo =
            remoteMessage.notification?.title
                ?: remoteMessage.data["titulo"]
                ?: "TurnoMed"

        val mensaje =
            remoteMessage.notification?.body
                ?: remoteMessage.data["mensaje"]
                ?: "Tienes una actualización de tu turno."

        mostrarNotificacion(
            titulo = titulo,
            mensaje = mensaje
        )
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)

        // Más adelante guardaremos este token en Firestore
        // para identificar el celular del paciente.
    }

    private fun mostrarNotificacion(
        titulo: String,
        mensaje: String
    ) {

        crearCanalNotificaciones()

        val packageManager = applicationContext.packageManager

        val intent = packageManager
            .getLaunchIntentForPackage(applicationContext.packageName)
            ?.apply {
                flags =
                    Intent.FLAG_ACTIVITY_CLEAR_TOP or
                            Intent.FLAG_ACTIVITY_SINGLE_TOP
            }

        val pendingIntent = if (intent != null) {

            PendingIntent.getActivity(
                this,
                0,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT or
                        PendingIntent.FLAG_IMMUTABLE
            )

        } else {
            null
        }

        val notificationBuilder =
            NotificationCompat.Builder(
                this,
                CHANNEL_ID
            )
                .setSmallIcon(android.R.drawable.ic_dialog_info)
                .setContentTitle(titulo)
                .setContentText(mensaje)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true)

        if (pendingIntent != null) {
            notificationBuilder.setContentIntent(pendingIntent)
        }

        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE)
                    as NotificationManager

        notificationManager.notify(
            System.currentTimeMillis().toInt(),
            notificationBuilder.build()
        )
    }

    private fun crearCanalNotificaciones() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            val canal = NotificationChannel(
                CHANNEL_ID,
                CHANNEL_NAME,
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = CHANNEL_DESCRIPTION
            }

            val notificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE)
                        as NotificationManager

            notificationManager.createNotificationChannel(canal)
        }
    }
}
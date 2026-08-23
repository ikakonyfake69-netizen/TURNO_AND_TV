package com.example.turno_and_tv

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.turno_and_tv.ui.theme.Turno_and_TVTheme
import navigation.AppNavigation

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {

            Turno_and_TVTheme {

                AppNavigation()

            }
        }
    }
}
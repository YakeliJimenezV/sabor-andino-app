package com.example.saborandinoapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.saborandinoapp.navigation.AppNavigation
import com.example.saborandinoapp.ui.theme.SaborAndinoAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            SaborAndinoAppTheme {
                AppNavigation() //  AQUÍ ARRANCA TODA TU APP
            }
        }
    }
}
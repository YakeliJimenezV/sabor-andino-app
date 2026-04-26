package com.example.saborandinoapp.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@Composable
fun LoginScreen(navController: NavHostController) {

    //  Estado del campo correo (guarda lo que el usuario escribe)
    var email by remember { mutableStateOf("") }

    //  Estado del campo contraseña
    var password by remember { mutableStateOf("") }

    //  Estado para mostrar mensajes de error en pantalla
    var error by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),

        //  Centra el contenido verticalmente
        verticalArrangement = Arrangement.Center
    ) {

        //  TÍTULO DE BIENVENIDA
        Text(
            "Bienvenido a Sabor Andino",
            style = MaterialTheme.typography.titleLarge
        )

        Spacer(modifier = Modifier.height(20.dp))

        //  CAMPO DE CORREO
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Correo") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(10.dp))

        //  CAMPO DE CONTRASEÑA
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Contraseña") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(20.dp))

        //  MENSAJE DE ERROR (si existe)
        if (error.isNotEmpty()) {
            Text(error, color = MaterialTheme.colorScheme.error)
        }

        Spacer(modifier = Modifier.height(10.dp))

        //  BOTÓN DE INGRESO
        Button(
            onClick = {

                //  VALIDACIONES BÁSICAS
                when {

                    //  Campos vacíos
                    email.isBlank() || password.isBlank() -> {
                        error = "Completa todos los campos"
                    }

                    //  Validación de correo
                    !email.contains("@") -> {
                        error = "Correo inválido"
                    }

                    //  Contraseña muy corta
                    password.length < 4 -> {
                        error = "Contraseña muy corta"
                    }

                    //  SI TODO ES CORRECTO
                    else -> {
                        error = ""

                        //  Se obtiene el nombre desde el correo
                        val usuario = email.substringBefore("@")

                        //  Navegación enviando usuario y email al Home
                        navController.navigate("home/$usuario/$email")
                    }
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Ingresar")
        }
    }
}
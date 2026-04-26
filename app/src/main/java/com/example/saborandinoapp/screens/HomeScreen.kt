package com.example.saborandinoapp.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@Composable
fun HomeScreen(
    navController: NavHostController,
    usuario: String,
    email: String
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        //  SALUDO PERSONALIZADO CON EL NOMBRE DEL USUARIO
        Text(
            text = "Hola $usuario 👋",
            style = MaterialTheme.typography.titleLarge
        )

        Spacer(modifier = Modifier.height(5.dp))

        //  MOSTRAR CORREO DEL USUARIO LOGUEADO
        Text(
            text = "📧 $email",
            style = MaterialTheme.typography.bodyMedium
        )

        Spacer(modifier = Modifier.height(20.dp))

        // 🍽 TARJETA 1: ACCESO AL MENÚ
        // Permite navegar a la lista de platos
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
                .clickable {
                    navController.navigate("menu")
                }
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("Ver Menú 🍽️", style = MaterialTheme.typography.titleMedium)
                Text("Explora nuestros platos")
            }
        }

        //  TARJETA 2: ACCESO AL PEDIDO
        // Muestra los productos agregados al carrito
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
                .clickable {
                    navController.navigate("pedido")
                }
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("Mi Pedido 🛒", style = MaterialTheme.typography.titleMedium)
                Text("Revisa lo que agregaste")
            }
        }

        //  TARJETA 3: PERFIL DEL USUARIO
        // Se envía usuario y email para mostrar datos personalizados
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
                .clickable {
                    navController.navigate("perfil/$usuario/$email")
                }
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("Mi Perfil 👤", style = MaterialTheme.typography.titleMedium)
                Text("Tus datos personales")
            }
        }
    }
}
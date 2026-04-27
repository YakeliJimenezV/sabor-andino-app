package com.example.saborandinoapp.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.saborandinoapp.R

@Composable
fun HomeScreen(
    navController: NavHostController,
    usuario: String,
    email: String
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {

        // 🖼 IMAGEN GRANDE TIPO BANNER
        Image(
            painter = painterResource(id = R.drawable.restaurante),
            contentDescription = "Bienvenida",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .height(220.dp)
        )

        Column(
            modifier = Modifier
                .padding(16.dp)
        ) {

            // 👋 TITULO
            Text(
                text = "Bienvenido a Sabor Andino 👋",
                style = MaterialTheme.typography.titleLarge
            )

            Spacer(modifier = Modifier.height(6.dp))

            // 📄 DESCRIPCIÓN
            Text(
                text = "Aquí encontrarás los mejores platos peruanos, haz tu pedido y disfruta desde casa.",
                style = MaterialTheme.typography.bodyMedium
            )

            Spacer(modifier = Modifier.height(10.dp))

            // 👤 DATOS DEL USUARIO
            Text(
                text = "Usuario: $usuario",
                style = MaterialTheme.typography.bodySmall
            )

            Text(
                text = "Correo: $email",
                style = MaterialTheme.typography.bodySmall
            )

            Spacer(modifier = Modifier.height(20.dp))

            // 🍽 MENÚ
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
                    Text("Explora nuestros deliciosos platos")
                }
            }

            // 🛒 PEDIDO
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
                    Text("Revisa o modifica tu pedido")
                }
            }

            // 👤 PERFIL
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
                    Text("Ver tus datos personales")
                }
            }
        }
    }
}
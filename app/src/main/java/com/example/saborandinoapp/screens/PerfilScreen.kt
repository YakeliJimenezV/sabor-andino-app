package com.example.saborandinoapp.screens

import androidx.compose.foundation.Image
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.saborandinoapp.data.PedidoManager

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PerfilScreen(
    navController: NavHostController,
    usuario: String,
    email: String
) {

    val pedido = PedidoManager.pedido
    val total = PedidoManager.total()

    val inicial = usuario.firstOrNull()?.uppercase() ?: "U"

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Mi Perfil") },
                navigationIcon = {
                    IconButton(onClick = {
                        navController.popBackStack()
                    }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Volver"
                        )
                    }
                }
            )
        }
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            //  AVATAR
            Box(
                modifier = Modifier
                    .size(90.dp)
                    .clip(CircleShape)
                    .background(Color(0xFF4CAF50)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = inicial,
                    style = MaterialTheme.typography.headlineLarge,
                    color = Color.White
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            //  DATOS
            Text(
                text = "👤 $usuario",
                style = MaterialTheme.typography.titleLarge
            )

            Text(text = "📧 $email")

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "🛒 Mis pedidos",
                style = MaterialTheme.typography.titleLarge
            )

            Spacer(modifier = Modifier.height(10.dp))

            if (pedido.isEmpty()) {

                Text("No tienes pedidos aún ")

            } else {

                Column(modifier = Modifier.fillMaxWidth()) {

                    pedido.forEach { plato ->

                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(6.dp)
                        ) {

                            Row(
                                modifier = Modifier.padding(12.dp)
                            ) {

                                Image(
                                    painter = painterResource(id = plato.imagen),
                                    contentDescription = plato.nombre,
                                    modifier = Modifier.size(60.dp)
                                )

                                Spacer(modifier = Modifier.width(10.dp))

                                Column {
                                    Text(plato.nombre)
                                    Text("S/. ${plato.precio}")
                                }
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = "💰 Total: S/. $total",
                    style = MaterialTheme.typography.titleLarge
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            //  BOTÓN CERRAR SESIÓN
            Button(
                onClick = {
                    navController.navigate("login") {
                        popUpTo(0) // limpia toda la pila
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Red
                )
            ) {
                Text("Cerrar sesión")
            }
        }
    }
}
package com.example.saborandinoapp.screens

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.RestaurantMenu
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.saborandinoapp.R
import com.example.saborandinoapp.data.PedidoManager
import com.example.saborandinoapp.ui.theme.*

@Composable
fun HomeScreen(
    navController: NavHostController,
    usuario: String,
    email: String
) {
    // Garantizamos que los datos no se pierdan usando el Manager como respaldo
    val nombreUsuario = if (usuario.isNotBlank() && !usuario.contains("{")) usuario else PedidoManager.usuarioActual
    val correoUsuario = if (email.isNotBlank() && !email.contains("{")) email else PedidoManager.emailActual

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(GrisClaro)
    ) {
        Box {
            Image(
                painter = painterResource(id = R.drawable.restaurante),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            )
            Surface(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(16.dp),
                color = AzulMarino.copy(alpha = 0.8f),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(
                    "¡Hola, $nombreUsuario!",
                    color = Blanco,
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp),
                    style = MaterialTheme.typography.titleMedium
                )
            }
        }

        Column(modifier = Modifier.padding(20.dp)) {
            Text(
                "¿Qué te apetece hoy?",
                style = MaterialTheme.typography.headlineSmall,
                color = AzulMarino,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(24.dp))

            HomeCard(
                title = "Ver Nuestra Carta",
                subtitle = "Explora los mejores platos",
                icon = Icons.Default.RestaurantMenu,
                onClick = { navController.navigate("menu") }
            )

            HomeCard(
                title = "Mi Carrito",
                subtitle = "Gestiona tu pedido actual",
                icon = Icons.Default.ShoppingCart,
                onClick = { navController.navigate("pedido") }
            )

            HomeCard(
                title = "Perfil",
                subtitle = "Tus datos y direcciones",
                icon = Icons.Default.Person,
                onClick = { navController.navigate("perfil/$nombreUsuario/$correoUsuario") }
            )
        }
    }
}

@Composable
fun HomeCard(title: String, subtitle: String, icon: ImageVector, onClick: () -> Unit) {
    var isPressed by remember { mutableStateOf(false) }
    val scale by animateFloatAsState(if (isPressed) 0.95f else 1f, label = "scale")

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .graphicsLayer(scaleX = scale, scaleY = scale)
            .pointerInput(Unit) {
                detectTapGestures(
                    onPress = {
                        isPressed = true
                        try {
                            awaitRelease()
                        } finally {
                            isPressed = false
                        }
                        onClick()
                    }
                )
            },
        colors = CardDefaults.cardColors(containerColor = AzulMarino),
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            modifier = Modifier.padding(20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                shape = RoundedCornerShape(12.dp),
                color = AzulClaro.copy(alpha = 0.2f),
                modifier = Modifier.size(50.dp)
            ) {
                Icon(icon, contentDescription = null, tint = Blanco, modifier = Modifier.padding(12.dp))
            }

            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(title, color = Blanco, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                Text(subtitle, color = Blanco.copy(alpha = 0.7f), fontSize = 14.sp)
            }
        }
    }
}
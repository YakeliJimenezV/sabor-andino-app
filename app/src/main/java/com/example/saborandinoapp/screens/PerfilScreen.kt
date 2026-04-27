package com.example.saborandinoapp.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.saborandinoapp.data.PedidoManager
import com.example.saborandinoapp.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PerfilScreen(
    navController: NavHostController,
    usuario: String,
    email: String
) {
    // Garantizamos que los datos no se pierdan usando el Manager como respaldo
    val nombreUsuario = if (usuario.isNotBlank() && !usuario.contains("{")) usuario else PedidoManager.usuarioActual
    val correoUsuario = if (email.isNotBlank() && !email.contains("{")) email else PedidoManager.emailActual

    val pedido = PedidoManager.pedido
    val total = PedidoManager.total()
    val inicial = nombreUsuario.firstOrNull()?.uppercase() ?: "U"

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("MI PERFIL", fontWeight = FontWeight.Bold, color = Blanco) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Volver", tint = Blanco)
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = AzulMarino)
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(GrisClaro)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Header con Avatar
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(AzulMarino)
                    .padding(bottom = 32.dp, top = 16.dp),
                contentAlignment = Alignment.Center
            ) {
                Surface(
                    modifier = Modifier.size(120.dp),
                    shape = CircleShape,
                    color = AzulClaro,
                    tonalElevation = 8.dp
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Text(
                            text = inicial,
                            style = MaterialTheme.typography.displayMedium,
                            color = Blanco,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

            Column(
                modifier = Modifier
                    .padding(24.dp)
                    .fillMaxWidth()
            ) {
                // Sección de Información
                Text(
                    text = "Información Personal",
                    style = MaterialTheme.typography.titleMedium,
                    color = AzulMarino,
                    fontWeight = FontWeight.Bold
                )
                
                Spacer(modifier = Modifier.height(16.dp))

                InfoRow(icon = Icons.Default.Person, label = "Usuario", value = nombreUsuario)
                Spacer(modifier = Modifier.height(12.dp))
                InfoRow(icon = Icons.Default.Email, label = "Correo", value = correoUsuario)

                Spacer(modifier = Modifier.height(32.dp))

                // Sección de Mis Pedidos Recientes
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Mis Pedidos Recientes",
                        style = MaterialTheme.typography.titleMedium,
                        color = AzulMarino,
                        fontWeight = FontWeight.Bold
                    )
                    Icon(Icons.Default.ShoppingBag, contentDescription = null, tint = AzulMedio)
                }

                Spacer(modifier = Modifier.height(16.dp))

                if (pedido.isEmpty()) {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(containerColor = Blanco),
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Text(
                            "No tienes pedidos activos en este momento.",
                            modifier = Modifier.padding(24.dp),
                            style = MaterialTheme.typography.bodyMedium,
                            color = AzulMedio.copy(alpha = 0.6f)
                        )
                    }
                } else {
                    pedido.forEach { plato ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp),
                            colors = CardDefaults.cardColors(containerColor = Blanco),
                            shape = RoundedCornerShape(12.dp),
                            elevation = CardDefaults.cardElevation(2.dp)
                        ) {
                            Row(
                                modifier = Modifier.padding(12.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Image(
                                    painter = painterResource(id = plato.imagen),
                                    contentDescription = plato.nombre,
                                    modifier = Modifier
                                        .size(50.dp)
                                        .clip(RoundedCornerShape(8.dp)),
                                    contentScale = ContentScale.Crop
                                )
                                Spacer(modifier = Modifier.width(12.dp))
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(plato.nombre, fontWeight = FontWeight.SemiBold, color = AzulMarino)
                                    Text("S/. ${plato.precio}", color = AzulClaro, fontSize = 14.sp)
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))
                    
                    Surface(
                        color = AzulClaro.copy(alpha = 0.1f),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier.padding(16.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text("Total acumulado", fontWeight = FontWeight.Medium, color = AzulMedio)
                            Text("S/. $total", fontWeight = FontWeight.Bold, color = AzulMarino, fontSize = 18.sp)
                        }
                    }
                }

                Spacer(modifier = Modifier.height(40.dp))

                // Botón Cerrar Sesión
                Button(
                    onClick = {
                        navController.navigate("login") {
                            popUpTo(0)
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD32F2F)),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Icon(Icons.Default.ExitToApp, contentDescription = null, tint = Blanco)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("CERRAR SESIÓN", fontWeight = FontWeight.Bold, color = Blanco)
                }
                
                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }
}

@Composable
fun InfoRow(icon: ImageVector, label: String, value: String) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Blanco),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(icon, contentDescription = null, tint = AzulClaro, modifier = Modifier.size(24.dp))
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(label, style = MaterialTheme.typography.labelSmall, color = AzulMedio.copy(alpha = 0.6f))
                Text(value, style = MaterialTheme.typography.bodyLarge, color = AzulMarino, fontWeight = FontWeight.Medium)
            }
        }
    }
}
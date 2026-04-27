package com.example.saborandinoapp.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.RemoveShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
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
fun PedidoScreen(navController: NavHostController) {

    val pedido = PedidoManager.pedido
    val total = PedidoManager.total()

    var mostrarDialogoConfirmacion by remember { mutableStateOf(false) }
    var mostrarDialogoExito by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("MI PEDIDO", fontWeight = FontWeight.Bold, color = Blanco) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Volver", tint = Blanco)
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = AzulMarino)
            )
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .background(GrisClaro)
        ) {

            if (pedido.isEmpty()) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        Icons.Default.RemoveShoppingCart,
                        contentDescription = null,
                        modifier = Modifier.size(100.dp),
                        tint = AzulMedio.copy(alpha = 0.3f)
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text("Tu pedido está vacío 😢", color = AzulMedio, fontSize = 18.sp)
                    Spacer(modifier = Modifier.height(24.dp))
                    Button(
                        onClick = { navController.navigate("menu") },
                        colors = ButtonDefaults.buttonColors(containerColor = AzulClaro),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text("Explorar Menú", color = Blanco)
                    }
                }
            } else {
                LazyColumn(
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                ) {
                    items(pedido.toList()) { plato ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 6.dp),
                            colors = CardDefaults.cardColors(containerColor = Blanco),
                            elevation = CardDefaults.cardElevation(2.dp),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Row(
                                modifier = Modifier.padding(12.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Image(
                                    painter = painterResource(id = plato.imagen),
                                    contentDescription = plato.nombre,
                                    modifier = Modifier
                                        .size(70.dp)
                                        .clip(RoundedCornerShape(8.dp)),
                                    contentScale = ContentScale.Crop
                                )

                                Spacer(modifier = Modifier.width(12.dp))

                                Column(
                                    modifier = Modifier.weight(1f)
                                ) {
                                    Text(
                                        plato.nombre,
                                        fontWeight = FontWeight.Bold,
                                        color = AzulMarino,
                                        fontSize = 16.sp
                                    )
                                    Text(
                                        "S/. ${plato.precio}",
                                        color = AzulClaro,
                                        fontWeight = FontWeight.Medium
                                    )
                                }

                                IconButton(onClick = {
                                    PedidoManager.pedido.remove(plato)
                                }) {
                                    Icon(
                                        imageVector = Icons.Default.Delete,
                                        contentDescription = "Eliminar",
                                        tint = Color.Red.copy(alpha = 0.6f)
                                    )
                                }
                            }
                        }
                    }
                }

                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    shadowElevation = 8.dp,
                    color = Blanco,
                    shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)
                ) {
                    Column(modifier = Modifier.padding(24.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text("Total:", fontSize = 18.sp, color = AzulMedio)
                            Text(
                                "S/. $total",
                                fontSize = 24.sp,
                                fontWeight = FontWeight.ExtraBold,
                                color = AzulMarino
                            )
                        }
                        Spacer(modifier = Modifier.height(16.dp))
                        Button(
                            onClick = { mostrarDialogoConfirmacion = true },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(56.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = AzulMarino),
                            shape = RoundedCornerShape(16.dp)
                        ) {
                            Text("PAGAR AHORA", fontWeight = FontWeight.Bold, color = Blanco, fontSize = 16.sp)
                        }
                    }
                }
            }
        }
    }

    // --- DIÁLOGOS DE FLUJO DE PAGO ---

    if (mostrarDialogoConfirmacion) {
        AlertDialog(
            onDismissRequest = { mostrarDialogoConfirmacion = false },
            title = { Text("Confirmar Pedido", color = AzulMarino, fontWeight = FontWeight.Bold) },
            text = { Text("¿Deseas procesar el pago por un total de S/. $total?") },
            confirmButton = {
                Button(
                    onClick = {
                        PedidoManager.pedido.clear()
                        mostrarDialogoConfirmacion = false
                        mostrarDialogoExito = true
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = AzulMarino)
                ) {
                    Text("Confirmar")
                }
            },
            dismissButton = {
                TextButton(onClick = { mostrarDialogoConfirmacion = false }) {
                    Text("Cancelar", color = AzulMedio)
                }
            },
            shape = RoundedCornerShape(20.dp),
            containerColor = Blanco
        )
    }

    if (mostrarDialogoExito) {
        AlertDialog(
            onDismissRequest = { /* No cerrar al tocar fuera */ },
            icon = { Icon(Icons.Default.CheckCircle, contentDescription = null, tint = Color(0xFF4CAF50), modifier = Modifier.size(48.dp)) },
            title = { Text("¡Pago Exitoso!", color = AzulMarino, fontWeight = FontWeight.Bold) },
            text = { Text("Tu pedido ha sido procesado correctamente. ¡Disfruta tu Sabor Andino!", textAlign = androidx.compose.ui.text.style.TextAlign.Center) },
            confirmButton = {
                Button(
                    onClick = {
                        mostrarDialogoExito = false
                        // NAVEGACIÓN CORREGIDA: Usamos los datos guardados en el Manager
                        val user = PedidoManager.usuarioActual
                        val mail = PedidoManager.emailActual
                        navController.navigate("home/$user/$mail") {
                            popUpTo("home/{usuario}/{email}") { inclusive = true }
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = AzulMarino)
                ) {
                    Text("Volver al Inicio")
                }
            },
            shape = RoundedCornerShape(20.dp),
            containerColor = Blanco
        )
    }
}
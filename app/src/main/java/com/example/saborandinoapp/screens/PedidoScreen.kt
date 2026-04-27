package com.example.saborandinoapp.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.saborandinoapp.data.PedidoManager

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PedidoScreen(navController: NavHostController) {

    val pedido = PedidoManager.pedido
    val total = PedidoManager.total()

    // 🔥 estados
    var mostrarDialogoPago by remember { mutableStateOf(false) }
    var pagoExitoso by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Mi Pedido") },
                navigationIcon = {
                    IconButton(onClick = {
                        navController.popBackStack()
                    }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Volver")
                    }
                }
            )
        }
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {

            if (pedido.isEmpty()) {

                Text("Tu pedido está vacío 😢")

            } else {

                LazyColumn(
                    modifier = Modifier.weight(1f)
                ) {

                    items(pedido) { plato ->

                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp)
                        ) {

                            Row(modifier = Modifier.padding(12.dp)) {

                                Image(
                                    painter = painterResource(id = plato.imagen),
                                    contentDescription = plato.nombre,
                                    modifier = Modifier.size(70.dp)
                                )

                                Spacer(modifier = Modifier.width(12.dp))

                                Column(
                                    modifier = Modifier.weight(1f)
                                ) {
                                    Text(plato.nombre)
                                    Text("S/. ${plato.precio}")
                                }
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = "Total: S/. $total",
                    style = MaterialTheme.typography.titleLarge
                )

                Spacer(modifier = Modifier.height(10.dp))

                Button(
                    onClick = {
                        mostrarDialogoPago = true
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Pagar pedido 💳")
                }
            }
        }
    }

    // 💳 CONFIRMACIÓN DE PAGO
    if (mostrarDialogoPago) {
        AlertDialog(
            onDismissRequest = { mostrarDialogoPago = false },
            title = { Text("Confirmar pago") },
            text = { Text("¿Deseas pagar S/. $total?") },
            confirmButton = {
                Button(onClick = {
                    PedidoManager.pedido.clear()
                    mostrarDialogoPago = false
                    pagoExitoso = true
                }) {
                    Text("Sí, pagar")
                }
            },
            dismissButton = {
                OutlinedButton(onClick = {
                    mostrarDialogoPago = false
                }) {
                    Text("Cancelar")
                }
            }
        )
    }

    //  MENSAJE DE ÉXITO (SIN CRASH)
    if (pagoExitoso) {
        AlertDialog(
            onDismissRequest = { pagoExitoso = false },
            title = { Text("Pago exitoso ✔") },
            text = { Text("Tu pedido fue pagado correctamente 🎉") },
            confirmButton = {
                Button(onClick = {
                    pagoExitoso = false
                    navController.popBackStack() //  esto evita crash
                }) {
                    Text("OK")
                }
            }
        )
    }
}

package com.example.saborandinoapp.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.saborandinoapp.data.PedidoManager

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PedidoScreen(navController: NavHostController) {

    //  Lista de productos agregados al carrito
    val pedido = PedidoManager.pedido

    //  Total calculado del pedido
    val total = PedidoManager.total()

    Scaffold(
        topBar = {

            //  Barra superior con botón regresar
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

            //  SI EL CARRITO ESTÁ VACÍO
            if (pedido.isEmpty()) {

                Text("Tu pedido está vacío 😢")

            } else {

                //  LISTA DE PRODUCTOS DEL PEDIDO
                LazyColumn(
                    modifier = Modifier.weight(1f)
                ) {

                    items(pedido) { plato ->

                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp)
                        ) {

                            Row(
                                modifier = Modifier.padding(12.dp)
                            ) {

                                //  IMAGEN DEL PLATO
                                Image(
                                    painter = painterResource(id = plato.imagen),
                                    contentDescription = plato.nombre,
                                    modifier = Modifier.size(70.dp)
                                )

                                Spacer(modifier = Modifier.width(12.dp))

                                Column(
                                    modifier = Modifier.weight(1f)
                                ) {

                                    //  NOMBRE DEL PLATO
                                    Text(
                                        text = plato.nombre,
                                        style = MaterialTheme.typography.titleMedium
                                    )

                                    //  PRECIO
                                    Text("S/. ${plato.precio}")

                                    Spacer(modifier = Modifier.height(6.dp))

                                    //  BOTÓN ELIMINAR DEL PEDIDO
                                    OutlinedButton(
                                        onClick = {
                                            // 🔹 Elimina el plato del carrito
                                            PedidoManager.eliminar(plato)
                                        }
                                    ) {
                                        Text("Eliminar")
                                    }
                                }
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                //  TOTAL FINAL DEL PEDIDO
                Text(
                    text = "Total: S/. $total",
                    style = MaterialTheme.typography.titleLarge
                )
            }
        }
    }
}
package com.example.saborandinoapp.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.saborandinoapp.data.listaPlatos
import com.example.saborandinoapp.data.PedidoManager

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetalleScreen(navController: NavHostController, id: Int) {

    //  Busca el plato seleccionado según el ID recibido desde el menú
    val plato = listaPlatos.find { it.id == id }

    // Estado para manejar la cantidad seleccionada por el usuario
    var cantidad by remember { mutableStateOf(1) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Detalle del Plato") },

                //  Botón para regresar a la pantalla anterior
                navigationIcon = {
                    IconButton(onClick = {
                        navController.popBackStack()
                    }) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
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
                .padding(16.dp)
        ) {

            if (plato != null) {

                //  IMAGEN DEL PLATO
                Image(
                    painter = painterResource(id = plato.imagen),
                    contentDescription = plato.nombre,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(220.dp)
                )

                Spacer(modifier = Modifier.height(10.dp))

                //  NOMBRE DEL PLATO
                Text(
                    text = plato.nombre,
                    style = MaterialTheme.typography.titleLarge
                )

                Spacer(modifier = Modifier.height(10.dp))

                //  DESCRIPCIÓN DEL PLATO
                Text(plato.descripcion)

                Spacer(modifier = Modifier.height(10.dp))

                // PRECIO UNITARIO
                Text(
                    text = "Precio: S/. ${plato.precio}",
                    style = MaterialTheme.typography.titleMedium
                )

                Spacer(modifier = Modifier.height(20.dp))

                //  SELECTOR DE CANTIDAD
                // Permite aumentar o disminuir la cantidad del plato
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
                ) {

                    //  Botón disminuir cantidad
                    Button(onClick = {
                        if (cantidad > 1) cantidad--
                    }) {
                        Text("-")
                    }

                    //  Muestra la cantidad seleccionada
                    Text(
                        text = cantidad.toString(),
                        modifier = Modifier.padding(horizontal = 16.dp),
                        style = MaterialTheme.typography.titleLarge
                    )

                    //  Botón aumentar cantidad
                    Button(onClick = {
                        cantidad++
                    }) {
                        Text("+")
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                //  BOTÓN AGREGAR AL PEDIDO
                Button(
                    onClick = {

                        //  Agrega el plato al carrito según la cantidad seleccionada
                        repeat(cantidad) {
                            PedidoManager.agregar(plato)
                        }

                        //  Navega a la pantalla de pedido
                        navController.navigate("pedido")
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Agregar al pedido")
                }

            } else {
                //  Si no se encuentra el plato
                Text("Plato no encontrado")
            }
        }
    }
}
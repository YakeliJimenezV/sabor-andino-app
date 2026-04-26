package com.example.saborandinoapp.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavHostController
import com.example.saborandinoapp.data.listaPlatos

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MenuScreen(navController: NavHostController) {

    //  Estado que guarda la categoría seleccionada
    var categoriaSeleccionada by remember { mutableStateOf("Todos") }

    Scaffold(
        topBar = {

            //  Barra superior con botón de regreso
            TopAppBar(
                title = { Text("Menú") },
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
        ) {

            //  CATEGORÍAS (con scroll horizontal)
            // Permite filtrar platos por tipo
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState())
                    .padding(8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {

                listOf("Todos", "Entradas", "Fondo", "Postres", "Bebidas").forEach { cat ->

                    Button(
                        onClick = {
                            // 🔹 Cambia la categoría seleccionada
                            categoriaSeleccionada = cat
                        }
                    ) {
                        Text(cat)
                    }
                }
            }

            //  FILTRADO DE PLATOS SEGÚN CATEGORÍA
            val platosFiltrados = if (categoriaSeleccionada == "Todos") {
                listaPlatos
            } else {
                listaPlatos.filter { it.categoria == categoriaSeleccionada }
            }

            //  LISTA DE PLATOS
            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {

                items(platosFiltrados) { plato ->

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                            .clickable {
                                //  Navega al detalle del plato con su ID
                                navController.navigate("detalle/${plato.id}")
                            }
                    ) {

                        Column {

                            //  IMAGEN DEL PLATO
                            Image(
                                painter = painterResource(id = plato.imagen),
                                contentDescription = plato.nombre,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(180.dp)
                            )

                            Column(
                                modifier = Modifier.padding(16.dp)
                            ) {

                                Text(
                                    text = plato.nombre,
                                    style = MaterialTheme.typography.titleMedium
                                )

                                Spacer(modifier = Modifier.height(4.dp))

                                Text(text = plato.descripcion)

                                Spacer(modifier = Modifier.height(4.dp))

                                Text(text = "S/. ${plato.precio}")
                            }
                        }
                    }
                }
            }
        }
    }
}
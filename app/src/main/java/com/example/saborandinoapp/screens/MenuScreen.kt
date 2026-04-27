package com.example.saborandinoapp.screens

import androidx.compose.animation.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.saborandinoapp.data.listaPlatos
import com.example.saborandinoapp.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MenuScreen(navController: NavHostController) {

    var categoriaSeleccionada by remember { mutableStateOf("Todos") }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("NUESTRA CARTA", fontWeight = FontWeight.Bold, color = Blanco) },
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
        ) {

            // Categorías horizontales
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState())
                    .padding(16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                listOf("Todos", "Entradas", "Fondo", "Postres", "Bebidas").forEach { cat ->
                    val isSelected = categoriaSeleccionada == cat
                    FilterChip(
                        selected = isSelected,
                        onClick = { categoriaSeleccionada = cat },
                        label = { Text(cat) },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = AzulMarino,
                            selectedLabelColor = Blanco,
                            containerColor = Blanco,
                            labelColor = AzulMedio
                        ),
                        border = FilterChipDefaults.filterChipBorder(
                            borderColor = AzulMedio,
                            selectedBorderColor = AzulMarino,
                            borderWidth = 1.dp,
                            selectedBorderWidth = 1.dp,
                            enabled = true,
                            selected = isSelected
                        ),
                        shape = RoundedCornerShape(12.dp)
                    )
                }
            }

            val platosFiltrados = if (categoriaSeleccionada == "Todos") {
                listaPlatos
            } else {
                listaPlatos.filter { it.categoria == categoriaSeleccionada }
            }

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(bottom = 16.dp)
            ) {
                items(platosFiltrados) { plato ->
                    var visible by remember { mutableStateOf(false) }
                    LaunchedEffect(plato) { visible = true }

                    AnimatedVisibility(
                        visible = visible,
                        enter = fadeIn() + slideInVertically(initialOffsetY = { it / 2 })
                    ) {
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp, vertical = 8.dp)
                                .clickable {
                                    navController.navigate("detalle/${plato.id}")
                                },
                            shape = RoundedCornerShape(16.dp),
                            colors = CardDefaults.cardColors(containerColor = Blanco),
                            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                        ) {
                            Column {
                                Image(
                                    painter = painterResource(id = plato.imagen),
                                    contentDescription = plato.nombre,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(200.dp),
                                    contentScale = ContentScale.Crop
                                )

                                Column(modifier = Modifier.padding(16.dp)) {
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Text(
                                            text = plato.nombre,
                                            style = MaterialTheme.typography.titleLarge,
                                            fontWeight = FontWeight.Bold,
                                            color = AzulMarino
                                        )
                                        Text(
                                            text = "S/. ${plato.precio}",
                                            fontWeight = FontWeight.Bold,
                                            color = AzulClaro,
                                            fontSize = 18.sp
                                        )
                                    }
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text(
                                        text = plato.descripcion,
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = AzulMedio.copy(alpha = 0.7f),
                                        maxLines = 2
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
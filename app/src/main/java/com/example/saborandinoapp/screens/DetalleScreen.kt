package com.example.saborandinoapp.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddShoppingCart
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.saborandinoapp.data.PedidoManager
import com.example.saborandinoapp.data.listaPlatos
import com.example.saborandinoapp.ui.theme.*

@Composable
fun DetalleScreen(navController: NavHostController, id: Int) {

    val plato = listaPlatos.find { it.id == id } ?: return

    var cantidad by remember { mutableStateOf(1) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(GrisClaro)
    ) {
        Box {
            Image(
                painter = painterResource(id = plato.imagen),
                contentDescription = plato.nombre,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp),
                contentScale = ContentScale.Crop
            )
            IconButton(
                onClick = { navController.popBackStack() },
                modifier = Modifier
                    .padding(16.dp)
                    .background(Color.Black.copy(alpha = 0.3f), CircleShape)
            ) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Volver", tint = Blanco)
            }
        }

        Column(
            modifier = Modifier
                .padding(24.dp)
                .fillMaxSize()
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = plato.nombre,
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = AzulMarino,
                    modifier = Modifier.weight(1f)
                )

                Text(
                    text = "S/. ${plato.precio}",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = AzulClaro
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = plato.descripcion,
                style = MaterialTheme.typography.bodyLarge,
                color = AzulMedio.copy(alpha = 0.8f),
                lineHeight = 24.sp
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Selector de cantidad con estilo mejorado
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedIconButton(
                    onClick = { if (cantidad > 1) cantidad-- },
                    shape = RoundedCornerShape(12.dp),
                    colors = IconButtonDefaults.outlinedIconButtonColors(contentColor = AzulMarino)
                ) {
                    Text("-", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                }

                Text(
                    text = cantidad.toString(),
                    modifier = Modifier.padding(horizontal = 24.dp),
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    color = AzulMarino
                )

                OutlinedIconButton(
                    onClick = { cantidad++ },
                    shape = RoundedCornerShape(12.dp),
                    colors = IconButtonDefaults.outlinedIconButtonColors(contentColor = AzulMarino)
                ) {
                    Text("+", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = {
                    repeat(cantidad) {
                        PedidoManager.agregar(plato)
                    }
                    navController.navigate("pedido")
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp),
                colors = ButtonDefaults.buttonColors(containerColor = AzulMarino),
                shape = RoundedCornerShape(16.dp)
            ) {
                Icon(Icons.Default.AddShoppingCart, contentDescription = null)
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    "AGREGAR AL PEDIDO",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = Blanco
                )
            }
        }
    }
}
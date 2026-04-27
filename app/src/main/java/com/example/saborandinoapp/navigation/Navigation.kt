package com.example.saborandinoapp.navigation

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.*
import androidx.navigation.navArgument
import com.example.saborandinoapp.screens.*

@Composable
fun AppNavigation() {

    //  Controlador principal de navegación (permite moverse entre pantallas)
    val navController = rememberNavController()

    //  Contenedor de navegación donde se definen todas las rutas
    NavHost(
        navController = navController,
        startDestination = "login" // Pantalla inicial de la app
    ) {

        //  PANTALLA LOGIN
        composable("login") {
            // Muestra la pantalla de inicio de sesión
            LoginScreen(navController)
        }

        //  PANTALLA HOME con parámetros (usuario y email)
        composable(
            route = "home/{usuario}/{email}",
            arguments = listOf(
                navArgument("usuario") { type = NavType.StringType }, // recibe nombre
                navArgument("email") { type = NavType.StringType }    // recibe correo
            )
        ) { backStackEntry ->

            //  Se obtienen los datos enviados desde LoginScreen
            val usuario = backStackEntry.arguments?.getString("usuario") ?: ""
            val email = backStackEntry.arguments?.getString("email") ?: ""

            //  Se envían a HomeScreen para saludo personalizado
            HomeScreen(navController, usuario, email)
        }

        //  PANTALLA MENÚ (lista de platos)
        composable("menu") {
            MenuScreen(navController)
        }

        //  PANTALLA PEDIDO (carrito de compras)
        composable("pedido") {
            PedidoScreen(navController)
        }

        //  PANTALLA PERFIL con datos del usuario
        composable(
            route = "perfil/{usuario}/{email}",
            arguments = listOf(
                navArgument("usuario") { type = NavType.StringType },
                navArgument("email") { type = NavType.StringType }
            )
        ) { backStackEntry ->

            //  Recupera datos del usuario logueado
            val usuario = backStackEntry.arguments?.getString("usuario") ?: ""
            val email = backStackEntry.arguments?.getString("email") ?: ""

            //  Envía datos al perfil para mostrarlos
            PerfilScreen(navController, usuario, email)
        }

        // 🍽 DETALLE DEL PLATO (recibe ID)
        composable(
            route = "detalle/{id}",
            arguments = listOf(
                navArgument("id") { type = NavType.IntType } // ID del plato seleccionado
            )
        ) { backStackEntry ->

            //  Se obtiene el ID enviado desde el menú
            val id = backStackEntry.arguments?.getInt("id")

            //  Validación del ID para evitar errores
            if (id != null && id > 0) {
                // Muestra pantalla de detalle del plato
                DetalleScreen(navController, id)
            } else {
                // Mensaje de error si el ID no es válido
                Text("Error: ID inválido")
            }
        }
    }
}
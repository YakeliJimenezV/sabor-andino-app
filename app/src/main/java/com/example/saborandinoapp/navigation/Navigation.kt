package com.example.saborandinoapp.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.*
import androidx.navigation.navArgument
import com.example.saborandinoapp.screens.*

@Composable
fun AppNavigation() {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "login",
        enterTransition = {
            fadeIn(animationSpec = tween(400)) + slideIntoContainer(
                AnimatedContentTransitionScope.SlideDirection.Left,
                animationSpec = tween(400)
            )
        },
        exitTransition = {
            fadeOut(animationSpec = tween(400)) + slideOutOfContainer(
                AnimatedContentTransitionScope.SlideDirection.Left,
                animationSpec = tween(400)
            )
        },
        popEnterTransition = {
            fadeIn(animationSpec = tween(400)) + slideIntoContainer(
                AnimatedContentTransitionScope.SlideDirection.Right,
                animationSpec = tween(400)
            )
        },
        popExitTransition = {
            fadeOut(animationSpec = tween(400)) + slideOutOfContainer(
                AnimatedContentTransitionScope.SlideDirection.Right,
                animationSpec = tween(400)
            )
        }
    ) {

        composable("login") {
            LoginScreen(navController)
        }

        composable(
            route = "home/{usuario}/{email}",
            arguments = listOf(
                navArgument("usuario") { type = NavType.StringType },
                navArgument("email") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val usuario = backStackEntry.arguments?.getString("usuario") ?: ""
            val email = backStackEntry.arguments?.getString("email") ?: ""
            HomeScreen(navController, usuario, email)
        }

        composable("menu") {
            MenuScreen(navController)
        }

        composable("pedido") {
            PedidoScreen(navController)
        }

        composable(
            route = "perfil/{usuario}/{email}",
            arguments = listOf(
                navArgument("usuario") { type = NavType.StringType },
                navArgument("email") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val usuario = backStackEntry.arguments?.getString("usuario") ?: ""
            val email = backStackEntry.arguments?.getString("email") ?: ""
            PerfilScreen(navController, usuario, email)
        }

        composable(
            route = "detalle/{id}",
            arguments = listOf(
                navArgument("id") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getInt("id")
            if (id != null && id > 0) {
                DetalleScreen(navController, id)
            } else {
                Text("Error: ID inválido")
            }
        }
    }
}
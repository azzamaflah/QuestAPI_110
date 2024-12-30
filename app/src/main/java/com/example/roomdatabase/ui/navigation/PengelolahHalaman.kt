package com.example.roomdatabase.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.roomdatabase.ui.view.DestinasiDetail
import com.example.roomdatabase.ui.view.DestinasiEntry
import com.example.roomdatabase.ui.view.DestinasiEntry.EntryMhsScreen
import com.example.roomdatabase.ui.view.DestinasiHome
import com.example.roomdatabase.ui.view.DetailScreen
import com.example.roomdatabase.ui.view.HomeScreen

@Composable
fun PengelolaHalaman(
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = DestinasiHome.route,
        modifier = Modifier
    ) {
        // Layar Home
        composable(DestinasiHome.route) {
            HomeScreen(
                navigateToItemEntry = { navController.navigate(DestinasiEntry.route) },
                onDetailClick = { nim ->
                    navController.navigate("${DestinasiDetail.route}/$nim")
                }
            )
        }

        // Layar Entry
        composable(DestinasiEntry.route) {
            EntryMhsScreen(
                navigateBack = {
                    navController.navigate(DestinasiHome.route) {
                        popUpTo(DestinasiHome.route) {
                            inclusive = true
                        }
                    }
                }
            )
        }
        composable(
            route = "${DestinasiDetail.route}/{nim}",
            arguments = listOf(navArgument("nim") { type = NavType.StringType })
        ) {
            val nim = it.arguments?.getString("nim")
            nim?.let { nimValue ->
                DetailScreen(
                    nim = nimValue,
                    navigateBack = { navController.popBackStack() }
                )
            }
        }
    }
}

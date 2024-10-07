package com.munra.gestion_prestamo.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.munra.gestion_prestamo.ui.home.HomeDestination
import com.munra.gestion_prestamo.ui.home.HomeScreen
import com.munra.gestion_prestamo.ui.user.UserEntryDestination
import com.munra.gestion_prestamo.ui.user.UserEntryScreen
import com.munra.gestion_prestamo.ui.user.UserListDestination
import com.munra.gestion_prestamo.ui.user.UserListScreen

@Composable
fun GestionPrestamoNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
){

    NavHost(
        navController = navController,
        startDestination = HomeDestination.route,
        modifier = modifier
    ){
        composable(route = HomeDestination.route) {
            HomeScreen(
                navigateToUser = { navController.navigate(UserListDestination.route) }
            )
        }
        composable(route = UserListDestination.route) {
            UserListScreen(
                navigateToBack = {navController.navigate(HomeDestination.route)},
                navigateToUserUpdate = {
                    navController.navigate("${UserListDestination.route}/${it}")
                },
                navigateToUserEntry = { navController.navigate(UserEntryDestination.route) },
            )
        }
        composable(route = UserEntryDestination.route) {
            UserEntryScreen(
                navigateToBack = {navController.navigate(UserListDestination.route)}
            )
        }
    }

}
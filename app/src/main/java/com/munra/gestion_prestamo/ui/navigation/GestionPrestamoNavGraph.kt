package com.munra.gestion_prestamo.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.munra.gestion_prestamo.ui.home.HomeDestination
import com.munra.gestion_prestamo.ui.home.HomeScreen
import com.munra.gestion_prestamo.ui.user.UserDetailsDestination
import com.munra.gestion_prestamo.ui.user.UserDetailsScreen
import com.munra.gestion_prestamo.ui.user.UserEditDestination
import com.munra.gestion_prestamo.ui.user.UserEditScreen
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
                navigateToUser = { navController.navigate(UserListDestination.route) },
                navigateToClient = {}
            )
        }

        //region User Navigation
        composable(route = UserListDestination.route) {
            UserListScreen(
                navigateToBack = {navController.navigate(HomeDestination.route)},
                navigateToUserUpdate = {
                    navController.navigate("${UserDetailsDestination.route}/${it}")
                },
                navigateToUserEntry = { navController.navigate(UserEntryDestination.route) },
            )
        }
        composable(route = UserEntryDestination.route) {
            UserEntryScreen(
                navigateToBack = {navController.navigate(UserListDestination.route)}
            )
        }
        composable(
            route = UserDetailsDestination.routeWithArgs,
            arguments = listOf(navArgument(UserDetailsDestination.userIdArg) {
                type = NavType.IntType
            })
        ) {
            UserDetailsScreen(
                navigateToEditUser = { navController.navigate("${UserEditDestination.route}/$it") },
                navigateBack = { navController.navigateUp() }
            )
        }
        composable(
            route = UserEditDestination.routeWithArgs,
            arguments = listOf(navArgument(UserEditDestination.userIdArg) {
                type = NavType.IntType
            })
        ) {
            UserEditScreen(
                navigateBack = { navController.popBackStack() },
                onNavigateUp = { navController.navigateUp() }
            )
        }
        //endregion

        //region Client Navigation
        composable(route = UserListDestination.route) {
            UserListScreen(
                navigateToBack = {navController.navigate(HomeDestination.route)},
                navigateToUserUpdate = {
                    navController.navigate("${UserDetailsDestination.route}/${it}")
                },
                navigateToUserEntry = { navController.navigate(UserEntryDestination.route) },
            )
        }
        //endregion
    }
}
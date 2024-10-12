package com.munra.gestion_prestamo.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.munra.gestion_prestamo.ui.client.ClientDetailsDestination
import com.munra.gestion_prestamo.ui.client.ClientDetailsScreen
import com.munra.gestion_prestamo.ui.client.ClientEditDestination
import com.munra.gestion_prestamo.ui.client.ClientEditScreen
import com.munra.gestion_prestamo.ui.client.ClientEntryDestination
import com.munra.gestion_prestamo.ui.client.ClientEntryScreen
import com.munra.gestion_prestamo.ui.client.ClientListDestination
import com.munra.gestion_prestamo.ui.client.ClientListScreen
import com.munra.gestion_prestamo.ui.home.HomeDestination
import com.munra.gestion_prestamo.ui.home.HomeScreen
import com.munra.gestion_prestamo.ui.login.LoginDestination
import com.munra.gestion_prestamo.ui.login.LoginScreen
import com.munra.gestion_prestamo.ui.user.UserDetailsDestination
import com.munra.gestion_prestamo.ui.user.UserDetailsScreen
import com.munra.gestion_prestamo.ui.user.UserEditDestination
import com.munra.gestion_prestamo.ui.user.UserEditScreen
import com.munra.gestion_prestamo.ui.user.UserEntryDestination
import com.munra.gestion_prestamo.ui.user.UserEntryScreen
import com.munra.gestion_prestamo.ui.user.UserListDestination
import com.munra.gestion_prestamo.ui.user.UserListScreen
import com.munra.gestion_prestamo.ui.loan.LoanListDestination
import com.munra.gestion_prestamo.ui.loan.LoanListScreen

@Composable
fun GestionPrestamoNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
){

    NavHost(
        navController = navController,
        startDestination = LoginDestination.route,
        modifier = modifier
    ){
        composable(route = LoginDestination.route) {
            LoginScreen(
                navigateToHome = { navController.navigate(HomeDestination.route) },
                navigateToUserEntry = { navController.navigate(UserEntryDestination.route) },
            )
        }
        composable(route = HomeDestination.route) {
            HomeScreen(
                navigateToUser = { navController.navigate(UserListDestination.route) },
                navigateToClient = { navController.navigate(ClientListDestination.route) },
                navigateToLoan = { navController.navigate(LoanListDestination.route) }

            )
        }

        //region User Navigation
        composable(route = UserListDestination.route) {
            UserListScreen(
                navigateToBack = {navController.navigate(HomeDestination.route)},
                navigateToUserDetails = {
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
        composable(route = ClientListDestination.route) {
            ClientListScreen(
                navigateToBack = {navController.navigate(HomeDestination.route)},
                navigateToClientDetails = {
                    navController.navigate("${ClientDetailsDestination.route}/${it}")
                },
                navigateToClientEntry = { navController.navigate(ClientEntryDestination.route) },
            )
        }
        composable(route = ClientEntryDestination.route) {
            ClientEntryScreen(
                navigateToBack = {navController.navigate(ClientListDestination.route)}
            )
        }
        composable(
            route = ClientDetailsDestination.routeWithArgs,
            arguments = listOf(navArgument(ClientDetailsDestination.clientIdArg) {
                type = NavType.IntType
            })
        ) {
            ClientDetailsScreen(
                navigateToEditClient = { navController.navigate("${ClientEditDestination.route}/$it") },
                navigateBack = { navController.navigateUp() }
            )
        }
        composable(
            route = ClientEditDestination.routeWithArgs,
            arguments = listOf(navArgument(ClientEditDestination.clientIdArg) {
                type = NavType.IntType
            })
        ) {
            ClientEditScreen(
                navigateBack = { navController.popBackStack() },
                onNavigateUp = { navController.navigateUp() }
            )
        }
        //endregion

            composable(route = LoanListDestination.route) {
                        LoanListScreen(
                            navigateToBack = {navController.navigate(HomeDestination.route)},
                            navigateToClientDetails = {
                                navController.navigate("${ClientDetailsDestination.route}/${it}")
                            },
                            navigateToClientEntry = { navController.navigate(ClientEntryDestination.route) },
                        )
                    }
                    composable(route = ClientEntryDestination.route) {
                        ClientEntryScreen(
                            navigateToBack = {navController.navigate(ClientListDestination.route)}
                        )
                    }
                    composable(
                        route = ClientDetailsDestination.routeWithArgs,
                        arguments = listOf(navArgument(ClientDetailsDestination.clientIdArg) {
                            type = NavType.IntType
                        })
                    ) {
                        ClientDetailsScreen(
                            navigateToEditClient = { navController.navigate("${ClientEditDestination.route}/$it") },
                            navigateBack = { navController.navigateUp() }
                        )
                    }
    }
}
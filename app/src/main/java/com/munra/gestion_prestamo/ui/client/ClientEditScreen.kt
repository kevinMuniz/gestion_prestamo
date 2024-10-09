package com.munra.gestion_prestamo.ui.client

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import com.munra.gestion_prestamo.GestionPrestamoTopAppBar
import com.munra.gestion_prestamo.R
import com.munra.gestion_prestamo.ui.AppViewModelProvider
import com.munra.gestion_prestamo.ui.navigation.NavigationDestination
import kotlinx.coroutines.launch

object ClientEditDestination : NavigationDestination {
    override val route = "client_edit"
    override val titleRes = R.string.title_client_edit
    const val clientIdArg = "clientId"
    val routeWithArgs = "$route/{$clientIdArg}"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ClientEditScreen(
    navigateBack: () -> Unit,
    onNavigateUp: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: ClientEditViewModel = viewModel(factory = AppViewModelProvider.Factory)
){
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            GestionPrestamoTopAppBar(
                title = stringResource(ClientEditDestination.titleRes),
                canNavigateBack = true,
                navigateUp = onNavigateUp
            )
        },
        modifier = modifier
    ) { innerPadding ->
        ClientEntryBody(
            clientUiState = viewModel.clientUiState,
            onItemValueChange = viewModel::updateUiState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.updateClient()
                    navigateBack()
                }
            },
            modifier = modifier.padding(innerPadding)
        )
    }
}
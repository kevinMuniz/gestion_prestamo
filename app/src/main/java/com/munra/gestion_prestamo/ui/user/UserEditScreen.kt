package com.munra.gestion_prestamo.ui.user

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.munra.gestion_prestamo.GestionPrestamoTopAppBar
import com.munra.gestion_prestamo.R
import com.munra.gestion_prestamo.ui.AppViewModelProvider
import com.munra.gestion_prestamo.ui.navigation.NavigationDestination
import com.munra.gestion_prestamo.ui.theme.Gestion_prestamoTheme
import kotlinx.coroutines.launch

object UserEditDestination : NavigationDestination {
    override val route = "user_edit"
    override val titleRes = R.string.title_user_edit
    const val userIdArg = "userId"
    val routeWithArgs = "$route/{$userIdArg}"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserEditScreen(
    navigateBack: () -> Unit,
    onNavigateUp: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: UserEditViewModel = viewModel(factory = AppViewModelProvider.Factory)
){
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            GestionPrestamoTopAppBar(
                title = stringResource(UserEditDestination.titleRes),
                canNavigateBack = true,
                navigateUp = onNavigateUp
            )
        },
        modifier = modifier
    ) { innerPadding ->
        UserEntryBody(
            userUiState = viewModel.userUiState,
            onItemValueChange = viewModel::updateUiState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.updateUser()
                    navigateBack()
                }
            },
            modifier = modifier.padding(innerPadding)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ItemEditScreenPreview() {
    Gestion_prestamoTheme {
        UserEditScreen(navigateBack = { /*Do nothing*/ }, onNavigateUp = { /*Do nothing*/ })
    }
}
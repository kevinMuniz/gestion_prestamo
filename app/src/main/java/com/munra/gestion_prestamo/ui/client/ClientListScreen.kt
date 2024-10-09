package com.munra.gestion_prestamo.ui.client

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.munra.gestion_prestamo.GestionPrestamoTopAppBar
import com.munra.gestion_prestamo.R
import com.munra.gestion_prestamo.data.client.Client
import com.munra.gestion_prestamo.ui.AppViewModelProvider
import com.munra.gestion_prestamo.ui.navigation.NavigationDestination
import com.munra.gestion_prestamo.ui.theme.Gestion_prestamoTheme

object ClientListDestination : NavigationDestination {
    override val route = "client_list"
    override val titleRes = R.string.title_client_list
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ClientListScreen(
    navigateToBack: () -> Unit,
    navigateToClientDetails: (Int) -> Unit,
    navigateToClientEntry: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: ClientListViewModel =  viewModel(factory = AppViewModelProvider.Factory)
){
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val clientUiState by viewModel.clientListUiState.collectAsState()

    Scaffold(
        topBar = {
            GestionPrestamoTopAppBar(
                title = stringResource(ClientListDestination.titleRes),
                canNavigateBack = true,
                navigateUp = navigateToBack,
                scrollBehavior = scrollBehavior
            )
        },floatingActionButton = {
            FloatingActionButton(
                onClick = navigateToClientEntry,
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_large))
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = stringResource(R.string.lbl_agregar)
                )
            }
        },
    ) { innerPadding ->
        ClientBody(
            clientList = clientUiState.clientList,
            onClientClick = navigateToClientDetails,
            modifier = modifier
                .padding(innerPadding)
                .fillMaxSize()
        )
    }
}

@Composable
private fun ClientBody(
    clientList: List<Client>,
    onClientClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp),
){
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier,
    ) {
        if (clientList.isEmpty()) {
            Text(
                text = stringResource(R.string.no_client),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(contentPadding),
            )
        } else {
            ClientList(
                clientList = clientList,
                onClientClick = { onClientClick(it.id) },
                contentPadding = contentPadding,
                modifier = Modifier.padding(horizontal = dimensionResource(id = R.dimen.padding_small))
            )
        }
    }
}

@Composable
private fun ClientList(
    clientList: List<Client>,
    onClientClick: (Client) -> Unit,
    contentPadding: PaddingValues,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = contentPadding
    ) {
        items(items = clientList, key = { it.id }) { client ->
            ClientItem(
                client = client,
                modifier = Modifier
                    .padding(dimensionResource(id = R.dimen.padding_small))
                    .clickable { onClientClick(client) }
            )
        }
    }
}

@Composable
private fun ClientItem(
    client: Client, modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_large)),
            verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_small))
        ) {
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = client.fullName,
                    style = MaterialTheme.typography.titleLarge,
                )
                Spacer(Modifier.weight(1f))
                Text(
                    text = client.document,
                    style = MaterialTheme.typography.titleMedium
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeBodyPreview() {
    Gestion_prestamoTheme {
        ClientBody(listOf(
            Client(1, "Kevin Muniz", "1314853209",
                "Marbella", "097956004")
        ), onClientClick = {})
    }
}
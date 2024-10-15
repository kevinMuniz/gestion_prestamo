package com.munra.gestion_prestamo.ui.loan

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
import com.munra.gestion_prestamo.data.prestamo.Prestamo
import com.munra.gestion_prestamo.data.prestamo.TipoPlazo
import com.munra.gestion_prestamo.ui.AppViewModelProvider
import com.munra.gestion_prestamo.ui.loan.LoanListViewModel
import com.munra.gestion_prestamo.ui.login.LoginViewModel
import com.munra.gestion_prestamo.ui.navigation.NavigationDestination
import com.munra.gestion_prestamo.ui.theme.Gestion_prestamoTheme

object LoanListDestination : NavigationDestination {
    override val route = "prestamo_list"
    override val titleRes = R.string.title_loan_list
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoanListScreen(
    navigateToBack: () -> Unit,
    navigateToLoanDetails: (Int) -> Unit,
    navigateToLoanEntry: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: LoanListViewModel =  viewModel(factory = AppViewModelProvider.Factory)

){
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val loanListUiState by viewModel.loanListUiState.collectAsState()

    Scaffold(
        topBar = {
            GestionPrestamoTopAppBar(
                title = stringResource(LoanListDestination.titleRes),
                canNavigateBack = true,
                navigateUp = navigateToBack,
                scrollBehavior = scrollBehavior
            )
        },floatingActionButton = {
            FloatingActionButton(
                onClick = navigateToLoanEntry,
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
        LoanBody(
            loanList = loanListUiState.loantList,
            onClientClick = navigateToLoanDetails,
            modifier = modifier
                .padding(innerPadding)
                .fillMaxSize()
        )
    }
}

@Composable
private fun LoanBody(
    loanList: List<Prestamo>,
    onClientClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp),
){
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier,
    ) {
        if (loanList.isEmpty()) {
            Text(
                text = stringResource(R.string.no_prestamo),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(contentPadding),
            )
        } else {
            LoanList(
                loanList = loanList,
                onClientClick = { onClientClick(it.id) },
                contentPadding = contentPadding,
                modifier = Modifier.padding(horizontal = dimensionResource(id = R.dimen.padding_small))
            )
        }
    }
}

@Composable
private fun LoanList(
    loanList: List<Prestamo>,
    onClientClick: (Prestamo) -> Unit,
    contentPadding: PaddingValues,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = contentPadding
    ) {
        items(items = loanList, key = { it.id }) { loan ->
            LoantItem(
                loan = loan,
                modifier = Modifier
                    .padding(dimensionResource(id = R.dimen.padding_small))
                    .clickable { onClientClick(loan) }
            )
        }
    }
}

@Composable
private fun LoantItem(
    loan: Prestamo, modifier: Modifier = Modifier
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
                    text = loan.id.toString(),
                    style = MaterialTheme.typography.titleLarge,
                )
                Spacer(Modifier.weight(1f))
                Text(
                    text = loan.valor.toString(),
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
        LoanBody(listOf(
            Prestamo(1,2, 10.0, TipoPlazo.SEMANAL, 8, 1.0,
                1, "097956004")
        ), onClientClick = {})
    }
}
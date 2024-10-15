package com.munra.gestion_prestamo.ui.loan

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.munra.gestion_prestamo.GestionPrestamoTopAppBar
import com.munra.gestion_prestamo.R
import com.munra.gestion_prestamo.data.client.Client
import com.munra.gestion_prestamo.ui.AppViewModelProvider
import com.munra.gestion_prestamo.ui.navigation.NavigationDestination
import com.munra.gestion_prestamo.ui.theme.Gestion_prestamoTheme
import kotlinx.coroutines.launch

object LoanEntryDestination: NavigationDestination {
    override val route = "loan_entry"
    override val titleRes = R.string.title_loan_entry
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoanEntryScreen(
    navigateToBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: LoanEntryViewModel = viewModel(factory = AppViewModelProvider.Factory)
){
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val coroutineScope = rememberCoroutineScope()
    val clientUiState by viewModel.clientListUiState.collectAsState()

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            GestionPrestamoTopAppBar (
                title = stringResource(LoanEntryDestination.titleRes),
                canNavigateBack = false,
                scrollBehavior = scrollBehavior
            )
        }
    ){ innerPadding ->

        LoanEntryBody(
            loanUiState = viewModel.loanUiDetails,
            onItemValueChange = viewModel::updateUiState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.saveLoan()
                    navigateToBack()
                }
            },
            clientList = clientUiState.clientList,
            modifier = Modifier
                .padding(
                    start = innerPadding.calculateStartPadding(LocalLayoutDirection.current),
                    end = innerPadding.calculateEndPadding(LocalLayoutDirection.current),
                    top = innerPadding.calculateTopPadding()
                )
                .verticalScroll(rememberScrollState())
                .fillMaxWidth()
        )
    }
}



@Composable
fun LoanEntryBody(
    loanUiState: LoanUiState,
    onItemValueChange: (LoanDetails) -> Unit,
    onSaveClick: () -> Unit,
    clientList: List<Client>,
    modifier: Modifier = Modifier
){
    Column(
        verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_large)),
        modifier = modifier.padding(dimensionResource(id = R.dimen.padding_medium))
    ) {
        LoanInputForm(
            loanDetails = loanUiState.loanDetails,
            clientList=clientList,

            onValueChange = onItemValueChange,
            modifier = Modifier.fillMaxWidth()
        )
        Button(
            onClick = onSaveClick,
            //enabled = loanUiState.isEntryValid,
            shape = MaterialTheme.shapes.small,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = stringResource(R.string.btn_save_action))
        }
    }
}
data class Person(val fullName: String)

@Composable
fun LoanInputForm(
    loanDetails: LoanDetails,
    clientList: List<Client>,
    modifier: Modifier = Modifier,
    onValueChange: (LoanDetails) -> Unit = {},
    enabled: Boolean = true
){
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_medium))
    ) {
        if (clientList.isEmpty()) {
            Text(
                text = stringResource(R.string.no_client),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleLarge,
            )
        } else {



            val personNames = clientList.map { it.fullName }

            var expanded by remember { mutableStateOf(false) }
            var selectedPersonName by remember { mutableStateOf("") }
            var selectedPersonId by remember { mutableStateOf(-1) }  // Para guardar el ID seleccionado

            Box(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = if (selectedPersonName.isEmpty()) "Seleccione un Cliente " else selectedPersonName,
                    modifier = Modifier.clickable { expanded = true }
                )
                DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                    clientList.forEach { person ->
                        DropdownMenuItem(
                            text = { Text(text = person.fullName) },
                            onClick = {
                                selectedPersonName = person.fullName  // Mostrar el nombre
                                selectedPersonId = person.id  // Guardar el ID
                                expanded = false
                                onValueChange(loanDetails.copy(idPrestamista = selectedPersonId))

                            }
                        )
                    }
                }
            }


            OutlinedTextField(
                value = loanDetails.valor.toString(),  // Convertimos el valor Double a String
                onValueChange = { newValue ->
                    // Intentamos convertir el nuevo valor a Double
                    val parsedValue = newValue.toDoubleOrNull()  // Devuelve null si no es un número válido
                    if (parsedValue != null) {
                        onValueChange(loanDetails.copy(valor = parsedValue)) // Solo actualizamos si es válido
                    } else {
                        // Aquí puedes manejar el caso de un valor inválido si lo deseas
                        // Por ejemplo, mostrar un mensaje de error o simplemente ignorar el cambio
                    }
                },
                label = { Text(stringResource(R.string.title_loan_valor)) },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                    unfocusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                    disabledContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                ),
                modifier = Modifier.fillMaxWidth(),
                enabled = enabled,
                singleLine = true
            )

            val tipoPlazo = listOf("SEMANAL", "QUINCENAL", "MENSUAL")


            val isDropDownExpanded = remember {
                mutableStateOf(false)
            }

            val itemPosition = remember {
                mutableStateOf(0)
            }

            Box {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.clickable {
                        isDropDownExpanded.value = true
                    }
                ) {
                    Text(text = tipoPlazo[itemPosition.value])
//                    Image(
//                        painter = painterResource(id = R.drawable.drop_down_ic),
//                        contentDescription = "DropDown Icon"
//                    )
                }
                DropdownMenu(
                    expanded = isDropDownExpanded.value,
                    onDismissRequest = {
                        isDropDownExpanded.value = false
                    }) {
                    tipoPlazo.forEachIndexed { index, username ->
                        DropdownMenuItem(text = {
                            Text(text = username)
                        },
                            onClick = {
                                isDropDownExpanded.value = false
                                itemPosition.value = index
                            })
                    }
                }
            }

            OutlinedTextField(
                value = loanDetails.plazo.toString(),  // Convertimos el valor Double a String
                onValueChange = { newValue ->
                    // Intentamos convertir el nuevo valor a Double
                    val parsedValue = newValue.toDoubleOrNull()  // Devuelve null si no es un número válido
                    if (parsedValue != null) {
                        onValueChange(loanDetails.copy(plazo = parsedValue.toInt())) // Solo actualizamos si es válido
                    } else {
                        // Aquí puedes manejar el caso de un valor inválido si lo deseas
                        // Por ejemplo, mostrar un mensaje de error o simplemente ignorar el cambio
                    }
                },
                label = { Text(stringResource(R.string.title_loan_valor)) },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                    unfocusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                    disabledContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                ),
                modifier = Modifier.fillMaxWidth(),
                enabled = enabled,
                singleLine = true
            )


        }









    }

}
//@Composable
//fun ClientInputForm(
//    loanDetails: LoanDetails,
//    modifier: Modifier = Modifier,
//    onValueChange: (LoanDetails) -> Unit = {},
//    enabled: Boolean = true
//){
//    Column(
//        modifier = modifier,
//        verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_medium))
//    ) {
//        OutlinedTextField(
//            value = loanDetails.fechaPrestamo,
//            onValueChange = { onValueChange(loanDetails.copy(fechaPrestamo = it)) },
//            label = { Text(stringResource(R.string.lbl_fullname)) },
//            colors = OutlinedTextFieldDefaults.colors(
//                focusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
//                unfocusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
//                disabledContainerColor = MaterialTheme.colorScheme.secondaryContainer,
//            ),
//            modifier = Modifier.fillMaxWidth(),
//            enabled = enabled,
//            singleLine = true
//        )
//        OutlinedTextField(
//            value = loanDetails.fechaPrestamo,
//            onValueChange = { onValueChange(loanDetails.copy(fechaPrestamo = it)) },
//            label = { Text(stringResource(R.string.lbl_ci)) },
//            colors = OutlinedTextFieldDefaults.colors(
//                focusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
//                unfocusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
//                disabledContainerColor = MaterialTheme.colorScheme.secondaryContainer,
//            ),
//            modifier = Modifier.fillMaxWidth(),
//            enabled = enabled,
//            singleLine = true
//        )
//        OutlinedTextField(
//            value = loanDetails.fechaPrestamo,
//            onValueChange = { onValueChange(loanDetails.copy(fechaPrestamo = it)) },
//            label = { Text(stringResource(R.string.lbl_addrees)) },
//            colors = OutlinedTextFieldDefaults.colors(
//                focusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
//                unfocusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
//                disabledContainerColor = MaterialTheme.colorScheme.secondaryContainer,
//            ),
//            modifier = Modifier.fillMaxWidth(),
//            enabled = enabled,
//            singleLine = true
//        )
//        OutlinedTextField(
//            value = loanDetails.fechaPrestamo,
//            onValueChange = { onValueChange(loanDetails.copy(fechaPrestamo = it)) },
//            label = { Text(stringResource(R.string.lbl_phone)) },
//            colors = OutlinedTextFieldDefaults.colors(
//                focusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
//                unfocusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
//                disabledContainerColor = MaterialTheme.colorScheme.secondaryContainer,
//            ),
//            modifier = Modifier.fillMaxWidth(),
//            enabled = enabled,
//            singleLine = true
//        )
//
//        if (enabled) {
//            Text(
//                text = stringResource(R.string.required_fields),
//                modifier = Modifier.padding(start = dimensionResource(id = R.dimen.padding_medium))
//            )
//        }
//    }
//}
//
////@Composable
////fun PersonNameDropdown(personViewModel: PersonViewModel = viewModel()) {
////    val personNames by personViewModel.personNames.collectAsState()
////    var expanded by remember { mutableStateOf(false) }
////    var selectedPerson by remember { mutableStateOf("") }
////
////    Box(modifier = Modifier.fillMaxWidth()) {
////        Text(
////            text = if (selectedPerson.isEmpty()) "Select a person" else selectedPerson,
////            modifier = Modifier.clickable { expanded = true }
////        )
////        DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
////            personNames.forEach { name ->
////                DropdownMenuItem(onClick = {
////                    selectedPerson = name
////                    expanded = false
////                }) {
////                    Text(text = name)
////                }
////            }
////        }
////    }
////}

@Preview(showBackground = true)
@Composable
private fun ItemEntryScreenPreview() {
    Gestion_prestamoTheme {
//        LoanEntryBody(
//            loanUiState = LoanUiState(
//            LoanDetails(
//                fechaPrestamo = "Kevin Muniz",
//            )
//        ), onItemValueChange = {}, onSaveClick = {})
    }
}

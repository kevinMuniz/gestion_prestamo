package com.munra.gestion_prestamo.ui.client

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.munra.gestion_prestamo.data.client.Client
import com.munra.gestion_prestamo.data.client.ClientRepository

class ClientEntryViewModel(private val clientRepository: ClientRepository): ViewModel() {

    var clientUiDetails by mutableStateOf(ClientUiState())
        private set

    fun updateUiState(clientDetails: ClientDetails) {
        clientUiDetails =
            ClientUiState(clientDetails = clientDetails, isEntryValid = validateInputClientEntry(clientDetails))
    }

    private fun validateInputClientEntry(uiState: ClientDetails = clientUiDetails.clientDetails): Boolean {
        return with(uiState) {
            fullName.isNotBlank() && document.isNotBlank() && address.isNotBlank() && phone.isNotBlank()
        }
    }

    suspend fun saveClient() {
        if (validateInputClientEntry()) {
            clientRepository.insertClient(clientUiDetails.clientDetails.toEntity())
        }
    }

}

data class ClientUiState (
    val clientDetails: ClientDetails = ClientDetails(),
    val isEntryValid: Boolean = false
)

data class ClientDetails (
    val id: Int = 0,
    val fullName: String = "",
    val document: String = "",
    val address: String = "",
    val phone: String = ""
)

fun ClientDetails.toEntity(): Client = Client (
    id = id,
    fullName = fullName,
    document = document,
    address = address,
    phone = phone
)

fun Client.toClientDetails(): ClientDetails = ClientDetails(
    id = id,
    fullName = fullName,
    document = document,
    address = address,
    phone = phone
)

fun Client.toClientUiState(isEntryValid: Boolean = false): ClientUiState = ClientUiState(
    clientDetails = this.toClientDetails(),
    isEntryValid = isEntryValid
)
package com.munra.gestion_prestamo.ui.client

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.munra.gestion_prestamo.data.client.ClientRepository
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class ClientEditViewModel(
    savedStateHandle: SavedStateHandle,
    private val clientRepository: ClientRepository
) : ViewModel() {

    private val clientId: Int = checkNotNull(savedStateHandle[ClientEditDestination.clientIdArg])

    init {
        viewModelScope.launch {
            clientUiState = clientRepository.getClientStream(clientId)
                .filterNotNull()
                .first()
                .toClientUiState(true)
        }
    }

    /**
     * Holds current item ui state
     */
    var clientUiState by mutableStateOf(ClientUiState())
        private set

    private fun validateInput(uiState: ClientDetails = clientUiState.clientDetails): Boolean {
        return with(uiState) {
            fullName.isNotBlank() && document.isNotBlank() && address.isNotBlank() && phone.isNotBlank()
        }
    }

    fun updateUiState(clientDetails: ClientDetails) {
        clientUiState =
            ClientUiState(clientDetails = clientDetails, isEntryValid = validateInput(clientDetails))
    }

    suspend fun updateClient() {
        if (validateInput(clientUiState.clientDetails)) {
            clientRepository.updateClient(clientUiState.clientDetails.toEntity())
        }
    }

}
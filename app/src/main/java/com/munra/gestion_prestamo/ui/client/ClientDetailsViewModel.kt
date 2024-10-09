package com.munra.gestion_prestamo.ui.client

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.munra.gestion_prestamo.data.client.ClientRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class ClientDetailsViewModel(
    savedStateHandle: SavedStateHandle,
    private val clientRepository: ClientRepository
) : ViewModel() {

    private val clientId: Int = checkNotNull(savedStateHandle[ClientDetailsDestination.clientIdArg])

    val uiState: StateFlow<ClientDetailsUiState> =
        clientRepository.getClientStream(clientId)
            .filterNotNull()
            .map {
                ClientDetailsUiState(clientDetails = it.toClientDetails())
            }.stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
                initialValue = ClientDetailsUiState()
            )

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }

    suspend fun deleteClient() {
        clientRepository.deleteClient(uiState.value.clientDetails.toEntity())
    }

}

data class ClientDetailsUiState(
    val clientDetails: ClientDetails = ClientDetails()
)

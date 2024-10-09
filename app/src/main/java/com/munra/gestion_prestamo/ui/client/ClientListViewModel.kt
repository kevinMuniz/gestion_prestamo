package com.munra.gestion_prestamo.ui.client

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.munra.gestion_prestamo.data.client.Client
import com.munra.gestion_prestamo.data.client.ClientRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class ClientListViewModel(clientRepository: ClientRepository) : ViewModel() {

    val clientListUiState: StateFlow<ClientListUiState> =
        clientRepository.getAllClientStream().map { ClientListUiState(it) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
                initialValue = ClientListUiState()
            )
    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }

}

data class ClientListUiState(val clientList: List<Client> = listOf())
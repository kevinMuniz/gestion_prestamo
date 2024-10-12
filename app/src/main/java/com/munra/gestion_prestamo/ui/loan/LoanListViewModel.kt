package com.munra.gestion_prestamo.ui.loan

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.munra.gestion_prestamo.data.client.Client
import com.munra.gestion_prestamo.data.client.ClientRepository
import com.munra.gestion_prestamo.data.prestamo.Prestamo
import com.munra.gestion_prestamo.data.prestamo.PrestamoRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class LoanListViewModel(prestamoRepository: PrestamoRepository) : ViewModel() {

    val loanListUiState: StateFlow<LoanListUiState> =
        prestamoRepository.getAllPrestamo().map { LoanListUiState(it) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
                initialValue = LoanListUiState()
            )
    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }

}

data class LoanListUiState(val loantList: List<Prestamo> = listOf())
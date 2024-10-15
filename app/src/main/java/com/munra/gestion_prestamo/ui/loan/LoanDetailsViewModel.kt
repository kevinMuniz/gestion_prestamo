package com.munra.gestion_prestamo.ui.loan

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.munra.gestion_prestamo.data.prestamo.PrestamoRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class LoanDetailsViewModel(
    savedStateHandle: SavedStateHandle,
    private val prestamoRepository: PrestamoRepository
) : ViewModel() {

    private val loanId: Int = checkNotNull(savedStateHandle[LoanDetailsDestination.loanIdArg])

    val uiState: StateFlow<PrestamoDetailsUiState> =
        prestamoRepository.getPrestamoStream(loanId)
            .filterNotNull()
            .map {
                PrestamoDetailsUiState(loanDetails = it.toLoanDetails())
            }.stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
                initialValue = PrestamoDetailsUiState()
            )

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }

    suspend fun deleteloan() {
        prestamoRepository.deletePrestamo(uiState.value.loanDetails.toEntity())
    }

}

data class PrestamoDetailsUiState(
    val loanDetails: LoanDetails = LoanDetails()
)

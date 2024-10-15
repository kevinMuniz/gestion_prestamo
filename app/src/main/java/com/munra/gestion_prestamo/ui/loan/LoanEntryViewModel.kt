package com.munra.gestion_prestamo.ui.loan

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.munra.gestion_prestamo.data.client.Client
import com.munra.gestion_prestamo.data.client.ClientRepository
import com.munra.gestion_prestamo.data.prestamo.Prestamo
import com.munra.gestion_prestamo.data.prestamo.PrestamoRepository
import com.munra.gestion_prestamo.data.prestamo.TipoPlazo
import com.munra.gestion_prestamo.ui.client.ClientListUiState
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import androidx.compose.ui.platform.LocalContext

class LoanEntryViewModel(private val prestamoRepository: PrestamoRepository,private val clientRepository: ClientRepository): ViewModel() {

    ///////////////
    val clientListUiState: StateFlow<ClientListUiState> =
        clientRepository.getAllClientStream().map { ClientListUiState(it) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
                initialValue = ClientListUiState()
            )

//    val allPersonsNames: StateFlow<List<String>> = personaDao.getAllPersonNames()
//        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }

    ///////////
    var loanUiDetails by mutableStateOf(LoanUiState())
        private set

    fun updateUiState(loanDetails: LoanDetails) {
        loanUiDetails =
            LoanUiState(loanDetails = loanDetails, isEntryValid = validateInputLoanEntry(loanDetails))
    }


    private fun validateInputLoanEntry(uiState: LoanDetails = loanUiDetails.loanDetails): Boolean {
        return with(uiState) {
                    idPrestamista != 0 &&  // idPrestamista no debe ser 0
                    valor > 0 &&  // valor debe ser mayor que 0
                    plazo > 0 &&  // plazo debe ser mayor que 0
//                    interes > 0 &&  // interes debe ser mayor que 0
//                    diaCobro > 0 && diaCobro <= 31 &&  // diaCobro debe ser un día válido del mes
                    fechaPrestamo.isNotBlank()  // fechaPrestamo no debe estar vacía

        }
    }


    suspend fun saveLoan() {
        prestamoRepository.insertPrestamo(loanUiDetails.loanDetails.toEntity())

        if (validateInputLoanEntry()) {
        }else{

        }
    }

    @Composable
    fun men(){
        val context = LocalContext.current
        Toast.makeText(context, "Campos invalidos", Toast.LENGTH_SHORT).show()

    }

}

data class LoanUiState (
    val loanDetails: LoanDetails = LoanDetails(),
    val isEntryValid: Boolean = false
)

data class LoanDetails (
    val id: Int = 0,
    val idPrestamista: Int = 0,
    val valor: Double = 0.0,
    val tipoPlazo: TipoPlazo = TipoPlazo.SEMANAL,
    val plazo: Int = 0,
    val interes: Double = 0.0,
    val diaCobro: Int = 0,
    val fechaPrestamo: String=""

)

//////

data class ClientListUiState(val clientList: List<Client> = listOf())


///////////////////////////////////

fun LoanDetails.toEntity(): Prestamo = Prestamo (
    id = id,
    idPrestamista = idPrestamista,
    valor = valor,
    tipoPlazo = tipoPlazo,
    plazo = plazo,
    interes = interes,
    diaCobro = diaCobro,
    fechaPrestamo = fechaPrestamo
)

fun Prestamo.toLoanDetails(): LoanDetails = LoanDetails(
    id = id,
    idPrestamista = idPrestamista,
    valor = valor,
    tipoPlazo = tipoPlazo,
    plazo = plazo,
    interes = interes,
    diaCobro = diaCobro,
    fechaPrestamo = fechaPrestamo
)
//
//fun Prestamo.toLoanUiState(isEntryValid: Boolean = false): LoanUiState = LoanUiState(
//    loanDetails = this.toLoanDetails(),
//    isEntryValid = isEntryValid
//)
package com.munra.gestion_prestamo.ui.user

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.munra.gestion_prestamo.data.user.UserRepository
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class UserEditViewModel(
    savedStateHandle: SavedStateHandle,
    private val userRepository: UserRepository
) : ViewModel() {

    private val itemId: Int = checkNotNull(savedStateHandle[UserEditDestination.userIdArg])

    init {
        viewModelScope.launch {
            userUiState = userRepository.getUserStream(itemId)
                .filterNotNull()
                .first()
                .toUserUiState(true)
        }
    }

    /**
     * Holds current item ui state
     */
    var userUiState by mutableStateOf(UserUiState())
        private set

    private fun validateInput(uiState: UserDetails = userUiState.userDetails): Boolean {
        return with(uiState) {
            fullName.isNotBlank() && userName.isNotBlank() && email.isNotBlank()
        }
    }

    fun updateUiState(userDetails: UserDetails) {
        userUiState =
            UserUiState(userDetails = userDetails, isEntryValid = validateInput(userDetails))
    }

    suspend fun updateUser() {
        if (validateInput(userUiState.userDetails)) {
            userRepository.updateUser(userUiState.userDetails.toEntity())
        }
    }

}

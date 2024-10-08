package com.munra.gestion_prestamo.ui.user

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.munra.gestion_prestamo.data.user.UserRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class UserDetailsViewModel(
    savedStateHandle: SavedStateHandle,
    private val userRepository: UserRepository
) : ViewModel() {

    private val userId: Int = checkNotNull(savedStateHandle[UserDetailsDestination.userIdArg])

    val uiState: StateFlow<UserDetailsUiState> =
        userRepository.getUserStream(userId)
            .filterNotNull()
            .map {
                UserDetailsUiState(active = it.status, userDetails = it.toUserDetails())
            }.stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
                initialValue = UserDetailsUiState()
            )

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }

    suspend fun deleteItem() {
        userRepository.deleteUser(uiState.value.userDetails.toEntity())
    }

}

data class UserDetailsUiState(
    val active: Boolean = true,
    val userDetails: UserDetails = UserDetails()
)

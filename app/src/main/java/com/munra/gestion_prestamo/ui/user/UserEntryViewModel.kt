package com.munra.gestion_prestamo.ui.user

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.munra.gestion_prestamo.data.AESEncyption
import com.munra.gestion_prestamo.data.user.User
import com.munra.gestion_prestamo.data.user.UserRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class UserEntryViewModel(private val userRepository: UserRepository): ViewModel () {

    var userUiDetails by mutableStateOf(UserUiState())
        private set

    fun updateUiState(userDetails: UserDetails) {
        userUiDetails =
            UserUiState(userDetails = userDetails, isEntryValid = validateInputUserEntry(userDetails))
    }

    private fun validateInputUserEntry(uiState: UserDetails = userUiDetails.userDetails): Boolean {
        return with(uiState) {
            fullName.isNotBlank() && userName.isNotBlank() && passwordHash.isNotBlank() && email.isNotBlank()
        }
    }

    suspend fun saveUser() {
        if (validateInputUserEntry()) {
            userRepository.insertUser(userUiDetails.userDetails.toEntity())
        }
    }

    val userUiState: StateFlow<UserEntrysUiState> =
        userRepository.getAllUserStream().map { UserEntrysUiState(it) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
                initialValue = UserEntrysUiState()
            )
    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }

}
data class UserEntrysUiState(val itemList: List<User> = listOf())


data class UserUiState(
    val userDetails: UserDetails = UserDetails(),
    val isEntryValid: Boolean = false
)

data class UserDetails(
    val id: Int = 0,
    val fullName: String = "",
    val userName: String = "",
    val passwordHash: String = "",
    val email: String = "",
    val status: Boolean = true
)

fun UserDetails.toEntity(): User = User(
    id = id,
    fullName = fullName,
    userName = userName,
    passwordHash = AESEncyption.encrypt(passwordHash).toString(),
    email = email,
    status = status
)
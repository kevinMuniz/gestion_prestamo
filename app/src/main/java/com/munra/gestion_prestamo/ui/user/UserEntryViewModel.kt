package com.munra.gestion_prestamo.ui.user

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.munra.gestion_prestamo.data.AESEncyption
import com.munra.gestion_prestamo.data.user.User
import com.munra.gestion_prestamo.data.user.UserRepository

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
}

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

fun User.toUserDetails(): UserDetails = UserDetails(
    id = id,
    fullName = fullName,
    userName = userName,
    passwordHash = passwordHash,
    email = email,
    status = status
)

fun User.toUserUiState(isEntryValid: Boolean = false): UserUiState = UserUiState(
    userDetails = this.toUserDetails(),
    isEntryValid = isEntryValid
)
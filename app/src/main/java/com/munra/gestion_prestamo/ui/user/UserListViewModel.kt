package com.munra.gestion_prestamo.ui.user

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.munra.gestion_prestamo.data.user.User
import com.munra.gestion_prestamo.data.user.UserRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class UserListViewModel(userRepository: UserRepository) : ViewModel() {

    val userListUiState: StateFlow<UserListUiState> =
        userRepository.getAllUserStream().map { UserListUiState(it) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
                initialValue = UserListUiState()
            )
    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }

}

data class UserListUiState(val userList: List<User> = listOf())
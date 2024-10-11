package com.munra.gestion_prestamo.ui.login

import androidx.lifecycle.ViewModel
import com.munra.gestion_prestamo.data.user.UserRepository
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first

class LoginViewModel(private val userRepository: UserRepository): ViewModel() {

    suspend fun loginUser(userName: String, password: String): Boolean {
        return userRepository.loginUser(userName, password).filterNotNull().first().status
    }

}


data class Credentials(
    var login: String = "",
    var pwd: String = "",
    var remember: Boolean = false
) {
    fun isNotEmpty(): Boolean {
        return login.isNotEmpty() && pwd.isNotEmpty()
    }
}
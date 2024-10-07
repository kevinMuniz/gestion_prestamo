package com.munra.gestion_prestamo.ui

import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.munra.gestion_prestamo.GestionPrestamoApplication
import com.munra.gestion_prestamo.ui.user.UserEntryViewModel
import com.munra.gestion_prestamo.ui.user.UserListViewModel

object AppViewModelProvider {
    val Factory = viewModelFactory {
        initializer {
            UserListViewModel(commentApplication().container.userRepository)
        }
        initializer {
            UserEntryViewModel(commentApplication().container.userRepository)
        }

    }
}

fun CreationExtras.commentApplication(): GestionPrestamoApplication =
    (this[AndroidViewModelFactory.APPLICATION_KEY] as GestionPrestamoApplication)
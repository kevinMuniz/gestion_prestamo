package com.munra.gestion_prestamo.ui

import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.munra.gestion_prestamo.GestionPrestamoApplication
import com.munra.gestion_prestamo.ui.user.UserDetailsViewModel
import com.munra.gestion_prestamo.ui.user.UserEditViewModel
import com.munra.gestion_prestamo.ui.user.UserEntryViewModel
import com.munra.gestion_prestamo.ui.user.UserListViewModel

object AppViewModelProvider {
    val Factory = viewModelFactory {
        initializer {
            UserListViewModel(gestionPrestamoApplication().container.userRepository)
        }
        initializer {
            UserEntryViewModel(gestionPrestamoApplication().container.userRepository)
        }
        initializer {
            UserDetailsViewModel(
                this.createSavedStateHandle(),
                gestionPrestamoApplication().container.userRepository)
        }
        initializer {
            UserEditViewModel(
                this.createSavedStateHandle(),
                gestionPrestamoApplication().container.userRepository
            )
        }
    }
}

fun CreationExtras.gestionPrestamoApplication(): GestionPrestamoApplication =
    (this[AndroidViewModelFactory.APPLICATION_KEY] as GestionPrestamoApplication)
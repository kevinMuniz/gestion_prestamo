package com.munra.gestion_prestamo.ui

import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.munra.gestion_prestamo.GestionPrestamoApplication
import com.munra.gestion_prestamo.ui.client.ClientDetailsViewModel
import com.munra.gestion_prestamo.ui.client.ClientEditViewModel
import com.munra.gestion_prestamo.ui.client.ClientEntryViewModel
import com.munra.gestion_prestamo.ui.client.ClientListViewModel
import com.munra.gestion_prestamo.ui.loan.LoanDetailsViewModel
import com.munra.gestion_prestamo.ui.loan.LoanEntryViewModel
import com.munra.gestion_prestamo.ui.loan.LoanListViewModel
import com.munra.gestion_prestamo.ui.login.LoginViewModel
import com.munra.gestion_prestamo.ui.user.UserDetailsViewModel
import com.munra.gestion_prestamo.ui.user.UserEditViewModel
import com.munra.gestion_prestamo.ui.user.UserEntryViewModel
import com.munra.gestion_prestamo.ui.user.UserListViewModel

object AppViewModelProvider {
    val Factory = viewModelFactory {
        initializer {
            LoginViewModel(gestionPrestamoApplication().container.userRepository)
        }
        //region User Initializar
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
        //endregion

        //region Client Initializar
        initializer {
            ClientListViewModel(gestionPrestamoApplication().container.clientRepository)
        }
        initializer {
            ClientEntryViewModel(gestionPrestamoApplication().container.clientRepository)
        }
        initializer {
            ClientDetailsViewModel(
                this.createSavedStateHandle(),
                gestionPrestamoApplication().container.clientRepository)
        }
        initializer {
            ClientEditViewModel(
                this.createSavedStateHandle(),
                gestionPrestamoApplication().container.clientRepository
            )
        }
        //endregion

        //region Client Initializar

        initializer {
            LoanListViewModel(gestionPrestamoApplication().container.prestamoRepository)
        }

        initializer {
            LoanEntryViewModel(gestionPrestamoApplication().container.prestamoRepository,gestionPrestamoApplication().container.clientRepository)
        }

        initializer {
            LoanDetailsViewModel(
                this.createSavedStateHandle(),
                gestionPrestamoApplication().container.prestamoRepository)
        }

        //endregion


    }
}

fun CreationExtras.gestionPrestamoApplication(): GestionPrestamoApplication =
    (this[AndroidViewModelFactory.APPLICATION_KEY] as GestionPrestamoApplication)
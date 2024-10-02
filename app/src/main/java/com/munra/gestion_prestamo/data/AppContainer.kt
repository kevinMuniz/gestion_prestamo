package com.munra.gestion_prestamo.data

import android.content.Context
import com.munra.gestion_prestamo.data.prestamista.PrestamistaOfflineRepository
import com.munra.gestion_prestamo.data.prestamista.PrestamistaRepository
import com.munra.gestion_prestamo.data.user.UserOfflineRepository
import com.munra.gestion_prestamo.data.user.UserRepository

interface AppContainer {
    val userRepository: UserRepository
    val prestamistaRepository: PrestamistaRepository
}

class AppDataContainer(private val context: Context) : AppContainer {

    override val userRepository: UserRepository by lazy {
        UserOfflineRepository(GestionPrestamoDatabase.getDatabase(context).userDao())
    }

    override val prestamistaRepository: PrestamistaRepository by lazy {
        PrestamistaOfflineRepository(GestionPrestamoDatabase.getDatabase(context).prestamistaDao())
    }

}

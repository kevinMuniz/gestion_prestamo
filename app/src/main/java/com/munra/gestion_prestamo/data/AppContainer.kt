package com.munra.gestion_prestamo.data

import android.content.Context
import com.munra.gestion_prestamo.data.prestamista.PrestamistaOfflineRepository
import com.munra.gestion_prestamo.data.prestamista.PrestamistaRepository
import com.munra.gestion_prestamo.data.prestamo.PrestamoOfflineRepository
import com.munra.gestion_prestamo.data.prestamo.PrestamoRepository
import com.munra.gestion_prestamo.data.transaccion.TransaccionOfflineRepository
import com.munra.gestion_prestamo.data.transaccion.TransaccionRepository
import com.munra.gestion_prestamo.data.user.UserOfflineRepository
import com.munra.gestion_prestamo.data.user.UserRepository

interface AppContainer {
    val userRepository: UserRepository
    val prestamistaRepository: PrestamistaRepository
    val prestamoRepository: PrestamoRepository
    val transaccionRepository: TransaccionRepository
}

class AppDataContainer(private val context: Context) : AppContainer {

    override val userRepository: UserRepository by lazy {
        UserOfflineRepository(GestionPrestamoDatabase.getDatabase(context).userDao())
    }

    override val prestamistaRepository: PrestamistaRepository by lazy {
        PrestamistaOfflineRepository(GestionPrestamoDatabase.getDatabase(context).prestamistaDao())
    }

    override val prestamoRepository: PrestamoRepository by lazy {
        PrestamoOfflineRepository(GestionPrestamoDatabase.getDatabase(context).prestamoDao())
    }

    override val transaccionRepository: TransaccionRepository by lazy {
        TransaccionOfflineRepository(GestionPrestamoDatabase.getDatabase(context).transaccionDao())
    }

}

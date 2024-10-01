package com.munra.gestion_prestamo

import android.app.Application
import com.munra.gestion_prestamo.data.AppContainer
import com.munra.gestion_prestamo.data.AppDataContainer

class GestionPrestamoApplication : Application() {

    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = AppDataContainer(this)
    }

}
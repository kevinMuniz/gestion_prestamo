package com.munra.gestion_prestamo.data

import android.content.Context
import com.munra.gestion_prestamo.data.user.UserRepository

interface AppContainer {
    val userRepository: UserRepository
}

class AppDataContainer(private val context: Context) : AppContainer {

    override val userRepository: UserRepository by lazy {
        OfflineCommentssRepository(GestionPrestamoDatabase.getDatabase(context).commentDao())
    }

}

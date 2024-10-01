package com.munra.gestion_prestamo.data.user

import kotlinx.coroutines.flow.Flow

interface UserRepository {

    suspend fun insertUser(comentario: User)
    suspend fun getAllUser(): Flow<List<User>>
    fun getAllUserStream(): Flow<List<User>>

}
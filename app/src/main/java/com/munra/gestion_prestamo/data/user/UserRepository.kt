package com.munra.gestion_prestamo.data.user

import kotlinx.coroutines.flow.Flow

interface UserRepository {

    suspend fun insertUser(user: User)
    suspend fun getAllUser(): Flow<List<User>>
    fun getAllUserStream(): Flow<List<User>>
    fun getUserStream(id: Int): Flow<User?>
    suspend fun deleteUser(user: User)
    suspend fun updateUser(user: User)

}
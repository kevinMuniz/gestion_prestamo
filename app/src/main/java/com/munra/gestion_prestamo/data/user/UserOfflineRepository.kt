package com.munra.gestion_prestamo.data.user

import kotlinx.coroutines.flow.Flow

class UserOfflineRepository (private val userDao: UserDao) : UserRepository {

    override suspend fun getAllUser(): Flow<List<User>> = userDao.getAllUser()
    override fun getAllUserStream(): Flow<List<User>> = userDao.getAllUser()
    override suspend fun insertUser(user: User) = userDao.insertUser(user)

}

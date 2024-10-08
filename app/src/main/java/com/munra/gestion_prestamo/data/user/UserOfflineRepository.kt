package com.munra.gestion_prestamo.data.user

import kotlinx.coroutines.flow.Flow

class UserOfflineRepository (private val userDao: UserDao) : UserRepository {

    override suspend fun getAllUser(): Flow<List<User>> = userDao.getAllUser()
    override fun getAllUserStream(): Flow<List<User>> = userDao.getAllUser()
    override fun getUserStream(id: Int): Flow<User?> = userDao.getUser(id)
    override suspend fun deleteUser(user: User) = userDao.delete(user)
    override suspend fun updateUser(user: User) = userDao.update(user)
    override suspend fun insertUser(user: User) = userDao.insertUser(user)

}

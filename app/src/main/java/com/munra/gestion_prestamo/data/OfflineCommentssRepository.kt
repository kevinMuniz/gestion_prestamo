package com.munra.gestion_prestamo.data

import com.munra.gestion_prestamo.data.user.User
import com.munra.gestion_prestamo.data.user.UserDao
import com.munra.gestion_prestamo.data.user.UserRepository
import kotlinx.coroutines.flow.Flow

class OfflineCommentssRepository (private val userDao: UserDao) : UserRepository {

    override suspend fun getAllUser(): Flow<List<User>> = userDao.getAllUser()
    override fun getAllUserStream(): Flow<List<User>> = userDao.getAllUser()
    override suspend fun insertUser(item: User) = userDao.insertUser(item)

}

package com.munra.gestion_prestamo.data.user

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertUser(user: User)

    @Update
    suspend fun update(user: User)

    @Delete
    suspend fun delete(user: User)

    @Query("SELECT * FROM USER")
    fun getAllUser(): Flow<List<User>>

    @Query("SELECT * FROM USER WHERE id =:id")
    fun getUser(id: Int):Flow<User>

    @Query("SELECT * FROM USER WHERE userName =:userName AND passwordHash =:password")
    fun loginUser(userName: String, password: String):Flow<User>

}
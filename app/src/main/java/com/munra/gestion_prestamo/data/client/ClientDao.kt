package com.munra.gestion_prestamo.data.client

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface ClientDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertClient(client: Client)

    @Update
    suspend fun update(client: Client)

    @Delete
    suspend fun delete(client: Client)

    @Query("SELECT * FROM CLIENT")
    fun getAllClient(): Flow<List<Client>>

    @Query("SELECT * FROM CLIENT WHERE id =:id")
    fun getClient(id: Int):Flow<Client>

}
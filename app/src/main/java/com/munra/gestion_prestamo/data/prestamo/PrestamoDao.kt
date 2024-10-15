package com.munra.gestion_prestamo.data.prestamo

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.munra.gestion_prestamo.data.client.Client
import kotlinx.coroutines.flow.Flow

@Dao
interface PrestamoDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertPrestamo(prestamo: Prestamo)

    @Query("SELECT * FROM PRESTAMO")
    fun getAllPrestamo(): Flow<List<Prestamo>>

    @Update
    suspend fun update(prestamo: Prestamo)

    @Delete
    suspend fun delete(prestamo: Prestamo)

    @Query("SELECT * FROM PRESTAMO WHERE id =:id")
    fun getPrestamo(id: Int):Flow<Prestamo>

}
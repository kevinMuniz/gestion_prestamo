package com.munra.gestion_prestamo.data.prestamo

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface PrestamoDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertPrestamo(prestamo: Prestamo)

    @Query("SELECT * FROM PRESTAMO")
    fun getAllPrestamo(): Flow<List<Prestamo>>

}
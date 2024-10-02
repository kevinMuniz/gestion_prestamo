package com.munra.gestion_prestamo.data.prestamista

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.munra.gestion_prestamo.data.prestamista.Prestamista
import kotlinx.coroutines.flow.Flow

@Dao
interface PrestamistaDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertPrestamista(user: Prestamista)

    @Query("SELECT * FROM PRESTAMISTA")
    fun getAllPrestamista(): Flow<List<Prestamista>>

}
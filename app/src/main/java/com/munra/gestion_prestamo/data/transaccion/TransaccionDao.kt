package com.munra.gestion_prestamo.data.transaccion

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface TransaccionDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertTransaccion(transaccion: Transaccion)

    @Query("SELECT * FROM TRANSACCION")
    fun getAllTransaccion(): Flow<List<Transaccion>>

    @Query("SELECT * FROM TRANSACCION WHERE  idPrestamo =:idPrestamo ORDER BY fecha, hora ASC")
    fun getAllTransaccionByPrestamo(idPrestamo: Int): Flow<List<Transaccion>>

    @Query("SELECT * FROM TRANSACCION WHERE fecha =:fecha")
    fun getAllTransaccionByDay(fecha: String): Flow<List<Transaccion>>

}
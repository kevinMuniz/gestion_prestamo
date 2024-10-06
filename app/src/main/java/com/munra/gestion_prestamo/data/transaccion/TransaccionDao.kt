package com.munra.gestion_prestamo.data.transaccion

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate
import java.time.LocalDateTime

@Dao
interface TransaccionDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertTransaccion(transaccion: Transaccion)

    @Query("SELECT * FROM TRANSACCION")
    fun getAllTransaccion(): Flow<List<Transaccion>>

    @Query("SELECT t.* FROM TRANSACCION t " +
            "INNER JOIN PRESTAMO p ON t.idPrestamo = p.id " +
            "WHERE  p.id = :idPrestamo " +
            "ORDER BY t.fechaHora ASC")
    fun getAllTransaccionByPrestamo(idPrestamo: Int)

    @Query("SELECT * FROM TRANSACCION " +
            "WHERE fechaHora BETWEEN :fechaInicio AND :fechaFin")
    fun getAllTransaccionByDay(fechaInicio: LocalDateTime, fechaFin: LocalDateTime)
}
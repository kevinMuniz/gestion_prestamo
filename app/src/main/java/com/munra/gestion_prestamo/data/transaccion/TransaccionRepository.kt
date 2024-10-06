package com.munra.gestion_prestamo.data.transaccion

import kotlinx.coroutines.flow.Flow
import java.time.LocalDateTime

interface TransaccionRepository {

    suspend fun insertTransaccion(transaccion: Transaccion)
    suspend fun getAllTransaccion():Flow<List<Transaccion>>
    suspend fun getAllTransaccionByPrestamo(idPrestamo: Int)
    suspend fun getAllTransaccionByDay(fechaInicio: LocalDateTime, fechaFin: LocalDateTime)

}
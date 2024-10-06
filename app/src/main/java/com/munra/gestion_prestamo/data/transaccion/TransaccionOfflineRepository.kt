package com.munra.gestion_prestamo.data.transaccion

import kotlinx.coroutines.flow.Flow
import java.time.LocalDateTime

class TransaccionOfflineRepository(private val transaccionDao: TransaccionDao): TransaccionRepository {

    override suspend fun insertTransaccion(transaccion: Transaccion) = transaccionDao.insertTransaccion(transaccion)

    override suspend fun getAllTransaccion(): Flow<List<Transaccion>> = transaccionDao.getAllTransaccion()

    override suspend fun getAllTransaccionByPrestamo(idPrestamo: Int) = transaccionDao.getAllTransaccionByPrestamo(idPrestamo)

    override suspend fun getAllTransaccionByDay(
        fechaInicio: LocalDateTime,
        fechaFin: LocalDateTime
    ) = transaccionDao.getAllTransaccionByDay(fechaInicio, fechaFin)

}
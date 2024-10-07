package com.munra.gestion_prestamo.data.transaccion

import kotlinx.coroutines.flow.Flow

interface TransaccionRepository {

    suspend fun insertTransaccion(transaccion: Transaccion)
    suspend fun getAllTransaccion():Flow<List<Transaccion>>
    suspend fun getAllTransaccionByPrestamo(idPrestamo: Int): Flow<List<Transaccion>>
    suspend fun getAllTransaccionByDay(fecha: String): Flow<List<Transaccion>>

}
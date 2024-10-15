package com.munra.gestion_prestamo.data.prestamo

import kotlinx.coroutines.flow.Flow

interface PrestamoRepository {

    suspend fun insertPrestamo(prestamo: Prestamo)
    suspend fun getAllPrestamo(): Flow<List<Prestamo>>
    fun getAllPrestamoStream(): Flow<List<Prestamo>>
    fun getPrestamoStream(id: Int): Flow<Prestamo?>
    suspend fun deletePrestamo(prestamo: Prestamo)
    suspend fun updatePrestamo(prestamo: Prestamo)

}
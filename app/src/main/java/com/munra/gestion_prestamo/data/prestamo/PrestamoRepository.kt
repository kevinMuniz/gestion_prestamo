package com.munra.gestion_prestamo.data.prestamo

import kotlinx.coroutines.flow.Flow

interface PrestamoRepository {

    suspend fun insertPrestamo(prestamo: Prestamo)
    suspend fun getAllPrestamo(): Flow<List<Prestamo>>
    fun getAllPrestamoStream(): Flow<List<Prestamo>>

}
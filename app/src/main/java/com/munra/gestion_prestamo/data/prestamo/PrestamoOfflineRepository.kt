package com.munra.gestion_prestamo.data.prestamo

import kotlinx.coroutines.flow.Flow

class PrestamoOfflineRepository (private val prestamoDao: PrestamoDao): PrestamoRepository {

    override suspend fun insertPrestamo(prestamo: Prestamo) = prestamoDao.insertPrestamo(prestamo);

    override suspend fun getAllPrestamo(): Flow<List<Prestamo>> = prestamoDao.getAllPrestamo();

    override fun getAllPrestamoStream(): Flow<List<Prestamo>> = prestamoDao.getAllPrestamo();
    
}
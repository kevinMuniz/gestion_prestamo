package com.munra.gestion_prestamo.data.prestamo

import com.munra.gestion_prestamo.data.client.Client
import kotlinx.coroutines.flow.Flow

class PrestamoOfflineRepository (private val prestamoDao: PrestamoDao): PrestamoRepository {

    override suspend fun insertPrestamo(prestamo: Prestamo) = prestamoDao.insertPrestamo(prestamo);

    override suspend fun getAllPrestamo(): Flow<List<Prestamo>> = prestamoDao.getAllPrestamo();

    override fun getAllPrestamoStream(): Flow<List<Prestamo>> = prestamoDao.getAllPrestamo();

    override fun getPrestamoStream(id: Int): Flow<Prestamo?> = prestamoDao.getPrestamo(id)

    override suspend fun deletePrestamo(prestamo: Prestamo) = prestamoDao.delete(prestamo)

    override suspend fun updatePrestamo(prestamo: Prestamo) = prestamoDao.update(prestamo)
}
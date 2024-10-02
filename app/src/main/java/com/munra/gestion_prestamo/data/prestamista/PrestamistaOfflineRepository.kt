package com.munra.gestion_prestamo.data.prestamista


import kotlinx.coroutines.flow.Flow

class PrestamistaOfflineRepository (private val prestamistaDao: PrestamistaDao) : PrestamistaRepository {

    override suspend fun insertLender(prestamista: Prestamista) = prestamistaDao.insertPrestamista(prestamista)
    override suspend fun getAllLender(): Flow<List<Prestamista>> = prestamistaDao.getAllPrestamista()
    override fun getAllLenderStream(): Flow<List<Prestamista>> = prestamistaDao.getAllPrestamista()

}
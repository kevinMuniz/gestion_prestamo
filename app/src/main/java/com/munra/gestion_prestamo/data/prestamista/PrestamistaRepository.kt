package com.munra.gestion_prestamo.data.prestamista

import kotlinx.coroutines.flow.Flow

interface PrestamistaRepository {

    suspend fun insertLender(prestamista: Prestamista)
    suspend fun getAllLender(): Flow<List<Prestamista>>
    fun getAllLenderStream(): Flow<List<Prestamista>>

}
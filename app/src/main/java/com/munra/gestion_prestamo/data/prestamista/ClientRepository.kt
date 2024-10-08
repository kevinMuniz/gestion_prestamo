package com.munra.gestion_prestamo.data.prestamista

import kotlinx.coroutines.flow.Flow

interface ClientRepository {

    suspend fun insertClient(client: Client)
    suspend fun getAllClient(): Flow<List<Client>>
    fun getAllClientStream(): Flow<List<Client>>
    fun getClientStream(id: Int): Flow<Client?>
    suspend fun deleteClient(client: Client)
    suspend fun updateClient(client: Client)

}
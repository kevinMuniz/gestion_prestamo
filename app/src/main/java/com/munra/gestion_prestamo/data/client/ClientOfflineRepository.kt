package com.munra.gestion_prestamo.data.client


import kotlinx.coroutines.flow.Flow

class ClientOfflineRepository (private val clientDao: ClientDao) : ClientRepository {

    override suspend fun insertClient(client: Client) = clientDao.insertClient(client)

    override suspend fun getAllClient(): Flow<List<Client>> = clientDao.getAllClient()

    override fun getAllClientStream(): Flow<List<Client>> = clientDao.getAllClient()

    override fun getClientStream(id: Int): Flow<Client?> = clientDao.getClient(id)

    override suspend fun deleteClient(client: Client) = clientDao.delete(client)

    override suspend fun updateClient(client: Client) = clientDao.update(client)

}
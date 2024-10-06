package com.munra.gestion_prestamo.data.transaccion

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime

@Entity(tableName = "TRANSACCION")
data class Transaccion(

    @PrimaryKey(autoGenerate = true)
    val Id: Int = 0,
    val idPrestamo: Int,
    val tipoTransaccion: TipoTransaccion,
    val valor: Double,
    val fechaHora: LocalDateTime,
    val idAgente : Int

)

package com.munra.gestion_prestamo.data.prestamo

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "PRESTAMO")
data class Prestamo(

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val idPrestamista: Int,
    val valor: Double,
    val tipoPlazo: TipoPlazo,
    val plazo: Int,
    val interes: Double,
    val diaCobro: Int,
    val fechaPrestamo: String

)

package com.munra.gestion_prestamo.data.prestamista

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "PRESTAMISTA")
data class Prestamista(

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val fullName: String,
    val document: String,
    val address: String,
    val phone: String

)

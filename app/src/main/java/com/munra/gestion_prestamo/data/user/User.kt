package com.munra.gestion_prestamo.data.user

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "USER")
data class User(

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val fullName : String,
    val userName : String,
    val password_hash : String,
    val email : String,

)

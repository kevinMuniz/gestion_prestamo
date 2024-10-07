package com.munra.gestion_prestamo.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.munra.gestion_prestamo.data.prestamista.Prestamista
import com.munra.gestion_prestamo.data.prestamista.PrestamistaDao
import com.munra.gestion_prestamo.data.prestamo.Prestamo
import com.munra.gestion_prestamo.data.prestamo.PrestamoDao
import com.munra.gestion_prestamo.data.transaccion.Transaccion
import com.munra.gestion_prestamo.data.transaccion.TransaccionDao
import com.munra.gestion_prestamo.data.user.User
import com.munra.gestion_prestamo.data.user.UserDao

@Database(entities = [User::class, Prestamista::class, Prestamo::class, Transaccion::class], version = 1, exportSchema = false)
abstract class GestionPrestamoDatabase : RoomDatabase(){

    abstract fun userDao(): UserDao
    abstract fun prestamistaDao(): PrestamistaDao
    abstract fun prestamoDao(): PrestamoDao
    abstract fun transaccionDao(): TransaccionDao

    companion object {

        @Volatile
        private var Instance: GestionPrestamoDatabase? = null

        fun getDatabase(context: Context): GestionPrestamoDatabase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, GestionPrestamoDatabase::class.java, "gestion_prestamo_database")
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { Instance = it }
            }
        }
    }

}
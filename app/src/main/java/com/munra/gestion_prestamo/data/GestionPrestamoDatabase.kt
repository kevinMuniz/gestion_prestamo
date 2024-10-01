package com.munra.gestion_prestamo.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.munra.gestion_prestamo.data.user.User
import com.munra.gestion_prestamo.data.user.UserDao

@Database(entities = [User::class], version = 1, exportSchema = false)
abstract class GestionPrestamoDatabase : RoomDatabase(){

    abstract fun commentDao(): UserDao

    companion object {

        @Volatile
        private var Instance: GestionPrestamoDatabase? = null

        fun getDatabase(context: Context): GestionPrestamoDatabase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, GestionPrestamoDatabase::class.java, "comment_database")
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { Instance = it }
            }
        }
    }

}
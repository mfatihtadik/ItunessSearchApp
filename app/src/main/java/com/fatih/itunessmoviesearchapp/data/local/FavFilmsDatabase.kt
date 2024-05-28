package com.fatih.itunessmoviesearchapp.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = arrayOf(FavFilms::class), version = 1)
abstract class FavFilmsDatabase : RoomDatabase() {
    abstract fun favDao(): FavDao
    companion object{
        @Volatile private var instance : FavFilmsDatabase? = null
        private val lock = Any()
        operator fun invoke(context : Context) = instance ?: synchronized(lock){
            instance ?: makeDatabase(context).also {
                instance = it
            }
        }
        private fun makeDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext,FavFilmsDatabase::class.java,"FavFilms"
        ).build()
    }
}
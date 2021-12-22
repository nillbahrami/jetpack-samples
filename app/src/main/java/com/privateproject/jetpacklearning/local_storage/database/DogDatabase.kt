package com.privateproject.jetpacklearning.local_storage.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.privateproject.jetpacklearning.model.DogBread

@Database(entities = arrayOf(DogBread::class), version = 1)
abstract class DogDatabase: RoomDatabase() {
    abstract fun dogDao(): DogDao

    companion object{
        @Volatile private var instance: DogDatabase? = null
        private val LOCK = Any() // if multiple threads wants to access, only one is allowed

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: buildDatabase(context).also {
                instance = it
            }
        }
        private fun buildDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            DogDatabase::class.java,
            "dogdatabase"
        ).build()
    }
}
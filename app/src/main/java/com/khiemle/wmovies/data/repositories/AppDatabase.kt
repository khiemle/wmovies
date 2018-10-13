package com.khiemle.wmovies.data.repositories

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.khiemle.wmovies.data.models.Movie

@Database(entities = [Movie::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun movieDAO() : MovieDAO
    companion object {
        private const val DATABASE_NAME = "WizeMovie"
        fun buildDatabase(context: Context): AppDatabase? {
            return Room.databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME).build()
        }
    }
}
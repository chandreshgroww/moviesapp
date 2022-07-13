package com.example.moviesapp.database

import android.content.Context
import androidx.room.*
import com.example.moviesapp.models.Movie

@Database(entities = [Movie::class], version = 1, exportSchema = false)
@TypeConverters(Convertors::class)
abstract class LocalDatabase : RoomDatabase() {

    abstract fun getDatabaseDao(): DatabaseDao
}

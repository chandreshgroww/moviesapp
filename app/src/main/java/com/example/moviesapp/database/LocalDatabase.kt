package com.example.moviesapp.database

import androidx.room.*
import com.example.moviesapp.models.Movie
import com.example.moviesapp.models.SortFilter

@Database(entities = [Movie::class, SortFilter::class], version = 1, exportSchema = false)
@TypeConverters(Convertors::class)
abstract class LocalDatabase : RoomDatabase() {

    abstract fun getDatabaseDao(): DatabaseDao
}

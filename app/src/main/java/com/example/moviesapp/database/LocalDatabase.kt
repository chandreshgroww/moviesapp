package com.example.moviesapp.database

import android.content.Context
import androidx.room.*
import com.example.moviesapp.models.Movie

@Database(entities = [Movie::class], version = 1, exportSchema = false)
@TypeConverters(Convertors::class)
abstract class LocalDatabase : RoomDatabase() {

    abstract val databaseDao: DatabaseDao

    companion object {

        @Volatile
        private var INSTANCE: LocalDatabase? = null

        fun getInstance(context: Context): LocalDatabase {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        LocalDatabase::class.java,
                        "local_database"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}

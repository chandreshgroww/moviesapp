package com.example.moviesapp.di

import android.content.Context
import androidx.room.Room
import com.example.moviesapp.database.LocalDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule {

    @Singleton
    @Provides
    fun provideMovieDB(context: Context): LocalDatabase {
        return Room.databaseBuilder(
            context,
            LocalDatabase::class.java,
            "local_database").build()
    }
}
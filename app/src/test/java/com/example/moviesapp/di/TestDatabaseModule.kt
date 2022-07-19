package com.example.moviesapp.di

import android.content.Context
import androidx.room.Room
import com.example.moviesapp.database.LocalDatabase
import dagger.Module
import dagger.Provides
import io.mockk.mockk
import javax.inject.Singleton

@Module
class TestDatabaseModule {

    @Singleton
    @Provides
    fun provideMovieDB(context: Context): LocalDatabase {
        return mockk()
    }
}
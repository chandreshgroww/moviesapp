package com.example.moviesapp.di

import com.example.moviesapp.database.ILocalDataSource
import com.example.moviesapp.database.LocalDataSource
import com.example.moviesapp.network.IRemoteDataSource
import com.example.moviesapp.network.RemoteDataSource
import dagger.Binds
import dagger.Module

@Module
abstract class InterfaceModule {
    @Binds
    abstract fun provideRemoteDataSource(remoteDataSource: RemoteDataSource):
            IRemoteDataSource

    @Binds
    abstract fun provideLocalDataSource(localDataSource: LocalDataSource):
            ILocalDataSource
}
package com.example.moviesapp.database

import androidx.lifecycle.LiveData
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.moviesapp.models.Movie
import com.example.moviesapp.models.SortFilter
import javax.inject.Inject

class LocalDataSource @Inject constructor(localDatabase: LocalDatabase) : ILocalDataSource {

    private val databaseDao = localDatabase.getDatabaseDao()

    override suspend fun addMovieList(movieList: List<Movie>) =
        databaseDao.addMovieList(movieList)

    override fun getPopularMovieList() = databaseDao.getPopularMovieList()

    override fun getVoteCountMovieList() = databaseDao.getVoteCountMovieList()

    override suspend fun addGenreList(genreList: List<SortFilter>) =
        databaseDao.addGenreList(genreList)

    override fun getAllGenreList() = databaseDao.getAllGenreList()
}


interface ILocalDataSource {
    suspend fun addMovieList(movieList: List<Movie>)
    fun getPopularMovieList(): LiveData<List<Movie>>
    fun getVoteCountMovieList(): LiveData<List<Movie>>

    suspend fun addGenreList(genreList: List<SortFilter>)
    fun getAllGenreList(): LiveData<List<SortFilter>>
}
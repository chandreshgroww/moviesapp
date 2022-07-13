package com.example.moviesapp.repository

import androidx.lifecycle.LiveData
import androidx.paging.*
import com.example.moviesapp.database.LocalDatabase
import com.example.moviesapp.models.Movie
import com.example.moviesapp.network.RemoteDataSource
import com.example.moviesapp.paging.MoviePagingSource
import com.example.moviesapp.util.Result
import com.example.moviesapp.util.resultLiveData
import javax.inject.Inject

private const val TAG = "MovieRepository"

class MovieRepository @Inject constructor(private val remoteDataSource: RemoteDataSource, private val localDatabase: LocalDatabase) {

    val popularMovieList: LiveData<Result<List<Movie>>> = resultLiveData(
        databaseQuery = { localDatabase.getDatabaseDao().getPopularMovieList() },
        networkCall = { remoteDataSource.fetchPopularList() },
        saveCallResult = { localDatabase.getDatabaseDao().addMovieList(it.results) })

    fun getVoteCountMovies(): LiveData<PagingData<Movie>> = Pager(
        config = PagingConfig(pageSize = 2, enablePlaceholders = true, maxSize = 100),
        pagingSourceFactory = { MoviePagingSource(remoteDataSource) }
    ).liveData

}
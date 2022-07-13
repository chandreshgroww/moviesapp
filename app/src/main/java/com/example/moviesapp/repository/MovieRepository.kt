package com.example.moviesapp.repository

import androidx.lifecycle.LiveData
import androidx.paging.*
import com.example.moviesapp.database.LocalDatabase
import com.example.moviesapp.models.Movie
import com.example.moviesapp.network.RemoteDataSource
import com.example.moviesapp.paging.MoviePagingSource
import com.example.moviesapp.util.Result
import com.example.moviesapp.util.SortBy
import com.example.moviesapp.util.resultLiveData
import javax.inject.Inject

private const val TAG = "MovieRepository"

class MovieRepository @Inject constructor(private val remoteDataSource: RemoteDataSource, private val localDatabase: LocalDatabase) {

    val popularMovieList: LiveData<Result<List<Movie>>> = resultLiveData(
        databaseQuery = { localDatabase.getDatabaseDao().getPopularMovieList() },
        networkCall = { remoteDataSource.fetchPopularList(SortBy.PopularityDesc) },
        saveCallResult = { localDatabase.getDatabaseDao().addMovieList(it.results) }
    )

    val viewCountMovieList = resultLiveData(
        databaseQuery = { localDatabase.getDatabaseDao().getVoteCountMovieList() },
        networkCall = { remoteDataSource.fetchPopularList(SortBy.VoteCount) },
        saveCallResult = { localDatabase.getDatabaseDao().addMovieList(it.results) }
    )

    fun getVoteCountMovies(): LiveData<PagingData<Movie>> = Pager(
        config = PagingConfig(pageSize = 20, enablePlaceholders = true, maxSize = 200),
        pagingSourceFactory = { MoviePagingSource(remoteDataSource) }
    ).liveData

}
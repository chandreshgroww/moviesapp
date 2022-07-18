package com.example.moviesapp.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import androidx.paging.*
import com.example.moviesapp.database.LocalDatabase
import com.example.moviesapp.models.Movie
import com.example.moviesapp.models.MovieDetail
import com.example.moviesapp.models.SortFilter
import com.example.moviesapp.network.RemoteDataSource
import com.example.moviesapp.paging.MoviePagingSource
import com.example.moviesapp.util.Result
import com.example.moviesapp.util.SortBy
import javax.inject.Inject


class MovieRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDatabase: LocalDatabase
) {

    val popularMovieList: LiveData<Result<List<Movie>>> = resultLiveData(
        databaseQuery = { localDatabase.getDatabaseDao().getPopularMovieList() },
        networkCall = { remoteDataSource.fetchMoviesList(SortBy.PopularityDesc) },
        saveCallResult = { localDatabase.getDatabaseDao().addMovieList(it.results) }
    )

    val viewCountMovieList = resultLiveData(
        databaseQuery = { localDatabase.getDatabaseDao().getVoteCountMovieList() },
        networkCall = { remoteDataSource.fetchMoviesList(SortBy.VoteCountDesc) },
        saveCallResult = { localDatabase.getDatabaseDao().addMovieList(it.results) }
    )

    val genreMovieList = resultLiveData(
        databaseQuery = { localDatabase.getDatabaseDao().getAllGenreList() },
        networkCall = { remoteDataSource.fetchAllGenreList() },
        saveCallResult = { localDatabase.getDatabaseDao().addGenreList(it.genres) }
    )

    fun getVoteCountMovies(sortBy: SortBy, withGenre: String): LiveData<PagingData<Movie>> = Pager(
        config = PagingConfig(pageSize = 10, maxSize = 200),
        pagingSourceFactory = { MoviePagingSource(remoteDataSource, sortBy, withGenre) }
    ).liveData

    suspend fun movieDetail(movieId: Int): Result<MovieDetail> =
        remoteDataSource.fetchMovieDetail(movieId)

}
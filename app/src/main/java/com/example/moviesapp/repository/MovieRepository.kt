package com.example.moviesapp.repository

import androidx.lifecycle.LiveData
import androidx.paging.*
import com.example.moviesapp.database.ILocalDataSource
import com.example.moviesapp.models.Movie
import com.example.moviesapp.models.MovieDetail
import com.example.moviesapp.network.IRemoteDataSource
import com.example.moviesapp.paging.MoviePagingSource
import com.example.moviesapp.util.Result
import com.example.moviesapp.util.SortBy
import javax.inject.Inject


class MovieRepository @Inject constructor(
    private val remoteDataSource: IRemoteDataSource,
    private val localDataSource: ILocalDataSource
) {

    val popularMovieList: LiveData<Result<List<Movie>>> = resultLiveData(
        databaseQuery = { localDataSource.getPopularMovieList() },
        networkCall = { remoteDataSource.fetchMoviesList(SortBy.PopularityDesc) },
        saveCallResult = { localDataSource.addMovieList(it.results) }
    )

    val viewCountMovieList = resultLiveData(
        databaseQuery = { localDataSource.getVoteCountMovieList() },
        networkCall = { remoteDataSource.fetchMoviesList(SortBy.VoteCountDesc) },
        saveCallResult = { localDataSource.addMovieList(it.results) }
    )

    val genreMovieList = resultLiveData(
        databaseQuery = { localDataSource.getAllGenreList() },
        networkCall = { remoteDataSource.fetchAllGenreList() },
        saveCallResult = { localDataSource.addGenreList(it.genres) }
    )

    fun getVoteCountMovies(sortBy: SortBy, withGenre: String): LiveData<PagingData<Movie>> = Pager(
        config = PagingConfig(pageSize = 10, maxSize = 200),
        pagingSourceFactory = { MoviePagingSource(remoteDataSource, sortBy, withGenre) }
    ).liveData

    suspend fun movieDetail(movieId: Int): Result<MovieDetail> =
        remoteDataSource.fetchMovieDetail(movieId)

}
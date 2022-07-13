package com.example.moviesapp.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.example.moviesapp.database.DatabaseDao
import com.example.moviesapp.models.Movie
import com.example.moviesapp.models.MovieDetail
import com.example.moviesapp.network.ApiService
import com.example.moviesapp.util.NetworkResult
import com.example.moviesapp.util.SortBy

private const val TAG = "MovieRepository"

class MovieRepository(private val apiService: ApiService, private val databaseDao: DatabaseDao) {

    val popularMovieList = databaseDao.getPopularMovieList()

    val voteCountMovieList = databaseDao.getVoteCountMovieList()

    private val _movieDetailById = MutableLiveData<NetworkResult>()
    val movieDetailById: LiveData<NetworkResult>
        get() = _movieDetailById

    suspend fun fetchLatestMovies(sortBy: SortBy): NetworkResult {
        var networkStatus: NetworkResult = NetworkResult.Loading
        try {
            val resultBody = apiService.getMovieListAsync(sortBy.notation).body()
            val moviesList = resultBody?.results
            moviesList?.let {
                databaseDao.addMovieList(it)
                networkStatus = NetworkResult.Success(moviesList)
            }
        } catch (e: Exception) {
            networkStatus = NetworkResult.Error(e.message)
        }
        return networkStatus
    }

    suspend fun getMovieDetails(movieId: Long): NetworkResult = try {
        val resultBody = apiService.getMovieDetails(movieId).body()
        NetworkResult.Success(resultBody)
    } catch (e: Exception) {
        NetworkResult.Error(e.message)
    }

}
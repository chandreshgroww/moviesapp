package com.example.moviesapp.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.example.moviesapp.database.DatabaseDao
import com.example.moviesapp.database.LocalDatabase
import com.example.moviesapp.models.Movie
import com.example.moviesapp.models.MovieDetail
import com.example.moviesapp.network.ApiService
import com.example.moviesapp.util.NetworkResult
import com.example.moviesapp.util.SortBy
import javax.inject.Inject

private const val TAG = "MovieRepository"

class MovieRepository @Inject constructor(private val apiService: ApiService, private val localDatabase: LocalDatabase) {

    val popularMovieList = localDatabase.getDatabaseDao().getPopularMovieList()

    val voteCountMovieList = localDatabase.getDatabaseDao().getVoteCountMovieList()

    suspend fun fetchLatestMovies(sortBy: SortBy): NetworkResult = try {
        val moviesList = apiService.getMovieListAsync(sortBy.notation).body()?.results
        addMoviesToLocalDB(moviesList)
        NetworkResult.Success(moviesList)
    } catch (e: Exception) {
        NetworkResult.Error(e.message)
    }

    suspend fun getMovieDetails(movieId: Long): NetworkResult = try {
        val resultBody = apiService.getMovieDetails(movieId).body()
        NetworkResult.Success(resultBody)
    } catch (e: Exception) {
        NetworkResult.Error(e.message)
    }

    private suspend fun addMoviesToLocalDB(moviesList: List<Movie>?) {
        moviesList?.let {
            localDatabase.getDatabaseDao().addMovieList(it)
        }
    }

}
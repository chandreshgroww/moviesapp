package com.example.moviesapp.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.moviesapp.models.Movie
import com.example.moviesapp.util.SortBy

@Dao
interface DatabaseDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addMovieList(movieList: List<Movie>?)

    @Query("SELECT * FROM all_movies ORDER BY popularity DESC LIMIT 20")
    fun getPopularMovieList(): LiveData<List<Movie>>

    @Query("SELECT * FROM all_movies ORDER BY vote_count DESC LIMIT 20")
    fun getVoteCountMovieList(): LiveData<List<Movie>>

}
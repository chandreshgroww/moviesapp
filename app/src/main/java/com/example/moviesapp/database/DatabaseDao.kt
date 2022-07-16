package com.example.moviesapp.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.moviesapp.models.Movie
import com.example.moviesapp.models.SortFilter
import com.example.moviesapp.util.SortBy
import retrofit2.http.GET

@Dao
interface DatabaseDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addMovieList(movieList: List<Movie>)

    @Query("SELECT * FROM all_movies ORDER BY popularity DESC LIMIT 20")
    fun getPopularMovieList(): LiveData<List<Movie>>

    @Query("SELECT * FROM all_movies ORDER BY vote_count DESC LIMIT 20")
    fun getVoteCountMovieList(): LiveData<List<Movie>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addGenreList(genreList: List<SortFilter>)

    @Query("SELECT * FROM all_genre")
    fun getAllGenreList(): LiveData<List<SortFilter>>

}
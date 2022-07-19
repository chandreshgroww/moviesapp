package com.example.moviesapp.database

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.moviesapp.data.TestData
import com.example.moviesapp.models.Movie
import com.example.moviesapp.models.SortFilter
import org.junit.Assert.*

class LocalDataSourceTest: ILocalDataSource {
    override suspend fun addMovieList(movieList: List<Movie>) {
        TODO("Not yet implemented")
    }

    override fun getPopularMovieList(): LiveData<List<Movie>> {
        TODO("Not yet implemented")
    }

    override fun getVoteCountMovieList(): LiveData<List<Movie>> {
        TODO("Not yet implemented")
    }

    override suspend fun addGenreList(genreList: List<SortFilter>) {
        println("addGenreList: $genreList added to db")
    }

    override fun getAllGenreList(): LiveData<List<SortFilter>> {
        return MutableLiveData(TestData.sortFilterList)
    }

}
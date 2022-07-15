package com.example.moviesapp.ui.explore

import android.util.Log
import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.moviesapp.models.Genre
import com.example.moviesapp.models.Movie
import com.example.moviesapp.models.QueryParams
import com.example.moviesapp.repository.MovieRepository
import com.example.moviesapp.ui.details.DetailsViewModel
import com.example.moviesapp.util.SortBy
import java.lang.IllegalArgumentException
import javax.inject.Inject

private const val TAG = "ExploreViewModel"

class ExploreViewModel @Inject constructor(private val repository: MovieRepository) : ViewModel() {

    private val _sortFilterQuery = MutableLiveData<QueryParams>()

    val moviesList = _sortFilterQuery.switchMap {
        it?.let {
            val genres = it.genres.joinToString(separator = ",")
            repository.getVoteCountMovies(it.sortBy, genres).cachedIn(viewModelScope)
        }
    }

    init {
        _sortFilterQuery.value = QueryParams(SortBy.PopularityDesc)
    }

    fun sortMovies(sortBy: SortBy) {
        val queryParams = _sortFilterQuery.value
        queryParams?.sortBy = sortBy
        queryParams?.let {
            _sortFilterQuery.value = it
        }
    }

    fun filterMovies(genreId: Int) {
        val queryParams = _sortFilterQuery.value
        queryParams?.genres?.add(genreId)
        queryParams?.let {
            _sortFilterQuery.value = it
        }
    }
}

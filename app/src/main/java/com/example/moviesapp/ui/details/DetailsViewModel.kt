package com.example.moviesapp.ui.details

import androidx.lifecycle.*
import com.example.moviesapp.models.Movie
import com.example.moviesapp.models.MovieDetail
import com.example.moviesapp.repository.MovieRepository
import com.example.moviesapp.util.Result
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException

class DetailsViewModel(private val repository: MovieRepository, val movie: Movie) : ViewModel() {

    private val _movieDetail = MutableLiveData<Result<MovieDetail>>()
    val movieDetail: LiveData<Result<MovieDetail>>
        get() = _movieDetail


    init {
        getMovieDetails()
    }

    fun getMovieDetails() {
        viewModelScope.launch {
            _movieDetail.postValue( repository.movieDetail(movie.id) )
        }
    }

}

class DetailsViewModelFactory(private val movie: Movie, private val repository: MovieRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetailsViewModel::class.java)) {
            return DetailsViewModel(repository, movie) as T
        }
        throw IllegalArgumentException("Unknown ViewModel")
    }
}
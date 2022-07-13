package com.example.moviesapp.ui.details

import android.util.Log
import androidx.lifecycle.*
import com.example.moviesapp.models.Movie
import com.example.moviesapp.models.MovieDetail
import com.example.moviesapp.repository.MovieRepository
import com.example.moviesapp.ui.home.HomeViewModel
import com.example.moviesapp.util.NetworkResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException
import javax.inject.Inject

private const val TAG = "DetailsViewModel"

class DetailsViewModel(private val repository: MovieRepository, val movie: Movie): ViewModel() {

    private val _networkStatus = MutableLiveData<NetworkResult>()
    val networkStatus: LiveData<NetworkResult>
        get() = _networkStatus

    private val _movieDetail = MutableLiveData<MovieDetail>()
    val movieDetail: LiveData<MovieDetail>
        get() = _movieDetail


    init {
        _movieDetail.value = MovieDetail()
//        getMovieDetails()
    }

//    private fun getMovieDetails() {
//        _networkStatus.value = NetworkResult.Loading
//        viewModelScope.launch (Dispatchers.IO) {
//            val networkResult = async { repository.getMovieDetails(movie.id.toLong()) }
//            val result = networkResult.await()
//            if(result is NetworkResult.Success<*>) {
//                setSuccessData(result.content)
//            }
//            _networkStatus.postValue(result)
//        }
//    }

    private fun setSuccessData(content: Any?) {
        content?.let {
            if(it is MovieDetail) {
                _movieDetail.postValue(it)
            }
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
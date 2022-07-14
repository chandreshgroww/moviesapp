package com.example.moviesapp.ui.home

import androidx.lifecycle.ViewModel
import com.example.moviesapp.repository.MovieRepository
import javax.inject.Inject

class HomeViewModel @Inject constructor(repository: MovieRepository) : ViewModel() {

    val popularMovieList = repository.popularMovieList

    val voteCountMovieList = repository.viewCountMovieList

}



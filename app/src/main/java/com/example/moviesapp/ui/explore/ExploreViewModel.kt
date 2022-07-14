package com.example.moviesapp.ui.explore

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.moviesapp.repository.MovieRepository
import javax.inject.Inject

class ExploreViewModel @Inject constructor(private val repository: MovieRepository): ViewModel() {

    val exploreMoviesList = repository.getVoteCountMovies().cachedIn(viewModelScope)
}
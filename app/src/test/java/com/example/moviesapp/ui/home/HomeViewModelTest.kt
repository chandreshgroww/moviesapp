package com.example.moviesapp.ui.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.moviesapp.models.MovieDetail
import com.example.moviesapp.models.QueryParams
import com.example.moviesapp.repository.MovieRepository
import com.example.moviesapp.ui.explore.ExploreViewModel
import com.example.moviesapp.util.MainCoroutineRule
import com.example.moviesapp.util.Result
import com.example.moviesapp.util.SortBy
import com.example.moviesapp.util.getOrAwaitValue
import com.google.common.truth.Truth
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class HomeViewModelTest {
    // No need to test
}
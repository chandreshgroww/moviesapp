package com.example.moviesapp.ui.details

import android.util.Log
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.moviesapp.models.Movie
import com.example.moviesapp.models.MovieDetail
import com.example.moviesapp.repository.MovieRepository
import com.example.moviesapp.repository.MovieRepositoryTest
import com.example.moviesapp.util.MainCoroutineRule
import com.example.moviesapp.util.Result
import com.example.moviesapp.util.getOrAwaitValue
import com.google.common.truth.Truth
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.runTest
import org.junit.*
import org.junit.Assert.*

private const val TAG = "DetailsViewModelTest"

class DetailsViewModelTest {

    // Executes each task synchronously using Architecture Components.
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    // Set the main coroutines dispatcher for unit testing.
    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @MockK
    private lateinit var repository: MovieRepository

    private lateinit var movie: Movie

    private lateinit var movieDetail: MovieDetail

    private lateinit var viewModel: DetailsViewModel

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxed = true)
        movie = Movie()
        movieDetail = MovieDetail(
            false,
            null,
            listOf(),
            -1,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null
        )
        viewModel = DetailsViewModel(repository, movie)
    }

    @Test
    fun `when getMovieDetails is called, livedata value is set`() =
        mainCoroutineRule.runTest {

            val res: Result<MovieDetail> = Result.success(movieDetail)

            coEvery { repository.movieDetail(any()) } returns res

            viewModel.getMovieDetails()

            Truth.assertThat(viewModel.movieDetail.getOrAwaitValue()).isEqualTo(res)
        }
}
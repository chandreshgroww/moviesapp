package com.example.moviesapp.ui.explore

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.paging.Pager
import androidx.paging.PagingData
import com.example.moviesapp.models.Movie
import com.example.moviesapp.models.MovieDetail
import com.example.moviesapp.models.QueryParams
import com.example.moviesapp.models.SortFilter
import com.example.moviesapp.repository.MovieRepository
import com.example.moviesapp.ui.details.DetailsViewModel
import com.example.moviesapp.util.MainCoroutineRule
import com.example.moviesapp.util.SortBy
import com.example.moviesapp.util.getOrAwaitValue
import com.google.common.truth.Truth
import com.google.common.truth.Truth.assertThat
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class ExploreViewModelTest {

    // Executes each task synchronously using Architecture Components.
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    // Set the main coroutines dispatcher for unit testing.
    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @MockK
    private lateinit var repository: MovieRepository

    private lateinit var viewModel: ExploreViewModel

    private lateinit var sortBy: SortBy

    private lateinit var queryParams: QueryParams

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxed = true)
        sortBy = SortBy.PopularityDesc
        queryParams = QueryParams()
        viewModel = ExploreViewModel(repository, sortBy)
    }


    @Test
    fun `when initializeGenreList is called, sortFilterQuery livedata is set`() =
        mainCoroutineRule.runTest {

            val resGenreList = listOf<SortFilter>(
                SortFilter(1, "adventure"),
                SortFilter(2, "action")
            )

            viewModel.initializeGenreList(resGenreList)

            assertThat(viewModel.sortFilterQuery.getOrAwaitValue().genres).isEqualTo(resGenreList)
        }

    @Test
    fun `when sortMovies is called, sortFilterQuery sortBy is changed`() =
        mainCoroutineRule.runTest {

            viewModel.sortMovies(
                SortFilter(
                    1,
                    SortBy.VoteCountDesc.displayName,
                    SortBy.VoteCountDesc.notation
                )
            )

            val result = viewModel.sortFilterQuery.getOrAwaitValue()

            assertThat(result.sortBy[0].isSelected).isEqualTo(false)

            assertThat(result.sortBy[1].isSelected).isEqualTo(true)
        }

    @Test
    fun `when filterMovies for new genre is called, sortFilterQuery genres is changed`() =
        mainCoroutineRule.runTest {

            val resGenreList = listOf<SortFilter>(
                SortFilter(1, "adventure"),
                SortFilter(2, "action")
            )

            viewModel.initializeGenreList(resGenreList)

            viewModel.filterMovies(SortFilter(1, "adventure"))

            val result = viewModel.sortFilterQuery.getOrAwaitValue()

            assertThat(result.genres[0].isSelected).isEqualTo(true)

            assertThat(result.genres[1].isSelected).isEqualTo(false)
        }

    @Test
    fun `when filterMovies for already selected genre is called, sortFilterQuery genres is changed`() =
        mainCoroutineRule.runTest {

            val resGenreList = listOf<SortFilter>(
                SortFilter(1, "adventure", "1", true),
                SortFilter(2, "action")
            )

            viewModel.initializeGenreList(resGenreList)

            viewModel.filterMovies(SortFilter(1, "adventure"))

            val result = viewModel.sortFilterQuery.getOrAwaitValue()

            assertThat(result.genres[0].isSelected).isEqualTo(false)

            assertThat(result.genres[1].isSelected).isEqualTo(false)
        }

    @Test
    fun `when filterMovies for more than one genre is called, sortFilterQuery genres is changed`() =
        mainCoroutineRule.runTest {

            val resGenreList = listOf<SortFilter>(
                SortFilter(1, "adventure", "1", true),
                SortFilter(2, "action")
            )

            viewModel.initializeGenreList(resGenreList)

            viewModel.filterMovies(SortFilter(1, "adventure"))
            viewModel.filterMovies(SortFilter(2, "action"))

            val result = viewModel.sortFilterQuery.getOrAwaitValue()

            assertThat(result.genres[0].isSelected).isEqualTo(false)

            assertThat(result.genres[1].isSelected).isEqualTo(true)
        }

//    @Test
//    fun `when getGenreParams is called with all false, return genre string`() {
//        queryParams.genres = mutableListOf(
//            SortFilter(1, "adventure", "1"),
//            SortFilter(2, "action", "2"),
//            SortFilter(3, "drama", "3")
//        )
//        println(viewModel.getGenreParams(queryParams))
//        Truth.assertThat(viewModel.getGenreParams(queryParams)).isEqualTo("")
//    }
//
//    @Test
//    fun `when getGenreParams is called with one true, return genre string`() {
//        val res = "1"
//        queryParams.genres = mutableListOf(
//            SortFilter(1, "adventure", "1", true),
//            SortFilter(2, "action")
//        )
//        println(viewModel.getGenreParams(queryParams))
//        Truth.assertThat(viewModel.getGenreParams(queryParams)).isEqualTo(res)
//    }
//
//    @Test
//    fun `when getGenreParams is called with multiple true, return genre string`() {
//        val res = "2, 3"
//        queryParams.genres = mutableListOf(
//            SortFilter(1, "adventure", "1"),
//            SortFilter(2, "action", "2", true),
//            SortFilter(3, "drama", "3", true)
//        )
//        println(viewModel.getGenreParams(queryParams))
//        Truth.assertThat(viewModel.getGenreParams(queryParams)).isEqualTo(res)
//    }
}

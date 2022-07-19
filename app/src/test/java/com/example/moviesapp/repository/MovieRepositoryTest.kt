package com.example.moviesapp.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import com.example.moviesapp.data.TestData
import com.example.moviesapp.database.ILocalDataSource
import com.example.moviesapp.database.LocalDataSourceTest
import com.example.moviesapp.models.SortFilter
import com.example.moviesapp.network.IRemoteDataSource
import com.example.moviesapp.network.RemoteDataSource
import com.example.moviesapp.network.RemoteDataSourceTest
import com.example.moviesapp.util.MainCoroutineRule
import com.example.moviesapp.util.Result
import com.example.moviesapp.util.getOrAwaitValue
import com.google.common.truth.Truth
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.runTest

import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class MovieRepositoryTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private lateinit var repository: MovieRepository

    private lateinit var remoteDataSourceTest: IRemoteDataSource

    private lateinit var localDataSourceTest: ILocalDataSource

    @Before
    fun setUp() {
        remoteDataSourceTest = RemoteDataSourceTest()
        localDataSourceTest = LocalDataSourceTest()
        repository = MovieRepository(remoteDataSourceTest, localDataSourceTest)
    }

    @Test
    fun `test resultLiveData`() = mainCoroutineRule.runBlockingTest {

        val loadingResult = Result(Result.Status.LOADING, null, null)

        val successResult = Result(Result.Status.SUCCESS, TestData.sortFilterList, null)

        val genreList: LiveData<Result<List<SortFilter>>> = resultLiveData(
            databaseQuery = { localDataSourceTest.getAllGenreList() },
            networkCall = { remoteDataSourceTest.fetchAllGenreList() },
            saveCallResult = { localDataSourceTest.addGenreList(it.genres) }
        )

        // loading state
        Truth.assertThat(genreList.getOrAwaitValue()).isEqualTo(loadingResult)

        // success state
        Truth.assertThat(genreList.getOrAwaitValue()).isEqualTo(successResult)

    }
}
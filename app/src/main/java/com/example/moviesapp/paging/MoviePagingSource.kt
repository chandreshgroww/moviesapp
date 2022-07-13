package com.example.moviesapp.paging

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.moviesapp.models.DiscoverResult
import com.example.moviesapp.models.Movie
import com.example.moviesapp.network.ApiService
import com.example.moviesapp.network.RemoteDataSource
import com.example.moviesapp.util.Result

private const val TAG = "MoviePagingSource"

class MoviePagingSource(private val remoteDataSource: RemoteDataSource) : PagingSource<Int, Movie>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        return try {
            val position = params.key ?: 1
            val response = remoteDataSource.apiService.getVoteCountMovieListAsync("vote_count.desc", position)
            LoadResult.Page(
                    data = response.results,
                    prevKey = null,
                    nextKey = if (position == response.total_pages) null else (position + 1)
                )
        } catch (e: Exception) {
            Log.i(TAG, "load: ${e.message}")
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {

        return state.anchorPosition?.let {
            state.closestPageToPosition(it)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(it)?.nextKey?.minus(1)
        }
    }
}
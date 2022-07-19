package com.example.moviesapp.paging

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.moviesapp.models.Movie
import com.example.moviesapp.network.BaseDataSource
import com.example.moviesapp.network.IRemoteDataSource
import com.example.moviesapp.network.RemoteDataSource
import com.example.moviesapp.util.Result
import com.example.moviesapp.util.SortBy

private const val TAG = "MoviePagingSource"

class MoviePagingSource(
    private val remoteDataSource: IRemoteDataSource,
    private val sortBy: SortBy,
    private val withGenres: String
) :
    PagingSource<Int, Movie>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        return try {
            val position = params.key ?: 1
            val response = remoteDataSource.fetchMoviesList(sortBy, withGenres, position)

            if (response.status == Result.Status.SUCCESS && response.data != null) {
                LoadResult.Page(
                    data = response.data.results,
                    prevKey = if (position == 1) null else (position - 1),
                    nextKey = if (position == response.data.total_pages) null else (position + 1)
                )
            } else {
                Log.i(TAG, "load: No Response")
                LoadResult.Error(throw Exception("No Response"))
            }

        } catch (e: Exception) {
            Log.i(TAG, "load: $e")
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
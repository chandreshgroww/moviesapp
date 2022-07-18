package com.example.moviesapp.ui.explore

import android.util.Log
import androidx.lifecycle.*
import androidx.paging.cachedIn
import com.example.moviesapp.models.QueryParams
import com.example.moviesapp.models.SortFilter
import com.example.moviesapp.repository.MovieRepository
import com.example.moviesapp.util.Result
import com.example.moviesapp.util.SortBy
import javax.inject.Inject

private const val TAG = "ExploreViewModel"

class ExploreViewModel @Inject constructor(
    private val repository: MovieRepository,
    private val sortByParam: SortBy
) : ViewModel() {
    
    private val _sortFilterQuery = MutableLiveData<QueryParams>()
    val sortFilterQuery: LiveData<QueryParams>
        get() = _sortFilterQuery

    val genreList = repository.genreMovieList

    private val sortByList = mutableListOf<SortFilter>()

    val moviesList = _sortFilterQuery.switchMap {
        it?.let { queryParam ->
            val genreParams = getGenreParams(queryParam)
            val sortByParams = getSortParams(queryParam)
            repository.getVoteCountMovies(sortByParams, genreParams).cachedIn(viewModelScope)
        }
    }

    init {
        _sortFilterQuery.value = QueryParams()
        initializeSortByList()
    }

    private fun getGenreParams(queryParam: QueryParams): String {
        val selectedGenreParams = queryParam.genres.filter { it.isSelected }
        return selectedGenreParams.joinToString { it.idString }
    }

    private fun initializeSortByList() {
        enumValues<SortBy>().forEach {
            val queryParam = SortFilter(-1, it.displayName, it.notation, false)
            if(sortByParam.notation == queryParam.idString)
                queryParam.isSelected = true
            sortByList.add(queryParam)
        }
        if(_sortFilterQuery.value?.sortBy.isNullOrEmpty())
            _sortFilterQuery.value?.sortBy = sortByList
    }

    fun initializeGenreList(genreList: List<SortFilter>) {
        _sortFilterQuery.value?.genres = genreList.toMutableList()
    }

    fun sortMovies(sortFilter: SortFilter) {
        val sortByParams = _sortFilterQuery.value
        sortByParams?.sortBy?.let { sortByList ->
            val alreadySelected = sortByList.filter { it.isSelected }
            if (alreadySelected.isNotEmpty())
                sortByList.filter { it.isSelected }[0].isSelected = false
            sortByList.filter { it.idString == sortFilter.idString }[0].isSelected = true
        }
        sortByParams?.let {
            _sortFilterQuery.value = it
        }
    }

    fun filterMovies(sortFilter: SortFilter) {
        val queryParams = _sortFilterQuery.value
        queryParams?.genres?.let { genreList ->
            val toSelect = genreList.filter { it.id == sortFilter.id }
            if (toSelect.isNotEmpty())
                toSelect[0].isSelected = !toSelect[0].isSelected
        }
        queryParams?.let {
            _sortFilterQuery.value = it
        }
    }

    private fun getSortParams(queryParam: QueryParams): SortBy {
        val sortQueryList = queryParam.sortBy.filter { it.isSelected }
        if (sortQueryList.isNotEmpty()) {
            val sortQuery = sortQueryList[0]
            return when (sortQuery.idString) {
                SortBy.VoteCountDesc.notation -> SortBy.VoteCountDesc
                SortBy.ReleaseDateDesc.notation -> SortBy.ReleaseDateDesc
                SortBy.PopularityDesc.notation -> SortBy.PopularityDesc
                SortBy.RevenueDesc.notation -> SortBy.RevenueDesc
                else -> SortBy.PopularityDesc
            }
        }
        return SortBy.PopularityDesc
    }
}

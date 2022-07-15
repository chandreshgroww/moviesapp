package com.example.moviesapp.models

import com.example.moviesapp.util.SortBy

data class QueryParams(
    var sortBy: SortBy = SortBy.PopularityDesc,
    var genres: MutableList<Int> = mutableListOf()
)
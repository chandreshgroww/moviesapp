package com.example.moviesapp.models

import com.example.moviesapp.util.SortBy

data class QueryParams(
    var sortBy: MutableList<SortFilter> = mutableListOf(),
    var genres: MutableList<SortFilter> = mutableListOf()
)
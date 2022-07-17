package com.example.moviesapp.models

data class MovieDetail(
    val adult: Boolean ?= false,
    val backdrop_path: String ?= null,
    val genres: List<SortFilter> ?= listOf(),
    val id: Int ?= -1,
    val overview: String ?= null,
    val popularity: Double ?= null,
    val poster_path: String ?= null,
    val release_date: String ?= null,
    val runtime: Int ?= null,
    val title: String ?= null,
    val vote_average: Double ?= null,
    val vote_count: Int ?= null
)
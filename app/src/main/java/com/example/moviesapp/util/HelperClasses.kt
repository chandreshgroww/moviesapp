package com.example.moviesapp.util

enum class SortBy(val notation: String, val displayName: String) {
    PopularityDesc("popularity.desc", "Popularity"),
    VoteCountDesc("vote_count.desc", "Vote Count"),
    ReleaseDateDesc("release_date.desc", "Release Date"),
    RevenueDesc("revenue.desc", "Revenue")
}

data class Result<out T>(val status: Status, val data: T?, val message: String?) {

    enum class Status {
        SUCCESS,
        ERROR,
        LOADING
    }

    companion object {
        fun <T> success(data: T): Result<T> {
            return Result(Status.SUCCESS, data, null)
        }

        fun <T> error(message: String, data: T? = null): Result<T> {
            return Result(Status.ERROR, data, message)
        }

        fun <T> loading(data: T? = null): Result<T> {
            return Result(Status.LOADING, data, null)
        }
    }
}


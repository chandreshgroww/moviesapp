package com.example.moviesapp.util

enum class SortBy(val notation: String) {
    PopularityDesc("popularity.desc"),
    VoteCount("vote_count.desc")
}

//sealed class NetworkResult<T>(
//    val data: T? = null,
//    val message: String? = null
//) {
//    class Success<T>(data: T) : NetworkResult<T>(data)
//    class Error<T>(message: String?, data: T? = null) : NetworkResult<T>(data, message)
//    class Loading<T> : NetworkResult<T>()
//}

sealed class NetworkResult {
    object Loading: NetworkResult()
    data class Error(val errorMessage: String?): NetworkResult()
    data class Success<T>(val content: T): NetworkResult()
}


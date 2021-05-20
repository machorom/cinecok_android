package com.daou.cinecok.data.model

import com.daou.cinecok.data.repository.MovieRepository
import com.daou.cinecok.data.repository.MovieRepository.ResponseFlag.*

data class MovieRepoResult<T>(
    val responseData: T,
    val responseFlag: MovieRepository.ResponseFlag
) {
    fun isSuccess() = (responseFlag == SUCCESS)
}

data class MovieRepoNAPIResult<T>(
    val repoResult: MovieRepoResult<T>,
    val pageInfo: PageInfo
)
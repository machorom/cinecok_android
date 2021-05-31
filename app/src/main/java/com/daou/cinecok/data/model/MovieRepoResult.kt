package com.daou.cinecok.data.model

import com.daou.cinecok.data.restapi.ResponseFlag
import com.daou.cinecok.data.restapi.ResponseFlag.*

data class MovieRepoResult<T>(
    val responseData: T,
    val responseFlag: ResponseFlag
) {
    fun isSuccess() = (responseFlag == SUCCESS)
}

data class MovieRepoNAPIResult<T>(
    val repoResult: MovieRepoResult<T>,
    val pageInfo: NPageInfo
)
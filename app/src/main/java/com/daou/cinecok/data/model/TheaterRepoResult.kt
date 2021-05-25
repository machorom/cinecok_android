package com.daou.cinecok.data.model

import com.daou.cinecok.data.restapi.ResponseFlag
import com.daou.cinecok.data.restapi.ResponseFlag.*

data class TheaterRepoResult<T>(
    val responseData: T,
    val responseFlag: ResponseFlag
) {
    fun isSuccess() = (responseFlag == SUCCESS)
}

data class TheaterRepoKAPIResult<T>(
    val repoResult: TheaterRepoResult<T>,
    val pageInfo: KPageInfo
)
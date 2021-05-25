package com.daou.cinecok.data.model


data class NPageInfo(
    var searchStartIdx: Int,
    var searchNextStartIdx: Int,
    var searchMaxIdx: Int
) {
    fun isNextPageExist() = (searchNextStartIdx <= searchMaxIdx)
}
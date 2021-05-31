package com.daou.cinecok.data.model

import androidx.annotation.IntRange

// 카카오는 Page Num 기준으로 Load
data class KPageInfo(
    @IntRange(from = 1, to = 45)
    var lastPageNum: Int,
    var hasNext: Boolean
) {
}
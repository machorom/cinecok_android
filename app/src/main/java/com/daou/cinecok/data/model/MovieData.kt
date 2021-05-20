package com.daou.cinecok.data.model

import android.os.Parcelable
import com.daou.cinecok.base.BaseMovieInfo
import com.daou.cinecok.data.localdb.entity.ScrapMovieEntitiy
import com.daou.cinecok.data.restapi.NMovieData
import com.daou.cinecok.utils.CineUtils
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MovieData(
    override val title: String,
    override val imgURL: String,
    override val pubDate: String,
    override val actor: String,
    override val director: String,
    override val link: String,
    val userRating: String = "0",
    var imgHighQualityURL: String = "",
    var movieDescription: String = "",
    val ratingStar: Float = userRating.toFloat() * 0.5F,
    val compareKey: String = (title + actor + pubDate + link)
) : BaseMovieInfo(), Parcelable {
    constructor(naverMovieData: NMovieData, highQualityImageURL: String = "") : this(
        CineUtils.removeHTMLTag(naverMovieData.title),
        naverMovieData.imgURL,
        naverMovieData.pubDate,
        CineUtils.removeHTMLTag(naverMovieData.actor).run { CineUtils.arrangeNApiResponseData(this) },
        CineUtils.removeHTMLTag(naverMovieData.director).run { CineUtils.arrangeNApiResponseData(this) },
        naverMovieData.link,
        naverMovieData.userRating.toString(),
        highQualityImageURL
    )

    constructor(scrapMovieEntity: ScrapMovieEntitiy) : this(
        scrapMovieEntity.title,
        scrapMovieEntity.imgURL,
        scrapMovieEntity.pubDate,
        scrapMovieEntity.actor,
        scrapMovieEntity.director,
        scrapMovieEntity.link,
        scrapMovieEntity.userRating,
        scrapMovieEntity.imgHighQualityURL,
        scrapMovieEntity.description
    )

    override fun toString(): String {
        return compareKey
    }
}
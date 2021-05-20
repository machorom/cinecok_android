package com.daou.cinecok.data.restapi;

import com.daou.cinecok.base.BaseMovieInfo
import com.google.gson.annotations.SerializedName

data class NSearchMovieResponse(
    @SerializedName("total")
    val totalResultNum: Int,

    @SerializedName("start")
    val start : Int,

    @SerializedName("display")
    val display : Int,

    @SerializedName("items")
    val listResult : List<NMovieData>
)

data class NMovieData (
    @SerializedName("title")
    override val title: String,

    @SerializedName("image")
    override val imgURL: String,

    @SerializedName("pubDate")
    override val pubDate: String,

    @SerializedName("director")
    override val director: String,

    @SerializedName("userRating")
    val userRating: Float,

    @SerializedName("actor")
    override val actor : String,

    @SerializedName("link")
    override val link: String
): BaseMovieInfo()

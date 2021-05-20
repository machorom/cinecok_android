package com.daou.cinecok.data.localdb.entity

import androidx.room.*
import com.daou.cinecok.base.BaseMovieInfo
import com.daou.cinecok.data.model.MovieData

@Entity(tableName = "movie_favorite_table")
data class ScrapMovieEntitiy(
    @PrimaryKey
    @ColumnInfo(name = "compareKey")
    val compareKey: String,

    @ColumnInfo(name = "title")
    override val title: String,

    @ColumnInfo(name = "imageURL")
    override val imgURL: String,

    @ColumnInfo(name = "pubDate")
    override val pubDate: String,

    @ColumnInfo(name = "director")
    override val director: String,

    @ColumnInfo(name = "actor")
    override val actor: String,

    @ColumnInfo(name = "userRating")
    val userRating: String,

    @ColumnInfo(name = "link")
    override val link: String,

    @ColumnInfo(name = "description")
    val description: String,

    @ColumnInfo(name = "imgHighQualityURL")
    val imgHighQualityURL: String
) : BaseMovieInfo(){
    constructor(movieData: MovieData) : this(
        movieData.compareKey,
        movieData.title,
        movieData.imgURL,
        movieData.pubDate,
        movieData.director,
        movieData.actor,
        movieData.userRating,
        movieData.link,
        movieData.movieDescription,
        movieData.imgHighQualityURL
    )
}


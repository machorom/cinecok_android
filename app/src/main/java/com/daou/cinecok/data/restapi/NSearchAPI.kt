package com.daou.cinecok.data.restapi

import retrofit2.http.GET
import retrofit2.http.Query

interface NSearchAPI {
    @GET("v1/search/movie.json")
    suspend fun searchMovie(@Query("query") query : String, @Query("genre") genre : Int,
                            @Query("start") start :Int, @Query("country") country : String) : NSearchMovieResponse
}
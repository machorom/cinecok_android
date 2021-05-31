package com.daou.cinecok.data.restapi

import retrofit2.http.GET
import retrofit2.http.Query

interface KSearchAPI {
    @GET("v2/local/search/keyword.json")
    suspend fun searchTheater(@Query("x") longitude: Double, @Query("y") latitude: Double,
                              @Query("page") start: Int,
                              @Query("query") query: String = "영화관", @Query("radius") radius: Int = 20000): KSearchTheaterResponse
}
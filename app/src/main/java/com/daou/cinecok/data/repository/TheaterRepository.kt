package com.daou.cinecok.data.repository

import android.content.res.Resources
import androidx.annotation.IntRange
import com.daou.cinecok.data.model.*
import com.daou.cinecok.data.restapi.KSearchAPI
import com.daou.cinecok.data.restapi.KTheaterData
import com.daou.cinecok.data.restapi.ResponseFlag
import java.io.IOException

interface TheaterRepository {
    fun getSysResources() : Resources
    suspend fun getTheaterList(longitude: Double, latitude: Double, @IntRange(from = 1, to = 45) pageNum: Int = 1) : TheaterRepoKAPIResult<List<KTheaterData>>
}

class TheaterRepositoryImpl(private val searchApi: KSearchAPI, private val resource : Resources): TheaterRepository {
    override fun getSysResources(): Resources = resource

    override suspend fun getTheaterList(
        longitude: Double,
        latitude: Double,
        pageNum: Int
    ): TheaterRepoKAPIResult<List<KTheaterData>> {
        // 저장용
        val pageInfo = KPageInfo(pageNum,true)
        val responseList : MutableList<KTheaterData> = mutableListOf()
        lateinit var responseFlag : ResponseFlag
        try {
            val response = searchApi.searchTheater(longitude, latitude, pageNum)
            if (response.listResult.isEmpty()) {
                responseFlag = ResponseFlag.NO_RESULT
            }
            else {
                pageInfo.hasNext = !response.metaData.isEnd

                responseList.addAll(response.listResult)

                responseFlag = ResponseFlag.SUCCESS
            }
        } catch (e: IOException) {
            responseFlag = ResponseFlag.CONNECT_FAIL
        }
        return TheaterRepoKAPIResult(TheaterRepoResult( responseList.toList(), responseFlag), pageInfo)
    }

}
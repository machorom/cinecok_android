package com.daou.cinecok.data.repository

import android.content.res.Resources
import com.daou.cinecok.data.localdb.AppDatabase
import com.daou.cinecok.data.localdb.entity.ScrapMovieEntitiy
import com.daou.cinecok.data.localdb.entity.SearchRecordEntitiy
import com.daou.cinecok.data.model.MovieData
import com.daou.cinecok.data.restapi.NSearchAPI
import com.daou.cinecok.data.restapi.NSearchMovieRequest
import com.daou.cinecok.data.model.MovieRepoNAPIResult
import com.daou.cinecok.data.model.MovieRepoResult
import com.daou.cinecok.data.model.NPageInfo
import com.daou.cinecok.data.restapi.ResponseFlag
import com.daou.cinecok.utils.CineUtils
import org.jsoup.Jsoup
import java.io.IOException

interface MovieRepository {
    fun getSysResources() : Resources
    suspend fun getMovieList( request : NSearchMovieRequest) : MovieRepoNAPIResult<List<MovieData>>
    suspend fun getMovieDescription(soruceURL: String): MovieRepoResult<String>
    suspend fun getRecordList() : List<SearchRecordEntitiy>
    suspend fun saveRecordData(title : String)
    suspend fun removeRecordData(title : String)
    suspend fun saveFavoriteMovieData(movieData : MovieData)
    suspend fun removeFavoriteMovieData(compareKey: String)
    suspend fun getExistFavoriteMovie(compareKey: String) : Boolean
    suspend fun getFavoriteMovie() : List<MovieData>
    suspend fun getHighQualityImageURL( link : String) : String
    suspend fun getRecommendMovieList( link : String) : List<MovieData>
    suspend fun getScheduledMovieList() : List<MovieData>
}


class MovieRepositoryImpl(private val searchAPI : NSearchAPI, private val appDB : AppDatabase, private val resource : Resources) : MovieRepository {
    override suspend fun getMovieDescription(soruceURL: String): MovieRepoResult<String> {
        lateinit var description: String
        lateinit var responseFlag: ResponseFlag

        try {
            val doc = Jsoup.connect(soruceURL).get();
            description = doc.select("p.con_tx").text()
            responseFlag = ResponseFlag.SUCCESS
        } catch (ex: IOException) {
            description = ""
            responseFlag = ResponseFlag.CONNECT_FAIL
        }

        return MovieRepoResult(description, responseFlag)
    }


    override suspend fun getRecordList() : List<SearchRecordEntitiy>{
        return appDB.getSearchRecordDao().getSearchList()
    }


    override suspend fun saveRecordData(title : String) {
        val curDate = CineUtils.getCurrentDate()
        val date = String.format("%02d/%02d/%02d", curDate[0], curDate[1], curDate[2])
        val time = String.format("%02d%02d%02d",curDate[3],curDate[4],curDate[5])
        appDB.getSearchRecordDao().insert(SearchRecordEntitiy(title, date, time))
    }

    override suspend fun removeRecordData(title: String) {
        appDB.getSearchRecordDao().removeSearchRecord(title)
    }


    override suspend fun getMovieList( request : NSearchMovieRequest) : MovieRepoNAPIResult<List<MovieData>> {
        val pageInfo = NPageInfo(0,0,0)
        val responseList : MutableList<MovieData> = mutableListOf()
        lateinit var responseFlag : ResponseFlag

        try {
            val response = searchAPI.searchMovie(request.query, request.genre, request.start, request.country)
            if(response.listResult.isEmpty()) {
                responseFlag = ResponseFlag.NO_RESULT
            }
            else {
                pageInfo.searchStartIdx = response.start
                pageInfo.searchNextStartIdx = response.start + response.display
                pageInfo.searchMaxIdx = response.totalResultNum

                for (data in response.listResult)
                    responseList.add(MovieData(data))

                responseFlag = ResponseFlag.SUCCESS
            }
        }catch( ex : IOException) {
            responseFlag =  ResponseFlag.CONNECT_FAIL
        }

        return MovieRepoNAPIResult(MovieRepoResult( responseList.toList(), responseFlag), pageInfo)
    }


    override fun getSysResources(): Resources = resource


    override suspend fun removeFavoriteMovieData(compareKey : String) {
        appDB.getFavoriteMovieDao().removeFavoriteMovie(compareKey)
    }


    override suspend fun getExistFavoriteMovie(compareKey: String) : Boolean {
        return appDB.getFavoriteMovieDao().isExistInFavorite(compareKey)
    }


    override suspend fun getFavoriteMovie(): List<MovieData> {
        val result = appDB.getFavoriteMovieDao().getFavoriteMovieList()
        val responseList : MutableList<MovieData> = mutableListOf()
        for (data in result)
            responseList.add(MovieData(data))

        return responseList
    }


    override suspend fun getHighQualityImageURL(link: String): String {
        lateinit var highQualityImageURL : String
        try {
                val doc = Jsoup.connect(link).get();
                val doc_poster = doc.select("div.poster").select("img")
                highQualityImageURL = doc_poster[0].absUrl("src").toString().substringBefore('?')
        }catch(ex : Exception) {
            highQualityImageURL = ""
        }

        return highQualityImageURL
    }

    override suspend fun getRecommendMovieList(link: String): List<MovieData> {
        val resultList = mutableListOf<MovieData>()
        try {
            val doc = Jsoup.connect(link).get();
            val doc_lst = doc.select("div.obj_section")[0].select("ul.lst_detail_t1")[0].select("li")
            var presentNum = if (doc_lst.size > 12 ) 12 else doc_lst.size
            for(i in 0 until presentNum){
                with(doc_lst[i]) {
                    val imgURL = select("div.thumb").select("img")[0].absUrl("src").toString()
                    val title = select("dt.tit")[0].select("a")[0].text()
                    val userRating = select("span.num")[0].text()
                    resultList.add(MovieData(title,imgURL,"","","","",userRating,"",(i+1).toString() + ' '))
                }
            }
        } catch( ex : Exception) {
            //TODO :: 에러처리
        }

        return resultList
    }


    override suspend fun getScheduledMovieList(): List<MovieData> {
        val resultList = mutableListOf<MovieData>()
        try {
            val doc = Jsoup.connect(SCHEDULED_MV_CRAWLING_URL).get();
            val doc_lst = doc.select("div.obj_section")[0].select("ul.lst_detail_t1")[0].select("li")
            var presentNum = if (doc_lst.size > 5 ) 5 else doc_lst.size
            for(i in 0 until presentNum){
                with(doc_lst[i]) {
                    val imgURL = select("div.thumb").select("img")[0].absUrl("src").toString()
                    val highQualImgURL = imgURL.substringBefore('?')
                    val title = select("dt.tit")[0].select("a")[0].text()
                    resultList.add(MovieData(title,imgURL,"","","","","0",highQualImgURL))
                }
            }
        } catch( ex : Exception) {
            //TODO :: 에러처리
        }

        return resultList
    }


    override suspend fun saveFavoriteMovieData(movieData: MovieData) {
        appDB.getFavoriteMovieDao().insert(ScrapMovieEntitiy(movieData))
    }

    companion object {
        var SCHEDULED_MV_CRAWLING_URL = "https://movie.naver.com/movie/running/premovie.nhn?order=interestRate"
        var RESERVE_ORDER_MV_CRAWLING_URL = "https://movie.naver.com/movie/running/current.nhn"
        var GRADE_ORDER_MV_CRAWLING_URL = "https://movie.naver.com/movie/running/current.nhn?view=list&tab=normal&order=point"
    }
}
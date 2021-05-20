package com.daou.cinecok.data.localdb.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.daou.cinecok.data.localdb.entity.ScrapMovieEntitiy

@Dao
interface ScrapMovieDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(movieData : ScrapMovieEntitiy)

    @Query("SELECT * FROM movie_favorite_table ")
    suspend fun getFavoriteMovieList(): List<ScrapMovieEntitiy>

    @Query("DELETE FROM movie_favorite_table WHERE compareKey = :compareKey")
    suspend fun removeFavoriteMovie(compareKey : String)

    @Query("SELECT EXISTS ( select * from movie_favorite_table where compareKey = :compareKey) as success")
    suspend fun isExistInFavorite(compareKey : String) : Boolean
}
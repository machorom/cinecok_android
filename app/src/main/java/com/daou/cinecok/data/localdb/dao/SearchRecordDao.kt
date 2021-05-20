package com.daou.cinecok.data.localdb.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.daou.cinecok.data.localdb.entity.SearchRecordEntitiy

@Dao
interface SearchRecordDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(searchData : SearchRecordEntitiy)

    @Query("SELECT * FROM search_record_table ")
    suspend fun getSearchList(): List<SearchRecordEntitiy>

    @Query("DELETE FROM search_record_table WHERE search_title = :searchTitle")
    suspend fun removeSearchRecord(searchTitle : String)
}

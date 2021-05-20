package com.daou.cinecok.data.localdb.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "search_record_table")
data class SearchRecordEntitiy (
    @PrimaryKey
    @ColumnInfo(name = "search_title")
    val searchTitle : String,

    @ColumnInfo(name = "search_date")
    val searchDate : String,

    @ColumnInfo(name = "search_time")
    val searchtime : String
) {
    override fun toString(): String = searchTitle
    //for diffutil compare key
}


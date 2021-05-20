package com.daou.cinecok.data.localdb

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.daou.cinecok.data.localdb.dao.ScrapMovieDao
import com.daou.cinecok.data.localdb.dao.SearchRecordDao
import com.daou.cinecok.data.localdb.entity.ScrapMovieEntitiy
import com.daou.cinecok.data.localdb.entity.SearchRecordEntitiy
import com.daou.cinecok.utils.AppConstants.ROOM_DB_NAME

@Database(entities = [SearchRecordEntitiy::class, ScrapMovieEntitiy::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getSearchRecordDao(): SearchRecordDao
    abstract fun getFavoriteMovieDao() : ScrapMovieDao

    companion object {
        private val DB_NAME = ROOM_DB_NAME
        private lateinit var instance: AppDatabase

        fun getInstance(context: Context): AppDatabase {
            if(::instance.isInitialized) {
                return instance
            } else {
                synchronized(this) {
                    return buildDatabase(context)
                }
            }
        }

        private fun buildDatabase(context: Context): AppDatabase {
            return Room.databaseBuilder(context.applicationContext, AppDatabase::class.java, DB_NAME)
                .addCallback(object : RoomDatabase.Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)
                    }
                }).build()
        }
    }

}

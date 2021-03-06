package com.example.vin.moviecatalogue.data.local.room

import android.content.Context

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.vin.moviecatalogue.entity.Movie
import com.example.vin.moviecatalogue.entity.TVShow

@Database(entities=[Movie::class, TVShow::class],
    version = 1,
    exportSchema = false)
abstract class MovieCatalogueDatabase: RoomDatabase() {
    abstract fun Dao(): Dao

    companion object{
        @Volatile
        private var INSTANCE: MovieCatalogueDatabase? = null

        fun getInstance(context: Context): MovieCatalogueDatabase =
            INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    MovieCatalogueDatabase::class.java,
                    "MovieCatalogue.db"
                ).build().apply {
                    INSTANCE = this
                }
            }
    }
}
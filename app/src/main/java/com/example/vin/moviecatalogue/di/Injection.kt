package com.example.vin.moviecatalogue.di

import android.content.Context
import com.example.vin.moviecatalogue.data.Repository
import com.example.vin.moviecatalogue.data.local.LocalDataSource
import com.example.vin.moviecatalogue.data.local.room.MovieCatalogueDatabase
import com.example.vin.moviecatalogue.data.remote.RemoteDataSource

object Injection {
    fun provideRepository(context: Context): Repository{
        val database = MovieCatalogueDatabase.getInstance(context)

        val remoteDataSource = RemoteDataSource()
        val localDataSource = LocalDataSource.getInstance(database.Dao())

        return Repository.getInstance(remoteDataSource, localDataSource)
    }
}
package com.example.vin.moviecatalogue.data.local

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import com.example.vin.moviecatalogue.data.local.room.Dao
import com.example.vin.moviecatalogue.entity.Movie
import com.example.vin.moviecatalogue.entity.TVShow
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class LocalDataSource private constructor(private val dao: Dao) {
    companion object{
        private var INSTANCE: LocalDataSource? = null

        fun getInstance(dao: Dao): LocalDataSource =
            INSTANCE ?: LocalDataSource(dao)
    }

    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    fun getAllFavoriteMovie(): DataSource.Factory<Int, Movie> = dao.getFavoriteMovies()
    fun getAllFavoriteTVShow(): DataSource.Factory<Int, TVShow> = dao.getFavoriteTVShow()

    fun insertFavoriteMovie(movie: Movie){
        executorService.execute{ dao.insertFavoriteMovie(movie) }
    }
    fun insertFavoriteTVShow(tvShow: TVShow){
        executorService.execute { dao.insertFavoriteTVShow(tvShow) }
    }

    fun deleteFavoriteMovie(movie: Movie){
        executorService.execute { dao.deleteFavoriteMovie(movie) }
    }

    fun deleteFavoriteTVShow(tvShow: TVShow){
        executorService.execute { dao.deleteFavoriteTVShow(tvShow) }
    }

    fun checkMovieFavoriteStatus(id: Int?): LiveData<Movie>? = dao.checkMovieFavoriteStatus(id)
    fun checkTVShowFavoriteStatus(id: Int?): LiveData<TVShow>? = dao.checkTVShowFavoriteStatus(id)
}
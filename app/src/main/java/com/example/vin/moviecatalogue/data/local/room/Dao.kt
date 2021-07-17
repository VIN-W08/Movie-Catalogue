package com.example.vin.moviecatalogue.data.local.room

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.*
import androidx.room.Dao
import com.example.vin.moviecatalogue.entity.Movie
import com.example.vin.moviecatalogue.entity.TVShow

@Dao
interface Dao {

    @Query("SELECT * FROM movie")
    fun getFavoriteMovies(): DataSource.Factory<Int, Movie>

    @Query("SELECT * FROM TVShow")
    fun getFavoriteTVShow(): DataSource.Factory<Int, TVShow>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFavoriteMovie(movie: Movie)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFavoriteTVShow(tvShow: TVShow)

    @Delete
    fun deleteFavoriteMovie(movie: Movie)

    @Delete
    fun deleteFavoriteTVShow(tvShow: TVShow)

    @Query("SELECT * FROM Movie WHERE movieId=:id")
    fun checkMovieFavoriteStatus(id: Int?): LiveData<Movie>?

    @Query("SELECT * FROM TVShow WHERE tvShowId=:id")
    fun checkTVShowFavoriteStatus(id: Int?): LiveData<TVShow>?
}
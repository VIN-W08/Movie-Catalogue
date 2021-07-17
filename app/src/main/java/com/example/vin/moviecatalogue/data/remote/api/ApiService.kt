package com.example.vin.moviecatalogue.data.remote.api

import com.example.vin.moviecatalogue.data.remote.response.*
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("movie/now_playing")
    fun getMovies(
        @Query(value="api_key") apiKey: String,
        @Query(value="language") language: String
    ): Call<MoviesResponse>

    @GET("tv/airing_today")
    fun getTVShows(
        @Query(value="api_key") apiKey: String,
        @Query(value="language") language: String
    ): Call<TVShowsResponse>

    @GET("movie/{movie_id}")
    fun getMovieById(
        @Path(value="movie_id") id: Int?,
        @Query(value="api_key") apiKey: String,
        @Query(value="language") language: String
    ): Call<MovieResponse>

    @GET("tv/{tv_id}")
    fun getTVShowById(
        @Path(value="tv_id") id: Int?,
        @Query(value="api_key") apiKey: String,
        @Query(value="language") language: String
    ): Call<TVShowResponse>
}
package com.example.vin.moviecatalogue.utils

import com.example.vin.moviecatalogue.data.remote.api.ApiConfig
import com.example.vin.moviecatalogue.data.remote.response.MovieResponse
import com.example.vin.moviecatalogue.data.remote.response.MoviesResponse
import com.example.vin.moviecatalogue.data.remote.response.TVShowResponse
import com.example.vin.moviecatalogue.data.remote.response.TVShowsResponse
import com.example.vin.moviecatalogue.entity.Movie
import com.example.vin.moviecatalogue.entity.TVShow

object Data {

    private val API_KEY = "54187bf3e5691bf522c30cb62bb0b0af"
    private val LANGUAGE = "en-US"

    fun getMoviesData(): ArrayList<Movie>{
        val movies = ArrayList<Movie>()
        val client = ApiConfig.getApiService().getMovies(API_KEY, LANGUAGE)
        val response = client.execute()
        for(moviesRes in response.body()?.results!!){
            movies.add(
                Movie(
                    moviesRes?.id,
                    "https://image.tmdb.org/t/p/original"+moviesRes?.posterPath,
                    moviesRes?.originalTitle,
                    moviesRes?.overview,
                    moviesRes?.releaseDate
                ))
        }
        return movies
    }

    fun getTVShowData(): ArrayList<TVShow>{
        val tvShows = ArrayList<TVShow>()
        val client = ApiConfig.getApiService().getTVShows(API_KEY, LANGUAGE)
        val response = client.execute()
        for(tvShowRes in response.body()?.results!!){
            tvShows.add(
                TVShow(
                    tvShowRes?.id,
                    "https://image.tmdb.org/t/p/original"+tvShowRes?.posterPath,
                    tvShowRes?.originalName,
                    tvShowRes?.overview,
                    tvShowRes?.firstAirDate
                ))
        }

        return tvShows
    }

    fun getMovieById(id: Int?): Movie{
        val client = ApiConfig.getApiService().getMovieById(id, API_KEY, LANGUAGE)
        val response = client.execute()
        val movie = Movie(
            response.body()?.id,
            "https://image.tmdb.org/t/p/original"+response.body()?.posterPath,
            response.body()?.originalTitle,
            response.body()?.overview,
            response.body()?.releaseDate
        )

        return movie
    }

    fun getTVShowById(id: Int?): TVShow {
        val client = ApiConfig.getApiService().getTVShowById(id, API_KEY, LANGUAGE)
        val response = client.execute()
        val tvShow = TVShow(
            response.body()?.id,
            "https://image.tmdb.org/t/p/original"+response.body()?.posterPath,
            response.body()?.originalName,
            response.body()?.overview,
            response.body()?.firstAirDate
        )

        return tvShow
    }

    fun getRemoteMoviesData(): MoviesResponse {
        val client = ApiConfig.getApiService().getMovies(API_KEY, LANGUAGE)
        val response = client.execute()

        return response.body()!!
    }

    fun getRemoteTVShowData(): TVShowsResponse {
        val client = ApiConfig.getApiService().getTVShows(API_KEY, LANGUAGE)
        val response = client.execute()

        return response.body()!!
    }

    fun getRemoteMovieById(id: Int?): MovieResponse{
        val client = ApiConfig.getApiService().getMovieById(id, API_KEY, LANGUAGE)
        val response = client.execute()

        return response.body()!!
    }

    fun getRemoteTVShowById(id: Int?): TVShowResponse {
        val client = ApiConfig.getApiService().getTVShowById(id, API_KEY, LANGUAGE)
        val response = client.execute()

        return response.body()!!
    }
}
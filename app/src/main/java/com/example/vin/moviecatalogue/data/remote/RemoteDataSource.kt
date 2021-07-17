package com.example.vin.moviecatalogue.data.remote

import com.example.vin.moviecatalogue.data.remote.api.ApiConfig
import com.example.vin.moviecatalogue.data.remote.response.*
import com.example.vin.moviecatalogue.utils.EspressoIdlingResource
import retrofit2.await

class RemoteDataSource {
    companion object {
        private val API_KEY = "54187bf3e5691bf522c30cb62bb0b0af"
        private val LANGUAGE = "en-US"

        @Volatile
        private var instance: RemoteDataSource? = null

        fun getInstance(): RemoteDataSource =
            instance ?: synchronized(this) {
                RemoteDataSource().apply {
                    instance = this
                }
            }
    }

    suspend fun getAllMovies(callback: LoadMoviesCallback){
        EspressoIdlingResource.increment()
        ApiConfig.getApiService().getMovies(API_KEY, LANGUAGE).await().let { moviesResponse ->
            callback.onMoviesReceived(moviesResponse)
            EspressoIdlingResource.decrement()
        }
    }

    suspend fun getMovieById(id: Int?, callback: LoadMovieByIdCallback){
        EspressoIdlingResource.increment()
        ApiConfig.getApiService().getMovieById(id, API_KEY, LANGUAGE).await().let { movieResponse ->
            callback.onMovieReceived(movieResponse)
            EspressoIdlingResource.decrement()
        }
    }

    suspend fun getAllTVShows(callback: LoadTVShowsCallback){
        EspressoIdlingResource.increment()
        ApiConfig.getApiService().getTVShows(API_KEY, LANGUAGE).await().let { tvShowsResponse ->
            callback.onTVShowsReceived(tvShowsResponse)
            EspressoIdlingResource.decrement()
        }
    }

    suspend fun getTVShowById(id: Int?, callback: LoadTVShowByIdCallback){
        EspressoIdlingResource.increment()
        ApiConfig.getApiService().getTVShowById(id, API_KEY, LANGUAGE).await().let { tvShowResponse ->
            callback.onTVShowReceived(tvShowResponse)
            EspressoIdlingResource.decrement()
        }
    }

    interface LoadMoviesCallback{
        fun onMoviesReceived(moviesResponse: MoviesResponse)
    }
    interface LoadMovieByIdCallback{
        fun onMovieReceived(movieResponse: MovieResponse)
    }
    interface LoadTVShowsCallback{
        fun onTVShowsReceived(tvShowsResponse: TVShowsResponse)
    }
    interface LoadTVShowByIdCallback{
        fun onTVShowReceived(tvShowResponse: TVShowResponse)
    }
}
package com.example.vin.moviecatalogue.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.vin.moviecatalogue.data.local.LocalDataSource
import com.example.vin.moviecatalogue.data.remote.RemoteDataSource
import com.example.vin.moviecatalogue.data.remote.response.*
import com.example.vin.moviecatalogue.entity.Movie
import com.example.vin.moviecatalogue.entity.TVShow
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

class Repository(private val remoteDataSource: RemoteDataSource,
                 private val localDataSource: LocalDataSource
) {
    companion object {
        private val API_KEY = "54187bf3e5691bf522c30cb62bb0b0af"
        private val LANGUAGE = "en-US"

        @Volatile
        private var instance: Repository? = null
        fun getInstance(remoteDataSource: RemoteDataSource, localDataSource: LocalDataSource): Repository =
            instance ?: synchronized(this) {
                instance ?: Repository(remoteDataSource, localDataSource).apply {
                    instance = this
                }
            }
    }

    fun getAllMovie(): LiveData<ArrayList<Movie>>{
        val moviesResult = MutableLiveData<ArrayList<Movie>>()
        CoroutineScope(IO).launch {
            remoteDataSource.getAllMovies(object : RemoteDataSource.LoadMoviesCallback{
                override fun onMoviesReceived(moviesResponse: MoviesResponse) {
                    val movies = ArrayList<Movie>()
                    for (item in moviesResponse.results!!) {
                        movies.add(
                            Movie(
                                item?.id,
                                "https://image.tmdb.org/t/p/original" + item?.posterPath,
                                item?.originalTitle,
                                item?.overview,
                                item?.releaseDate
                            )
                        )
                    }
                    moviesResult.postValue(movies)
                }
            })
        }

        return moviesResult
    }

    fun getMovieById(id: Int?): LiveData<Movie>{
        val movie = MutableLiveData<Movie>()
        CoroutineScope(IO).launch {
            remoteDataSource.getMovieById(id, object : RemoteDataSource.LoadMovieByIdCallback{
                override fun onMovieReceived(movieResponse: MovieResponse) {
                    movie.postValue(
                        Movie(
                            movieResponse.id,
                            "https://image.tmdb.org/t/p/original" + movieResponse?.posterPath,
                            movieResponse.originalTitle,
                            movieResponse.overview,
                            movieResponse.releaseDate
                        )
                    )
                }
            })
        }

        return movie
    }

    fun getAllTVShow(): LiveData<ArrayList<TVShow>>{
        val tvShowsResult = MutableLiveData<ArrayList<TVShow>>()
        CoroutineScope(IO).launch {
            remoteDataSource.getAllTVShows(object : RemoteDataSource.LoadTVShowsCallback{
                override fun onTVShowsReceived(tvShowsResponse: TVShowsResponse) {
                    val tvShows = ArrayList<TVShow>()
                    for (item in tvShowsResponse.results!!) {
                        tvShows.add(
                            TVShow(
                                item?.id,
                                "https://image.tmdb.org/t/p/original" + item?.posterPath,
                                item?.originalName,
                                item?.overview,
                                item?.firstAirDate
                            )
                        )
                    }
                    tvShowsResult.postValue(tvShows)
                }
            })
        }

        return tvShowsResult
    }

    fun getTVShowById(id: Int?): LiveData<TVShow>{
        val tvShow = MutableLiveData<TVShow>()
        CoroutineScope(IO).launch {
            remoteDataSource.getTVShowById(id, object : RemoteDataSource.LoadTVShowByIdCallback{
                override fun onTVShowReceived(tvShowResponse: TVShowResponse) {
                    tvShow.postValue(
                        TVShow(
                            tvShowResponse.id,
                            "https://image.tmdb.org/t/p/original" + tvShowResponse?.posterPath,
                            tvShowResponse.originalName,
                            tvShowResponse.overview,
                            tvShowResponse.firstAirDate
                        )
                    )
                }
            })
        }

        return tvShow
    }

    fun getAllFavoriteMovie(): LiveData<PagedList<Movie>> = LivePagedListBuilder(localDataSource.getAllFavoriteMovie(), 5).build()
    fun getAllFavoriteTVShow(): LiveData<PagedList<TVShow>> = LivePagedListBuilder(localDataSource.getAllFavoriteTVShow(), 5).build()

    fun insertFavoriteMovie(movie: Movie) = localDataSource.insertFavoriteMovie(movie)
    fun insertFavoriteTVShow(tvShow: TVShow) = localDataSource.insertFavoriteTVShow(tvShow)

    fun deleteFavoriteMovie(movie: Movie) = localDataSource.deleteFavoriteMovie(movie)
    fun deleteFavoriteTVShow(tvShow: TVShow) = localDataSource.deleteFavoriteTVShow(tvShow)

    fun checkMovieFavoriteStatus(id: Int?): LiveData<Movie>? = localDataSource.checkMovieFavoriteStatus(id)
    fun checkTVShowFavoriteStatus(id: Int?): LiveData<TVShow>? = localDataSource.checkTVShowFavoriteStatus(id)
}
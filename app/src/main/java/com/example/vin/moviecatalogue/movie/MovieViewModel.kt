package com.example.vin.moviecatalogue.movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.DataSource
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.vin.moviecatalogue.data.Repository
import com.example.vin.moviecatalogue.entity.Movie
import com.example.vin.moviecatalogue.entity.TVShow

class MovieViewModel(private val repository: Repository): ViewModel() {
    private val movieId = MutableLiveData<Int?>()

    fun getMovies(): LiveData<ArrayList<Movie>>{
        return repository.getAllMovie()
    }

    fun setSelectedMovieId(id: Int){
        movieId.value = id
    }
    fun getSelectedMovie(): LiveData<Movie> = repository.getMovieById(movieId.value)

    fun getAllFavoriteMovie(): LiveData<PagedList<Movie>> = repository.getAllFavoriteMovie()

    fun insertFavoriteMovie(movie: Movie) = repository.insertFavoriteMovie(movie)

    fun deleteFavoriteMovie(movie: Movie) = repository.deleteFavoriteMovie(movie)

    fun checkMovieFavoriteStatus(id: Int?): LiveData<Movie>? = repository.checkMovieFavoriteStatus(id)
}
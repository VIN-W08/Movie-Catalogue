package com.example.vin.moviecatalogue.movie

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import com.example.vin.moviecatalogue.data.Repository
import com.example.vin.moviecatalogue.entity.Movie
import com.example.vin.moviecatalogue.utils.Data
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class MovieViewModelTest{

    private lateinit var viewModel: MovieViewModel

    @get: Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var repository: Repository

    @Mock
    private lateinit var observerMovies: Observer<ArrayList<Movie>>

    @Mock
    private lateinit var observerMovie: Observer<Movie>

    @Mock
    private lateinit var observerFavoriteMovie: Observer<PagedList<Movie>>

    @Mock
    private lateinit var moviePagedList: PagedList<Movie>

    @Before
    fun setUp() {
        viewModel = MovieViewModel(repository)
    }

    @Test
    fun testGetMovies() {
        val dummyMovies = Data.getMoviesData()
        val movies = MutableLiveData<ArrayList<Movie>>()
        movies.value = dummyMovies
        Mockito.`when`(repository.getAllMovie()).thenReturn(movies)
        val movieEntities = viewModel.getMovies().value
        Mockito.verify(repository).getAllMovie()
        assertNotNull(movieEntities)
        assertEquals(20, movieEntities?.size)

        viewModel.getMovies().observeForever(observerMovies)
        verify(observerMovies).onChanged(dummyMovies)
    }

    @Test
    fun testGetSelectedMovie() {
        val dummyMovieSelected = Data.getMoviesData()[0]
        val movieId = dummyMovieSelected.id
        val movie = MutableLiveData<Movie>()
        movie.value = dummyMovieSelected
        viewModel.setSelectedMovieId(movieId as Int)

        `when`(repository.getMovieById(movieId)).thenReturn(movie)
        val movieEntity = viewModel.getSelectedMovie()
        verify(repository).getMovieById(movieId)
        assertNotNull(movieEntity)
        assertEquals(movie.value?.id, movieEntity.value?.id)
        assertEquals(movie.value?.title, movieEntity.value?.title)
        assertEquals(movie.value?.poster, movieEntity.value?.poster)
        assertEquals(movie.value?.description, movieEntity.value?.description)
        assertEquals(movie.value?.releaseDate, movieEntity.value?.releaseDate)

        viewModel.getSelectedMovie().observeForever(observerMovie)
        verify(observerMovie).onChanged(dummyMovieSelected)
    }

    @Test
    fun getAllFavoriteMovie(){
        val dummyMovies = moviePagedList
        `when`(dummyMovies.size).thenReturn(7)
        val movie = MutableLiveData<PagedList<Movie>>()
        movie.value = dummyMovies
        Mockito.`when`(repository.getAllFavoriteMovie()).thenReturn(movie)
        val movieEntities = viewModel.getAllFavoriteMovie().value
        Mockito.verify(repository).getAllFavoriteMovie()
        assertNotNull(movieEntities)
        assertEquals(7, movieEntities?.size)

        viewModel.getAllFavoriteMovie().observeForever(observerFavoriteMovie)
        verify(observerFavoriteMovie).onChanged(dummyMovies)
    }

    @Test
    fun checkedFavoriteMovieStatus(){
        val dummyMovieSelected = Data.getMoviesData()[0]
        val movieId = dummyMovieSelected.id
        val movie = MutableLiveData<Movie>()
        movie.value = dummyMovieSelected

        `when`(repository.checkMovieFavoriteStatus(movieId)).thenReturn(movie)
        val movieEntity = viewModel.checkMovieFavoriteStatus(movieId)
        verify(repository).checkMovieFavoriteStatus(movieId)
        assertNotNull(movieEntity)
        assertEquals(movie.value?.id, movieEntity?.value?.id)
        assertEquals(movie.value?.title, movieEntity?.value?.title)
        assertEquals(movie.value?.poster, movieEntity?.value?.poster)
        assertEquals(movie.value?.description, movieEntity?.value?.description)
        assertEquals(movie.value?.releaseDate, movieEntity?.value?.releaseDate)

        viewModel.checkMovieFavoriteStatus(movieId)?.observeForever(observerMovie)
        verify(observerMovie).onChanged(dummyMovieSelected)
    }
}
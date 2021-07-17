package com.example.vin.moviecatalogue.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.example.vin.moviecatalogue.data.local.LocalDataSource
import com.example.vin.moviecatalogue.data.remote.RemoteDataSource
import com.example.vin.moviecatalogue.entity.Movie
import com.example.vin.moviecatalogue.entity.TVShow
import com.example.vin.moviecatalogue.utils.Data
import com.example.vin.moviecatalogue.utils.LiveDataTestUtils
import com.example.vin.moviecatalogue.utils.PagedListUtil
import com.nhaarman.mockitokotlin2.verify
import org.junit.Rule
import org.junit.Test
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertNotNull
import org.mockito.Mockito
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.eq
import com.nhaarman.mockitokotlin2.doAnswer
import kotlinx.coroutines.runBlocking
import org.mockito.Mockito.`when`

class RepositoryTest {

    private val remoteDataSource = Mockito.mock(RemoteDataSource::class.java)
    private val localDataSource = Mockito.mock(LocalDataSource::class.java)

    private val repository = FakeRepository(remoteDataSource, localDataSource)

    private val movieResponses = Data.getRemoteMoviesData()
    private val movieId = movieResponses.results?.get(0)?.id
    private val movieResponse = Data.getRemoteMovieById(movieId)

    private val tvShowResponses = Data.getRemoteTVShowData()
    private val tvShowId = tvShowResponses.results?.get(0)?.id
    private val tvShowResponse = Data.getRemoteTVShowById(tvShowId)

    private val movieDataSourceFactory = Mockito.mock(DataSource.Factory::class.java) as DataSource.Factory<Int, Movie>
    private val tvShowDataSourceFactory = Mockito.mock(DataSource.Factory::class.java) as DataSource.Factory<Int, TVShow>

    private val dummyMovies = Data.getMoviesData()
    private val dummyTVShows = Data.getTVShowData()

    @get: Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Test
    fun testGetAllMovie() {
        runBlocking {
            doAnswer { invocation ->
                (invocation.arguments[0] as RemoteDataSource.LoadMoviesCallback).onMoviesReceived(
                    movieResponses
                )
            }.`when`(remoteDataSource).getAllMovies(any())
        }
        val movies = LiveDataTestUtils.getValue(repository.getAllMovie())
        runBlocking {
            verify(remoteDataSource).getAllMovies(any())
        }
        assertNotNull(movies)
        assertEquals(movieResponses.results?.size?.toLong(), movies.size.toLong())
    }

    @Test
    fun testGetMovieById() {
        runBlocking {
            doAnswer { invocation ->
                (invocation.arguments[1] as RemoteDataSource.LoadMovieByIdCallback).onMovieReceived(
                    movieResponse
                )
            }.`when`(remoteDataSource).getMovieById(eq(movieId), any())
        }
        val movie = LiveDataTestUtils.getValue(repository.getMovieById(movieId))
        runBlocking {
            verify(remoteDataSource).getMovieById(eq(movieId), any())
        }
        assertNotNull(movie)
        assertEquals(movieResponse.id, movie.id)
    }

    @Test
    fun testGetAllTVShow() {
        runBlocking {
            doAnswer { invocation ->
                (invocation.arguments[0] as RemoteDataSource.LoadTVShowsCallback).onTVShowsReceived(
                    tvShowResponses
                )
            }.`when`(remoteDataSource).getAllTVShows(any())
        }

        val tvShows = LiveDataTestUtils.getValue(repository.getAllTVShow())

        runBlocking {
            verify(remoteDataSource).getAllTVShows(any())
        }

        assertNotNull(tvShows)
        assertEquals(tvShowResponses.results?.size?.toLong(), tvShows.size.toLong())
    }

    @Test
    fun testGetTVShowById() {
        runBlocking {
            doAnswer { invocation ->
                (invocation.arguments[1] as RemoteDataSource.LoadTVShowByIdCallback).onTVShowReceived(
                    tvShowResponse
                )
            }.`when`(remoteDataSource).getTVShowById(eq(tvShowId), any())
        }
        val tvShow = LiveDataTestUtils.getValue(repository.getTVShowById(tvShowId))
        runBlocking {
            verify(remoteDataSource).getTVShowById(eq(tvShowId), any())
        }
        assertNotNull(tvShow)
        assertEquals(tvShowResponse.id, tvShow.id)
    }

    @Test
    fun  getAllFavoriteMovie(){
        `when`(localDataSource.getAllFavoriteMovie()).thenReturn(movieDataSourceFactory)
        repository.getAllFavoriteMovie()

        val movies = PagedListUtil.mockPagedList(Data.getMoviesData())
        verify(localDataSource).getAllFavoriteMovie()
        assertNotNull(movies)
        assertEquals(dummyMovies.size.toLong(), movies.size.toLong())
    }

    @Test
    fun getAllFavoriteTVShow(){
        `when`(localDataSource.getAllFavoriteTVShow()).thenReturn(tvShowDataSourceFactory)
        repository.getAllFavoriteTVShow()

        val tvShows = PagedListUtil.mockPagedList(Data.getTVShowData())
        verify(localDataSource).getAllFavoriteTVShow()
        assertNotNull(tvShows)
        assertEquals(dummyTVShows.size.toLong(), tvShows.size.toLong())
    }

    @Test
    fun checkMovieFavoriteStatus(){
        val movie = MutableLiveData<Movie>()
        movie.value = dummyMovies[0]
        `when`(localDataSource.checkMovieFavoriteStatus(dummyMovies[0].id)).thenReturn(movie)

        val movieEntity = LiveDataTestUtils.getValue(repository.checkMovieFavoriteStatus(dummyMovies[0].id))
        verify(localDataSource).checkMovieFavoriteStatus(dummyMovies[0].id)
        assertNotNull(movieEntity)
        assertEquals(dummyMovies[0].id, movieEntity.id)
    }

    @Test
    fun checkTVShowFavoriteStatus(){
        val tvShow = MutableLiveData<TVShow>()
        tvShow.value = dummyTVShows[0]
        `when`(localDataSource.checkTVShowFavoriteStatus(dummyTVShows[0].id)).thenReturn(tvShow)

        val tvShowEntity = LiveDataTestUtils.getValue(repository.checkTVShowFavoriteStatus(dummyTVShows[0].id))
        verify(localDataSource).checkTVShowFavoriteStatus(dummyTVShows[0].id)
        assertNotNull(tvShowEntity)
        assertEquals(dummyTVShows[0].id, tvShowEntity.id)
    }
}
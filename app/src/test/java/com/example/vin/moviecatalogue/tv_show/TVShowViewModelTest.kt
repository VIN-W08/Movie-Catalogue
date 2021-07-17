package com.example.vin.moviecatalogue.tv_show

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import com.example.vin.moviecatalogue.data.Repository
import com.example.vin.moviecatalogue.entity.TVShow
import com.example.vin.moviecatalogue.utils.Data
import junit.framework.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class TVShowViewModelTest {

    private lateinit var viewModel: TVShowViewModel

    @get: Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var repository: Repository

    @Mock
    private lateinit var observerTVShows: Observer<ArrayList<TVShow>>

    @Mock
    private lateinit var observerTVShow: Observer<TVShow>

    @Mock
    private lateinit var observerFavoriteTVShow: Observer<PagedList<TVShow>>

    @Mock
    private lateinit var tvShowPagedList: PagedList<TVShow>

    @Before
    fun setUp() {
        viewModel = TVShowViewModel(repository)
    }

    @Test
    fun testGetTVShows() {
        val dummyTVShows = Data.getTVShowData()
        val tvShows = MutableLiveData<ArrayList<TVShow>>()
        tvShows.value = dummyTVShows

        Mockito.`when`(repository.getAllTVShow()).thenReturn(tvShows)
        val tvShowEntities = viewModel.getTVShows().value
        Mockito.verify(repository).getAllTVShow()
        Assert.assertNotNull(tvShowEntities)
        Assert.assertEquals(20, tvShowEntities?.size)

        viewModel.getTVShows().observeForever(observerTVShows)
        Mockito.verify(observerTVShows).onChanged(dummyTVShows)
    }

    @Test
    fun testGetSelectedTVShow() {
        val dummyTVShowSelected = Data.getTVShowData()[0]
        val tvShowId = dummyTVShowSelected.id
        val tvShow = MutableLiveData<TVShow>()
        tvShow.value = dummyTVShowSelected
        viewModel.setSelectedTVShowId(tvShowId as Int)

        Mockito.`when`(repository.getTVShowById(tvShowId)).thenReturn(tvShow)
        val tvShowEntity = viewModel.getSelectedTVShow()
        Mockito.verify(repository).getTVShowById(tvShowId)
        Assert.assertNotNull(tvShowEntity)
        Assert.assertEquals(tvShow.value?.id, tvShowEntity.value?.id)
        Assert.assertEquals(tvShow.value?.title, tvShowEntity.value?.title)
        Assert.assertEquals(tvShow.value?.poster, tvShowEntity.value?.poster)
        Assert.assertEquals(tvShow.value?.description, tvShowEntity.value?.description)
        Assert.assertEquals(tvShow.value?.releaseDate, tvShowEntity.value?.releaseDate)

        viewModel.getSelectedTVShow().observeForever(observerTVShow)
        Mockito.verify(observerTVShow).onChanged(dummyTVShowSelected)
    }

    @Test
    fun getAllFavoriteTVShow(){
        val dummyTVShows = tvShowPagedList
        Mockito.`when`(dummyTVShows.size).thenReturn(7)
        val tvShow = MutableLiveData<PagedList<TVShow>>()
        tvShow.value = dummyTVShows
        Mockito.`when`(repository.getAllFavoriteTVShow()).thenReturn(tvShow)
        val tvShowEntities = viewModel.getAllFavoriteTVShow().value
        Mockito.verify(repository).getAllFavoriteTVShow()
        Assert.assertNotNull(tvShowEntities)
        Assert.assertEquals(7, tvShowEntities?.size)

        viewModel.getAllFavoriteTVShow().observeForever(observerFavoriteTVShow)
        Mockito.verify(observerFavoriteTVShow).onChanged(dummyTVShows)
    }

    @Test
    fun checkedFavoriteTVShowStatus(){
        val dummyTVShowSelected = Data.getTVShowData()[0]
        val tvShowId = dummyTVShowSelected.id
        val tvShow = MutableLiveData<TVShow>()
        tvShow.value = dummyTVShowSelected

        Mockito.`when`(repository.checkTVShowFavoriteStatus(tvShowId)).thenReturn(tvShow)
        val tvShowEntity = viewModel.checkTVShowFavoriteStatus(tvShowId)
        Mockito.verify(repository).checkTVShowFavoriteStatus(tvShowId)
        Assert.assertNotNull(tvShowEntity)
        Assert.assertEquals(tvShow.value?.id, tvShowEntity?.value?.id)
        Assert.assertEquals(tvShow.value?.title, tvShowEntity?.value?.title)
        Assert.assertEquals(tvShow.value?.poster, tvShowEntity?.value?.poster)
        Assert.assertEquals(tvShow.value?.description, tvShowEntity?.value?.description)
        Assert.assertEquals(tvShow.value?.releaseDate, tvShowEntity?.value?.releaseDate)

        viewModel.checkTVShowFavoriteStatus(tvShowId)?.observeForever(observerTVShow)
        Mockito.verify(observerTVShow).onChanged(dummyTVShowSelected)
    }
}
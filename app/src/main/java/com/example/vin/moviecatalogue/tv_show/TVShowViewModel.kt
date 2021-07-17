package com.example.vin.moviecatalogue.tv_show

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.vin.moviecatalogue.data.Repository
import com.example.vin.moviecatalogue.entity.Movie
import com.example.vin.moviecatalogue.entity.TVShow

class TVShowViewModel(private val repository: Repository): ViewModel() {
    private val tvShowId = MutableLiveData<Int?>()

    fun getTVShows(): LiveData<ArrayList<TVShow>> = repository.getAllTVShow()

    fun setSelectedTVShowId(id: Int){
        tvShowId.value = id
    }
    fun getSelectedTVShow(): LiveData<TVShow> = repository.getTVShowById(tvShowId.value)

    fun getAllFavoriteTVShow(): LiveData<PagedList<TVShow>> = repository.getAllFavoriteTVShow()

    fun insertFavoriteTVShow(tvShow: TVShow) = repository.insertFavoriteTVShow(tvShow)

    fun deleteFavoriteTVShow(tvShow: TVShow) = repository.deleteFavoriteTVShow(tvShow)

    fun checkTVShowFavoriteStatus(id: Int?): LiveData<TVShow>? = repository.checkTVShowFavoriteStatus(id)
}
package com.example.vin.moviecatalogue

import android.graphics.text.LineBreaker.JUSTIFICATION_MODE_INTER_WORD
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.vin.moviecatalogue.databinding.ActivityMovieDetailBinding
import com.example.vin.moviecatalogue.entity.Movie
import com.example.vin.moviecatalogue.entity.TVShow
import com.example.vin.moviecatalogue.movie.MovieViewModel
import com.example.vin.moviecatalogue.tv_show.TVShowViewModel
import com.example.vin.moviecatalogue.viewmodelfactory.ViewModelFactory

class MovieDetailActivity : AppCompatActivity(){
    private lateinit var binding: ActivityMovieDetailBinding
    private lateinit var factory: ViewModelFactory
    private lateinit var movieViewModel: MovieViewModel
    private lateinit var tvShowViewModel: TVShowViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val type = intent.getStringExtra("type")
        val id = intent.getIntExtra("id", 0)

        factory = ViewModelFactory.getInstance(this)
        movieViewModel = ViewModelProvider(this, factory)[MovieViewModel::class.java]
        tvShowViewModel = ViewModelProvider(this, factory)[TVShowViewModel::class.java]

        when(type){
            "movie" -> {
                movieViewModel.setSelectedMovieId(id)
                movieViewModel.getSelectedMovie().observe(this, {
                    binding.progressBar.visibility = View.GONE
                    populateMovieData(it)
                })
            }
            "tv show" -> {
                tvShowViewModel.setSelectedTVShowId(id)
                tvShowViewModel.getSelectedTVShow().observe(this, {
                    binding.progressBar.visibility = View.GONE
                    populateTVShowData(it)
                })
            }
        }
    }

    private fun populateMovieData(movie: Movie){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            binding.descTV.justificationMode = JUSTIFICATION_MODE_INTER_WORD
        }
        binding.titleTV.text = movie.title
        binding.releaseDataTV.text = movie.releaseDate
        binding.descTV.text = movie.description
        Glide.with(this)
            .load(movie.poster)
            .apply(RequestOptions().override(150, 150))
            .into(binding.posterIV)

        movieViewModel.checkMovieFavoriteStatus(movie.id)?.observe(this,{
            val isFavorite = it!=null
            updateFavoriteStatusUI(binding.favoriteImage, isFavorite)
            binding.favoriteImage.setOnClickListener{ view ->
                if(isFavorite){
                    movieViewModel.deleteFavoriteMovie(movie)
                    updateFavoriteStatusUI(view, isFavorite)
                }else{
                    movieViewModel.insertFavoriteMovie(movie)
                    updateFavoriteStatusUI(view, isFavorite)
                }
            }
        })
    }

    private fun populateTVShowData(tvShow: TVShow){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            binding.descTV.justificationMode = JUSTIFICATION_MODE_INTER_WORD
        }
        binding.titleTV.text = tvShow.title
        binding.releaseDataTV.text = tvShow.releaseDate
        binding.descTV.text = tvShow.description
        Glide.with(this)
            .load(tvShow.poster)
            .apply(RequestOptions().override(150, 150))
            .into(binding.posterIV)

        tvShowViewModel.checkTVShowFavoriteStatus(tvShow.id)?.observe(this,{
            val isFavorite = it!=null
            updateFavoriteStatusUI(binding.favoriteImage, isFavorite)
            binding.favoriteImage.setOnClickListener{ view ->
                if(isFavorite){
                    tvShowViewModel.deleteFavoriteTVShow(tvShow)
                    updateFavoriteStatusUI(view, isFavorite)
                }else{
                    tvShowViewModel.insertFavoriteTVShow(tvShow)
                    updateFavoriteStatusUI(view, isFavorite)
                }
            }
        })
    }

    private fun updateFavoriteStatusUI(view: View, isFavorite: Boolean){
       val drawable = if(isFavorite) R.drawable.ic_baseline_favorite_24
        else R.drawable.ic_baseline_favorite_border_24

        Glide.with(this)
            .load(drawable)
            .apply(RequestOptions().override(100,100))
            .into(view as ImageView)
    }
}
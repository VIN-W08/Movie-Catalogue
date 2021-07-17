package com.example.vin.moviecatalogue

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.vin.moviecatalogue.databinding.ItemMovieBinding
import com.example.vin.moviecatalogue.entity.Movie

class MoviePagedListAdapter(private val activity: Activity): PagedListAdapter<Movie, MoviePagedListAdapter.MovieViewHolder>(DIFF_CALLBACK) {

    inner class MovieViewHolder(private val binding: ItemMovieBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(movie: Movie){
            binding.apply {
                titleTV.text = movie.title
                descTV.text = movie.description
                Glide.with(binding.root.context)
                    .load(movie.poster)
                    .apply(RequestOptions().override(150, 150))
                    .into(binding.posterIV)
            }
            itemView.setOnClickListener{
                val intent = Intent(activity, MovieDetailActivity::class.java)
                intent.putExtra("id", movie.id)
                intent.putExtra("type", "movie")
                activity.startActivity(intent)
            }
        }
    }

    companion object {
        private val DIFF_CALLBACK: DiffUtil.ItemCallback<Movie> = object : DiffUtil.ItemCallback<Movie>() {
            override fun areItemsTheSame(oldMovie: Movie, newMovie: Movie): Boolean {
                return oldMovie.title == newMovie.title && oldMovie.description == newMovie.description
            }
            @SuppressLint("DiffUtilEquals")
            override fun areContentsTheSame(oldMovie: Movie, newMovie: Movie): Boolean {
                return oldMovie == newMovie
            }
        }
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(getItem(position) as Movie)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding = ItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(binding)
    }
}
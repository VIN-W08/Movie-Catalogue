package com.example.vin.moviecatalogue.movie

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.vin.moviecatalogue.MovieDetailActivity
import com.example.vin.moviecatalogue.databinding.ItemMovieBinding
import com.example.vin.moviecatalogue.entity.Movie

class MovieAdapter(private val movies: ArrayList<Movie>): RecyclerView.Adapter<MovieAdapter.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(movies[position])
    }

    override fun getItemCount() = movies.size

    class ViewHolder(private val binding: ItemMovieBinding): RecyclerView.ViewHolder(binding.root) {
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
                val intent = Intent(itemView.context, MovieDetailActivity::class.java)
                intent.putExtra("id", movie.id)
                intent.putExtra("type", "movie")
                itemView.context.startActivity(intent)
            }
        }
    }
}
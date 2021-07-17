package com.example.vin.moviecatalogue.tv_show

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.vin.moviecatalogue.MovieDetailActivity
import com.example.vin.moviecatalogue.databinding.ItemMovieBinding
import com.example.vin.moviecatalogue.entity.TVShow

class TVShowAdapter(private val tvShows: ArrayList<TVShow>): RecyclerView.Adapter<TVShowAdapter.ViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(tvShows[position])
    }

    override fun getItemCount() = tvShows.size

    class ViewHolder(private val binding: ItemMovieBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(tvShow: TVShow){
            binding.titleTV.text = tvShow.title
            binding.descTV.text = tvShow.description
            Glide.with(binding.root.context)
                .load(tvShow.poster)
                .apply(RequestOptions().override(150, 150))
                .into(binding.posterIV)
            itemView.setOnClickListener{
                val intent = Intent(itemView.context, MovieDetailActivity::class.java)
                intent.putExtra("id", tvShow.id)
                intent.putExtra("type", "tv show")
                itemView.context.startActivity(intent)
            }
        }
    }
}
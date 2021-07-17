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
import com.example.vin.moviecatalogue.entity.TVShow

class TVShowPagedListAdapter (private val activity: Activity): PagedListAdapter<TVShow, TVShowPagedListAdapter.TVShowViewHolder>(DIFF_CALLBACK) {
    inner class TVShowViewHolder(private val binding: ItemMovieBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(tvShow: TVShow){
            binding.apply {
                titleTV.text = tvShow.title
                descTV.text = tvShow.description
                Glide.with(binding.root.context)
                    .load(tvShow.poster)
                    .apply(RequestOptions().override(150, 150))
                    .into(binding.posterIV)
            }
            itemView.setOnClickListener{
                val intent = Intent(activity, MovieDetailActivity::class.java)
                intent.putExtra("id", tvShow.id)
                intent.putExtra("type", "tv show")
                activity.startActivity(intent)
            }
        }
    }

    companion object {
        private val DIFF_CALLBACK: DiffUtil.ItemCallback<TVShow> = object : DiffUtil.ItemCallback<TVShow>() {
            override fun areItemsTheSame(oldTVShow: TVShow, newTVShow: TVShow): Boolean {
                return oldTVShow.title == newTVShow.title && oldTVShow.description == newTVShow.description
            }
            @SuppressLint("DiffUtilEquals")
            override fun areContentsTheSame(oldTVShow: TVShow, newTVShow: TVShow): Boolean {
                return oldTVShow == newTVShow
            }
        }
    }

    override fun onBindViewHolder(holder: TVShowViewHolder, position: Int) {
        holder.bind(getItem(position) as TVShow)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TVShowViewHolder {
        val binding = ItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TVShowViewHolder(binding)
    }
}
package com.example.vin.moviecatalogue

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.vin.moviecatalogue.databinding.FragmentFavoriteTabBinding
import com.example.vin.moviecatalogue.movie.MovieViewModel
import com.example.vin.moviecatalogue.tv_show.TVShowViewModel
import com.example.vin.moviecatalogue.viewmodelfactory.ViewModelFactory

class FavoriteTabFragment : Fragment() {
    private lateinit var binding: FragmentFavoriteTabBinding
    private lateinit var movieAdapter: MoviePagedListAdapter
    private lateinit var tvShowAdapter: TVShowPagedListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavoriteTabBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        movieAdapter = MoviePagedListAdapter(requireActivity())
        tvShowAdapter = TVShowPagedListAdapter(requireActivity())
        setRecyclerView(getTabTitle())
    }

    fun newInstance(position: Int) =
        FavoriteTabFragment().apply {
            arguments = Bundle().apply {
                putInt("position", position)
            }
        }

    private fun setRecyclerView(title: String?){
        binding.favoriteRecyclerView.layoutManager = LinearLayoutManager(context)
        val factory = ViewModelFactory.getInstance(requireContext())
        when(title){
            "Movies" -> {
                val movieViewModel = ViewModelProvider(this, factory)[MovieViewModel::class.java]
                movieViewModel.getAllFavoriteMovie().observe(viewLifecycleOwner,{
                    binding.progressBar.visibility = View.GONE
                    if(it!=null){
                        movieAdapter.submitList(it)
                    }
                    if(it.isNotEmpty()){
                        binding.apply {
                            noEntityTV.visibility = View.GONE
                            favoriteRecyclerView.adapter = movieAdapter
                        }
                    }
                    else{
                        binding.noEntityTV.visibility = View.VISIBLE
                        binding.noEntityTV.text = getString(R.string.no_movie)
                    }
                })
            }
            "TV Shows" -> {
                val tvShowViewModel = ViewModelProvider(this, factory)[TVShowViewModel::class.java]
                tvShowViewModel.getAllFavoriteTVShow().observe(viewLifecycleOwner,{
                    binding.progressBar.visibility = View.GONE
                    if(it!=null){
                        tvShowAdapter.submitList(it)
                    }
                    if(it.isNotEmpty()){
                        binding.apply {
                            noEntityTV.visibility = View.GONE
                            favoriteRecyclerView.adapter = tvShowAdapter
                        }
                    }
                    else{
                        binding.noEntityTV.visibility = View.VISIBLE
                        binding.noEntityTV.text = "No Favorite TV Show"
                    }
                })
            }
        }
    }

    private fun getTabTitle(): String?{
        return when(arguments?.getInt("position")){
            0 -> "Movies"
            1 -> "TV Shows"
            else -> null
        }
    }
}
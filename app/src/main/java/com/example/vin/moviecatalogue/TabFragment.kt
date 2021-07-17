package com.example.vin.moviecatalogue

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.vin.moviecatalogue.databinding.FragmentTabBinding
import com.example.vin.moviecatalogue.movie.MovieAdapter
import com.example.vin.moviecatalogue.movie.MovieViewModel
import com.example.vin.moviecatalogue.tv_show.TVShowAdapter
import com.example.vin.moviecatalogue.tv_show.TVShowViewModel
import com.example.vin.moviecatalogue.viewmodelfactory.ViewModelFactory

class TabFragment : Fragment() {

    private lateinit var binding: FragmentTabBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTabBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setRecyclerView(getTabTitle())
    }

    fun newInstance(position: Int) =
            TabFragment().apply {
                arguments = Bundle().apply {
                    putInt("position", position)
                }
            }

    private fun setRecyclerView(title: String?){
        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        val factory = ViewModelFactory.getInstance(requireContext())
        when(title){
            "Movies" -> {
                val movieViewModel = ViewModelProvider(this, factory)[MovieViewModel::class.java]
                movieViewModel.getMovies().observe(viewLifecycleOwner,{
                    binding.progressBar.visibility = View.GONE
                    binding.recyclerView.adapter = MovieAdapter(it)
                })
            }
            "TV Shows" -> {
                val tvShowViewModel = ViewModelProvider(this, factory)[TVShowViewModel::class.java]
                tvShowViewModel.getTVShows().observe(viewLifecycleOwner,{
                    binding.progressBar.visibility = View.GONE
                    binding.recyclerView.adapter = TVShowAdapter(it)
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
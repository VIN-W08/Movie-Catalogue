package com.example.vin.moviecatalogue

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.vin.moviecatalogue.databinding.ActivityFavoriteBinding
import com.google.android.material.tabs.TabLayoutMediator

class FavoriteActivity : AppCompatActivity() {
    companion object{
        val TAB_TITLES = intArrayOf(
            R.string.title_movie,
            R.string.title_tvShow
        )
    }

    private lateinit var binding: ActivityFavoriteBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setTabs()
    }

    private fun setTabs() {
        val sectionFavoritePagerAdapter = SectionFavoritePageAdapter(this)
        binding.viewPager.adapter = sectionFavoritePagerAdapter
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()
    }
}
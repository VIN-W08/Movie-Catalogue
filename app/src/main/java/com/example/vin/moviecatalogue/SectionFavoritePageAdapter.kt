package com.example.vin.moviecatalogue

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class SectionFavoritePageAdapter(activity: AppCompatActivity): FragmentStateAdapter(activity) {
    override fun getItemCount() = 2

    override fun createFragment(position: Int): Fragment {
        return FavoriteTabFragment().newInstance(position)
    }
}
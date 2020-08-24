package com.mmajka.notatnik.main

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.mmajka.notatnik.bin.RecycleBin
import com.mmajka.notatnik.favorites.Favorites
import com.mmajka.notatnik.home.Home

class TabLayoutAdapter(fm: FragmentManager): FragmentStatePagerAdapter(fm) {
    override fun getItem(position: Int): Fragment {
        return when(position){
            0 -> Home()
            1 -> Favorites()
            else -> return RecycleBin()
        }
    }

    override fun getCount(): Int {
        return 3
    }
}
package com.tipiz.berita.ui

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class SectionsPagerAdapter internal constructor(activity: AppCompatActivity) :
    FragmentStateAdapter(activity) {


    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        val fragment = NewsFragment()
        fragment.arguments = Bundle().apply {
            when (position) {
                0 -> {
                    putString(NewsFragment.ARG_TAB, NewsFragment.TAB_NEWS)
                }

                1 -> {
                    putString(NewsFragment.ARG_TAB, NewsFragment.TAB_BOOKMARK)
                }
            }
        }

        return fragment
    }

}
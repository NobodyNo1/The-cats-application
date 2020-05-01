package com.bekarys.tech_assignment.thecats.main

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class PagerAdapter(
    fragmentActivity: FragmentActivity,
    private val fragmentList: List<MainNavigationFragmentData>
) : FragmentStateAdapter(fragmentActivity) {

    override fun createFragment(position: Int): Fragment {
        return fragmentList[position].getFragment()
    }

    override fun getItemCount(): Int {
        return fragmentList.size
    }
}

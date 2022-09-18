package com.presentation.ui.members.adapters

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.presentation.ui.members.MemberListFragment

class TabAdapter(fragment: Fragment) : FragmentStateAdapter(fragment)  {
    val NUM_ITEMS = 2
    override fun getItemCount(): Int {
        return NUM_ITEMS
    }

    override fun createFragment(position: Int): Fragment {
        return when(position) {
            0 -> MemberListFragment(true)
            1 -> MemberListFragment()
            else -> MemberListFragment()
        }
    }
}
package com.presentation.ui.members

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.tabs.TabLayout
import com.presentation.ui.members.adapters.TabAdapter
import com.test.mmustgdsc.R
import com.test.mmustgdsc.databinding.FragmentMembersBinding
import com.test.mmustgdsc.databinding.FragmentTrackBinding

class MembersFragment : Fragment() {
    private var _binding : FragmentMembersBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMembersBinding.inflate(inflater)

        val viewPager  = binding.membersViewPager
        viewPager.adapter = TabAdapter(this)
        viewPager.currentItem = 0
        binding.membersTabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                viewPager.currentItem = tab!!.position
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })

        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
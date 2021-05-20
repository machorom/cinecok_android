package com.daou.cinecok.ui.main

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class MainVPAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity){
    private var listFragment = ArrayList<Fragment>()

    override fun getItemCount(): Int {
        return listFragment.size
    }

    override fun createFragment(position: Int): Fragment {
        return listFragment[position]
    }

    fun addFragment(fragment : Fragment) {
        listFragment.add(fragment)
        notifyItemInserted(listFragment.size-1)
    }
}
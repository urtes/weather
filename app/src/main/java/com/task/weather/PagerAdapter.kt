package com.task.weather

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.task.weather.fragments.FirstFragment
import com.task.weather.fragments.SecondFragment
import com.task.weather.fragments.ThirdFragment

class PagerAdapter (fm: FragmentManager) : FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> {
                FirstFragment()
            }
            1 -> SecondFragment()
            else -> {
                return ThirdFragment()
            }
        }
    }

    override fun getCount(): Int {
        return 3
    }

    override fun getPageTitle(position: Int): CharSequence {
        return when (position) {
            0 -> "Now"
            1 -> "Today"
            else -> {
                return "7 days"
            }
        }
    }
}
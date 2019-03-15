package com.task.weather.adapters

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.task.weather.fragments.NowFragment
import com.task.weather.fragments.HourlyFragment
import com.task.weather.fragments.ForecastFragment

class PagerAdapter(fm: FragmentManager, private val bundleFromActivity: Bundle) : FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {

        var fragment: Fragment = when (position) {
            0 -> NowFragment()
            1 -> HourlyFragment()
            else -> ForecastFragment()
        }

        val bundleToFragment = Bundle()
        bundleToFragment.putString("location", bundleFromActivity.getString("location"))
        fragment.arguments = bundleToFragment

        return fragment
    }

    override fun getCount(): Int {
        return 3
    }

    override fun getPageTitle(position: Int): CharSequence {
        return when (position) {
            0 -> "Now"
            1 -> "Hourly"
            else -> "Forecast"
        }
    }
}
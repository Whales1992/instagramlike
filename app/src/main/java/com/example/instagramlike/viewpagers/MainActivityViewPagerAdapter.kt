package com.example.instagramlike.viewpagers

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import com.example.instagramlike.fragments.*

class MainActivityViewPagerAdapter(fm: FragmentManager, NumOfTabs: Int, bundle: Bundle) : FragmentStatePagerAdapter(fm) {
    internal var mNumOfTabs: Int = NumOfTabs
    private var fragmentBundle: Bundle = bundle
    internal var fragmentManagement: FragmentManager = fm

    override fun getItem(position: Int): Fragment? {
        fragmentBundle.putInt("position", position)

        when (position) {
            0 -> {
                val a = HomeFragment()
                a.arguments = this.fragmentBundle
                return a
            }

            1 -> {
                val b = SearchFragment()
                b.arguments = this.fragmentBundle
                return b
            }

            2 -> {
                val c = AddFeedFragment()
                c.arguments = this.fragmentBundle
                return c
            }

            3 -> {
                val d = NotificationFragment()
                d.arguments = this.fragmentBundle
                return d
            }

            4 -> {
                val e = ProfileFragment()
                e.arguments = this.fragmentBundle
                return e
            }
            else -> return null
        }
    }

    override fun getCount(): Int {
        return mNumOfTabs
    }

}
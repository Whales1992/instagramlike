package com.example.instagramlike.viewpagers.homefragmentcustomlayouts

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.example.instagramlike.network.models.SingleVideo


class CustomAdapter(fm: FragmentManager?, NumOfTabs: Int, private var data: ArrayList<SingleVideo>) : FragmentPagerAdapter(fm) {
    internal var mNumOfTabs: Int = NumOfTabs

    override fun getItem(position: Int): Fragment? {
        val ret = VideoViewFragment()
        ret.preloadVideo(data[position], position)
        return ret
    }

    override fun getCount(): Int {
        return mNumOfTabs
    }
}
package com.example.instagramlike

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.instagramlike.viewpagers.MainActivityViewPagerAdapter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.home_fragment.view.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initViewPager()
        clickListners()

        /*
        * Just to set a specific page as  default, we can simply pass any of the position as required
        * */
        viewpager.setCurrentItem(0, true)
    }

    private fun clickListners() {
        home.setOnClickListener {
            viewpager.setCurrentItem(0, true)
        }

        search.setOnClickListener {
            viewpager.setCurrentItem(1, true)
        }

        add.setOnClickListener {
            viewpager.setCurrentItem(2, true)
        }

        notification.setOnClickListener {
            viewpager.setCurrentItem(3, true)
        }

        profile.setOnClickListener {
            viewpager.setCurrentItem(4, true)
        }

    }

    private fun initViewPager() {
        val bundle = Bundle()
        bundle.putString("key", "value")

        viewpager.adapter = MainActivityViewPagerAdapter(supportFragmentManager, 5, bundle)
        viewpager.setOffscreenPageLimit(5)
    }

}
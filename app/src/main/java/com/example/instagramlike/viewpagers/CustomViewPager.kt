package com.example.instagramlike.viewpagers

import android.content.Context
import android.support.v4.view.ViewPager
import android.util.AttributeSet
import android.view.MotionEvent

class CustomViewPager(context: Context, attrs: AttributeSet?) : ViewPager(context, attrs) {
    private var disable: Boolean? = false

    override fun onInterceptTouchEvent(event: MotionEvent): Boolean {
        return (!disable!!) && super.onInterceptTouchEvent(event)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        return (!disable!!) && super.onTouchEvent(event)
    }

    fun disableScroll(disable: Boolean?) {
        //When disable = true not work the scroll and when disable = false work the scroll
        this.disable = disable
    }
}
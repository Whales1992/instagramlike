package com.example.instagramlike.viewpagers.homefragmentcustomlayouts

import android.content.Context
import android.support.v4.view.ViewPager
import android.util.AttributeSet
import android.support.v4.view.ViewCompat.setTranslationY
import android.opengl.ETC1.getHeight
import android.opengl.ETC1.getWidth
import android.support.v4.view.ViewCompat.setTranslationX
import android.view.View
import android.view.MotionEvent

class VerticalViewPager(context: Context, attrs: AttributeSet) : ViewPager(context, attrs) {

    inner class PageTransformer : ViewPager.PageTransformer {

        override fun transformPage(page: View, position: Float) {

            if (position < -1) {
                // [-infinity, -1], view page is off-screen to the left

                // hide the page.
                page.setVisibility(View.INVISIBLE)

            } else if (position <= 1) {
                // [-1, 1], page is on screen

                // show the page
                page.setVisibility(View.VISIBLE)

                // get page back to the center of screen since it will get swipe horizontally by default.
                page.setTranslationX(page.getWidth() * -position)

                // set Y position to swipe in vertical direction.
                val y = position * page.getHeight()
                page.setTranslationY(y)

            } else {
                // [1, +infinity], page is off-screen to the right

                // hide the page.
                page.setVisibility(View.INVISIBLE)
            }
        }
    }

    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {

        val interceped = super.onInterceptTouchEvent(swapXY(ev))
        swapXY(ev) // swap x,y back for other touch events.
        return interceped
    }

    override fun onTouchEvent(ev: MotionEvent): Boolean {
        return super.onTouchEvent(swapXY(ev))
    }

    // swap x and y
    private fun swapXY(ev: MotionEvent): MotionEvent {
        val width = width.toFloat()
        val height = height.toFloat()

        val newX = ev.y / height * width
        val newY = ev.x / width * height

        ev.setLocation(newX, newY)

        return ev
    }
}
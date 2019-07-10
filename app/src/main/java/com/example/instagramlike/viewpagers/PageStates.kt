package com.example.instagramlike.viewpagers

import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.VideoView
import com.example.instagramlike.network.models.SingleVideo

public interface PageStates {
    fun pageStateChange(position : Int)
}
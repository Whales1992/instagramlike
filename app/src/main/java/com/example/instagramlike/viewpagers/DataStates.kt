package com.example.instagramlike.viewpagers

import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.VideoView
import com.example.instagramlike.network.models.SingleVideo

public interface DataStates {
    fun finishFetchingFeedFromServer(result : String)
    fun loadVideo(
        result: SingleVideo,
        videoview: VideoView?,
        thumb: ImageView?,
        loading: ProgressBar?,
        media_controller: FrameLayout?
    )
}
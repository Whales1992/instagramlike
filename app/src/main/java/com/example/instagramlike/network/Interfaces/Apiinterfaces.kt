package com.example.instagramlike.network.Interfaces

import retrofit2.Call
import retrofit2.http.GET

interface Apiinterfaces {
    @GET("v1/posts/newsfeed")
    fun getVideos(): Call<String>
}
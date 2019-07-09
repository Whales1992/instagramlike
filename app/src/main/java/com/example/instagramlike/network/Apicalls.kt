package com.example.instagramlike.network

import com.example.instagramlike.network.Interfaces.Apiinterfaces
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

    /*
    * Note: @param readTimeOut, writeTimeOut and connectionTimeOut are all measure in SECONDS
    * */
class Apicalls(readTimeOut: Int, writeTimeOut: Int, connectionTimeOut: Int) {
    private val baseurl = "http://35.226.14.35:8080/api/"
    internal var apiInterface: Apiinterfaces? = null

    init {
        apiInterface = Apiclient.getClient(baseurl, readTimeOut, writeTimeOut, connectionTimeOut)
            .create(Apiinterfaces::class.java)
    }

    fun getVideos(responseCallback: ResponseCallback) {
        try {
            val call: Call<String> = apiInterface?.getVideos()!!
            call.enqueue(object : Callback<String> {
                override fun onResponse(call: Call<String>, response: Response<String>) {
                    if (response.body() != null)
                        responseCallback.onSuccess(response.body())
                    else
                        responseCallback.onFailure("An Error Occurred, Try again.")
                }

                override fun onFailure(call: Call<String>, t: Throwable) {
                    responseCallback.onFailure(t.message + "")
                }
            })
        } catch (ex: Exception) {
            responseCallback.onFailure("An error occurred, try again.")
        }
    }
}
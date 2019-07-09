package com.example.instagramlike.network

import android.content.Context
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit

class Apiclient {

    /*
   * This function does the initialization of Retrofit and most
    * importantly take 5 parameters, @param baseUrl is self explanatory,
    * @param readTime is to set the how long a request should stay reading a data from the
    * end-point, @param writeTime is to set the how long a request should stay writting
    * a data from the end-point, and finally @param timeOut is to set how long a request
    * should take before returning a connection time out error when the response takes too
    * long and this is most likely caused by slow internet connect or related.
    * Note: This documentation might not be entirely correct base on maybe any changes
    * so please update as required.
    * */

    companion object{
        fun getClient(
            baseURL: String,
            readTimeOut: Int,
            writeTimeOut: Int,
            connectTimeOut: Int
        ): Retrofit {

            val client = OkHttpClient.Builder()
                .connectTimeout(connectTimeOut.toLong(), TimeUnit.SECONDS)
                .readTimeout(readTimeOut.toLong(), TimeUnit.SECONDS)
                .writeTimeout(writeTimeOut.toLong(), TimeUnit.SECONDS)
                .build()

            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY

            return Retrofit.Builder()
                .baseUrl(baseURL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
        }
    }
}
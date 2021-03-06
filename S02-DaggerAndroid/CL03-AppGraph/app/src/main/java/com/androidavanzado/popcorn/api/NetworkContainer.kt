package com.androidavanzado.popcorn.api

import com.androidavanzado.popcorn.common.Constants
import com.androidavanzado.popcorn.common.MyApp
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class NetworkContainer {

        private val theMovieDBInterceptor = TheMovieDBInterceptor()

        private val okHttpClient: OkHttpClient = with(OkHttpClient.Builder()) {
            addInterceptor(theMovieDBInterceptor)
            connectTimeout(Constants.TIMEOUT_INMILIS, TimeUnit.MILLISECONDS)
            build()
        }

        val theMovieDBService: TheMovieDBService = Retrofit.Builder()
            .baseUrl(Constants.TMDBAPI_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
            .create(TheMovieDBService::class.java)
}
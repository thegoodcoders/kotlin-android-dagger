package com.androidavanzado.popcorn.api

import com.androidavanzado.popcorn.common.Constants
import com.androidavanzado.popcorn.common.MyApp
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

@Module
class NetworkModule {

    @Provides
    fun provideOkHttpClient(theMovieDBInterceptor: TheMovieDBInterceptor): OkHttpClient {
        return with(OkHttpClient.Builder()) {
            addInterceptor(theMovieDBInterceptor)
            connectTimeout(Constants.TIMEOUT_INMILIS, TimeUnit.MILLISECONDS)
            build()
        }
    }

    @Provides
    fun provideTheMovieDBService(okHttpClient: OkHttpClient): TheMovieDBService {
        return Retrofit.Builder()
            .baseUrl(Constants.TMDBAPI_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
            .create(TheMovieDBService::class.java)
    }

}
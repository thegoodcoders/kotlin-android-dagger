package com.androidavanzado.popcorn.common

import android.app.Application
import com.androidavanzado.popcorn.api.NetworkModule

class MyApp: Application() {

    companion object {
        lateinit var instance: MyApp
        val networkContainer = NetworkModule()
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}
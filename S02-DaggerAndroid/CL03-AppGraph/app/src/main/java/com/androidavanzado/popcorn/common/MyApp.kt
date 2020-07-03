package com.androidavanzado.popcorn.common

import android.app.Application
import com.androidavanzado.popcorn.api.NetworkContainer

class MyApp: Application() {

    companion object {
        lateinit var instance: MyApp
        val networkContainer = NetworkContainer()
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}
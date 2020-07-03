package com.androidavanzado.popcorn.common

import android.app.Application
import com.androidavanzado.popcorn.api.NetworkModule
import com.androidavanzado.popcorn.di.ApplicationComponent
import com.androidavanzado.popcorn.di.DaggerApplicationComponent

class MyApp: Application() {
    val appComponent: ApplicationComponent = DaggerApplicationComponent.create()

    companion object {
        lateinit var instance: MyApp
        val networkContainer = NetworkModule()
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}
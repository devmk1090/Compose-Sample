package com.devkproject.accessibility

import android.app.Application
import com.devkproject.accessibility.data.AppContainer
import com.devkproject.accessibility.data.AppContainerImpl

class JetnewsApplication : Application() {

    // AppContainer instance used by the rest of classes to obtain dependencies
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = AppContainerImpl(this)
    }
}
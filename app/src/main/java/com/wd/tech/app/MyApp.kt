package com.wd.tech.app

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.facebook.drawee.backends.pipeline.Fresco

class MyApp : Application() {
    private var context: Context? = null
    override fun onCreate() {
        super.onCreate()
        Fresco.initialize(this)
    }

    //拦截器
    fun getmContext(): Context? {
        return context
    }
}
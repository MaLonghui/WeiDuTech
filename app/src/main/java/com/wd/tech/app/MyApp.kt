package com.wd.tech.app

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.facebook.drawee.backends.pipeline.Fresco

class MyApp : Application() {
    private var sp: SharedPreferences? = null
    private var context: Context? = null
    override fun onCreate() {
        super.onCreate()

        val sp = getSharedPreferences("config", Context.MODE_PRIVATE)
        context = applicationContext
        val edit = sp.edit()
        val a = sp.getBoolean("auto_login", false)
        if (!a) {
            edit.putString("userId", "")
            edit.putString("sessionId", "")
        }
        edit.commit()
        Fresco.initialize(this)

    }

    //拦截器
    fun getmContext(): Context? {
        return context
    }
}
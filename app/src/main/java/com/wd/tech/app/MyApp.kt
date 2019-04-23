package com.wd.tech.app

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.facebook.drawee.backends.pipeline.Fresco
import com.huburt.library.ImagePicker
import com.tencent.mm.opensdk.openapi.IWXAPI
import com.tencent.mm.opensdk.openapi.WXAPIFactory
import com.umeng.analytics.MobclickAgent
import com.umeng.commonsdk.UMConfigure
import com.wd.tech.utils.GlideImageLoader

class MyApp : Application() {

    private var context: Context? = null
    var APP_ID = "wx4c96b6b8da494224"
    var api: IWXAPI ? = null
    override fun onCreate() {
        super.onCreate()
        Fresco.initialize(this)
        context = applicationContext
        api = WXAPIFactory.createWXAPI(this, APP_ID, true)
        api!!.registerApp(APP_ID)
        //U盟
        UMConfigure.init(this, UMConfigure.DEVICE_TYPE_PHONE, null)
        MobclickAgent.setScenarioType(this, MobclickAgent.EScenarioType.E_UM_NORMAL)

        ImagePicker.init(GlideImageLoader())
        //保存为自定义默认
        ImagePicker.limit(12).isCrop(true).saveAsDefault()
    }


    //拦截器
    fun getmContext(): Context? {
        return context
    }
}
package com.wd.tech.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import com.wd.tech.activity.SendMesageActivity
import cn.jpush.im.android.api.JMessageClient
import cn.jpush.im.android.api.event.MessageEvent
import cn.jpush.im.android.api.event.NotificationClickEvent


class GlobalEventListener {
    var MainContext: Context? = null

    var JG_details: SendMesageActivity? = null

    fun GlobalEventListener(context: Context) {
        MainContext = context
        JMessageClient.registerEventReceiver(this)
    }
    fun setJG(activity:Activity,islist:Boolean){
        if (islist){
            JG_details =  activity as SendMesageActivity
        }
    }

   fun onEventMainThread(event: MessageEvent){
       if (JG_details != null) {
           //JG_details!!.init()
       }
   }

}

package com.wd.tech.utils
import android.app.Activity
import android.app.ActivityOptions
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.support.v4.content.ContextCompat.startActivity
import com.wd.tech.activity.NetActivity
import com.wd.tech.app.MyApp
import java.util.Map

open class JumpActivityUtils {

    companion object {
        var handler: Handler = Handler()
        fun skipAnotherActivity(activity: Activity, clazz: Class<out Activity>) {
            if (NetWorkUtils.isNetworkAvailable(activity)) {
                val dialogprogress: DialogUtils = DialogUtils(activity)
                dialogprogress.showProgressDialog(true, "正在加载数据……")
                activity.startActivity(Intent(activity, clazz),ActivityOptions.makeSceneTransitionAnimation(activity).toBundle())
                        activity.finish()
                dialogprogress.dismiss()
                activity.finish()
                dialogprogress.dismiss()
                activity.finish()
                dialogprogress.dismiss()
                /*handler.postDelayed(object : Runnable {
                    override fun run() {
                      //  activity.startActivity(Intent(activity, clazz),ActivityOptions.makeSceneTransitionAnimation(activity).toBundle())
                        activity.finish()
                        dialogprogress.dismiss()
//                handler.postDelayed(object : Runnable {
//                    override fun run() {
//
//                    }
//                }, 650)*/
                        activity.finish()
                dialogprogress.dismiss()
            } else {
                activity.startActivity(Intent(activity, NetActivity::class.java),ActivityOptions.makeSceneTransitionAnimation(activity).toBundle())
                activity.finish()
            }
        }

        fun skipValueActivity(activity: Activity,
                              cls: Class<out Activity>,
                              hashMap: HashMap<String, out Any>) {
            if (NetWorkUtils.isNetworkAvailable(activity)) {
                val intent = Intent(activity, cls)
                val iterator = hashMap.entries.iterator()
                while (iterator.hasNext()) {
                    val entry = iterator
                            .next() as Map.Entry<String, Any>
                    val key = entry.key
                    val value = entry.value
                    if (value is String) {
                        intent.putExtra(key, value as String)
                    }
                    if (value is Boolean) {
                        intent.putExtra(key, value as Boolean)
                    }
                    if (value is Int) {
                        intent.putExtra(key, value as Int)
                    }
                    if (value is Float) {
                        intent.putExtra(key, value as Float)
                    }
                    if (value is Double) {
                        intent.putExtra(key, value as Double)
                    }
                }
                activity.startActivity(intent,ActivityOptions.makeSceneTransitionAnimation(activity).toBundle())
            } else {
                activity.startActivity(Intent(activity, NetActivity::class.java),ActivityOptions.makeSceneTransitionAnimation(activity).toBundle())
            }
        }

    }
}

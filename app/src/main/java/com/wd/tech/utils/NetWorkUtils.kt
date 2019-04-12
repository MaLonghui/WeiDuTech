
package com.wd.tech.utils
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.app.Dialog
import android.content.BroadcastReceiver
import android.content.ComponentName
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Handler
import android.support.v7.app.AlertDialog
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.animation.LinearInterpolator
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.PopupWindow
import android.widget.Toast



import android.content.Context.CONNECTIVITY_SERVICE
import com.wd.tech.R

class NetWorkUtils {

    /*
    * 判断是否有网络连接
    * */
    fun isNetworkConnected(context: Context?): Boolean {
        if (context != null) {
            val mConnectivityManager = context
                    .getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
            val mNetworkInfo = mConnectivityManager.activeNetworkInfo
            if (mNetworkInfo != null) {
                return mNetworkInfo.isAvailable
            }
        }
        return false
    }

    /*
    * 判断wifi是否有网
    * */
    fun isWifiConnected(context: Context?): Boolean {
        if (context != null) {
            val mConnectivityManager = context
                    .getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
            val mWiFiNetworkInfo =mConnectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI)

            if (mWiFiNetworkInfo != null) {
                return mWiFiNetworkInfo.isAvailable
            }
        }
        return false
    }

    /*
    * 判断移动数据是否有网
    * */
    fun isMobileConnected(context: Context?): Boolean {
        if (context != null) {
            val mConnectivityManager = context
                    .getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
            val mMobileNetworkInfo = mConnectivityManager
                    .getNetworkInfo(ConnectivityManager.TYPE_MOBILE)
            if (mMobileNetworkInfo != null) {
                return mMobileNetworkInfo.isAvailable
            }
        }
        return false
    }

    companion object {
        /*
    * 获取当前网络连接类的信息
    * */
        fun getConnectedType(context: Context?): Int {
            if (context != null) {
                val mConnectivityManager = context
                        .getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
                val mNetworkInfo = mConnectivityManager.activeNetworkInfo
                if (mNetworkInfo != null && mNetworkInfo.isAvailable) {
                    return mNetworkInfo.type
                }
            }
            return -1
        }

        /*
    * 安卓监控网络状态
    * */
        fun isNetworkAvailable(context: Context): Boolean {
            val connectivity = context.getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
            if (connectivity == null) {
                Log.i("NetWorkState", "Unavailabel")
                return false
            } else {
                val info = connectivity.allNetworkInfo
                if (info != null) {
                    for (i in info.indices) {
                        if (info[i].state == NetworkInfo.State.CONNECTED) {
                            Log.i("NetWorkState", "Availabel")
                            return true
                        }
                    }
                }
            }
            return false
        }

        var connectionReceiver: BroadcastReceiver = object : BroadcastReceiver() {

            private val handler = Handler()

            override fun onReceive(context: Context, intent: Intent) {
                val connectMgr = context.getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
                val mobNetInfo = connectMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)
                val wifiNetInfo = connectMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
                if (!mobNetInfo.isConnected && !wifiNetInfo.isConnected) {
                    val view = LayoutInflater.from(context).inflate(R.layout.network, null)
                    val qiuqiu = view.findViewById<ImageView>(R.id.qiuqiu)
                    val rotateAnimation = AnimationUtils.loadAnimation(context, R.anim.qiuqiu)
                    val interpolator = LinearInterpolator()
                    rotateAnimation.interpolator = interpolator

                    val dialog = AlertDialog.Builder(context).create()
                    dialog.setCancelable(false)
                    dialog.show()
                    dialog.window!!.setContentView(view)
                    qiuqiu.animation = rotateAnimation
                    handler.postDelayed({
                        dialog.cancel()
                        //Log.i(TAG, "unconnect");
                        //Toast.makeText(context, "您的网络连接已中断", Toast.LENGTH_LONG).show();
                        //弹出对话框并跳转到网络加载页面
                        val builder = AlertDialog.Builder(context)
                        builder.setTitle("网络提示").setMessage("网络未连接,是否进行网络设置?").setPositiveButton("设置") { dialog, which ->
                            var intent: Intent? = null
                            //判断手机系统的版本  即API大于10 就是3.0或以上版本
                            if (android.os.Build.VERSION.SDK_INT > 10) {
                                intent = Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS)
                            } else {
                                intent = Intent()
                                val component = ComponentName("com.android.settings", "com.android.settings.WirelessSettings")
                                intent.component = component
                                intent.action = "android.intent.action.VIEW"
                            }
                            context.startActivity(intent)
                        }.setNegativeButton("重试") { dialog, which ->
                            val noStudoInterent = NetWorkUtils()
                            if (noStudoInterent.isNetworkConnected(context)) {
                                dialog.dismiss()
                                Toast.makeText(context, "有网络", Toast.LENGTH_LONG).show()
                            } else {
                                builder.show()
                                Toast.makeText(context, "无网络", Toast.LENGTH_LONG).show()
                            }
                        }.show()
                    }, 5000)


                } else {
                    //Toast.makeText(context, "亲，您的网络连接已恢复", Toast.LENGTH_LONG).show();

                }
            }
        }
    }
}

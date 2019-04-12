package com.wd.tech.utils

import android.app.ProgressDialog
import android.content.Context
import android.os.Handler

/**
 * Created by Bowen on 2015-11-02.
 */
class DialogUtils(private val mContext: Context) {

    private val mProgressDialog: ProgressDialog
    var handler: Handler = Handler()

    init {
        mProgressDialog = ProgressDialog(mContext)
    }

    @JvmOverloads
    fun showProgressDialog(show: Boolean, message: String = "") {
        if (show) {
            mProgressDialog.setMessage(message)
            mProgressDialog.show()
        } else {
            handler.postDelayed(object : Runnable{
                override fun run() {
                    mProgressDialog.hide()
                }

            },650)

        }
    }

    fun dismiss() {
        mProgressDialog.dismiss()
    }
}

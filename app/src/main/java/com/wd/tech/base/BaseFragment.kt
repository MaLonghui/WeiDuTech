package com.wd.tech.base

import android.app.Activity
import android.content.Context
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.wd.tech.utils.NetWorkUtils
import com.wd.tech.utils.NetWorkUtils.Companion.connectionReceiver


 abstract class BaseFragment<in V : BaseContract.BaseView, P : BaseContract.BasePresenter<V>> : Fragment(){

    protected var mPresenter: P? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(getLayoutId(), container, false)
        mPresenter = initPresenter()
        mPresenter!!.attachView(this as V)
        connectionReceiver = connectionReceiver
        var intentFilter: IntentFilter = IntentFilter()
        intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION)
        activity!!.registerReceiver(connectionReceiver, intentFilter)
        if (NetWorkUtils.isNetworkAvailable(activity!!)) {
            initData()
        }

        return view
    }

    override fun onDestroy() {
        super.onDestroy()
        activity!!.unregisterReceiver(connectionReceiver)
        mPresenter!!.detachView()
    }

    protected abstract fun getLayoutId(): Int
    protected abstract fun initPresenter(): P
    protected abstract fun initData()
}

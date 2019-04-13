package com.wd.tech.base
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.wd.tech.mvp.Constanct
import com.wd.tech.utils.NetWorkUtils
import com.wd.tech.utils.NetWorkUtils.Companion.connectionReceiver


abstract class BaseActivity <in V: BaseContract.BaseView,P :BaseContract.BasePresenter<V>> : AppCompatActivity(),Constanct.View{

    protected var mPresenter: P? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutId())
        if (NetWorkUtils.isNetworkAvailable(this)){
            initData()
        }
        mPresenter = initPresenter()
        mPresenter!!.attachView(this as V)
        connectionReceiver = connectionReceiver
        var intentFilter:IntentFilter = IntentFilter()
        intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION)
        registerReceiver(connectionReceiver,intentFilter)
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(connectionReceiver)
        mPresenter!!.detachView()
    }

    protected abstract fun getLayoutId(): Int
    protected abstract fun initPresenter(): P
    protected abstract fun initData()
}
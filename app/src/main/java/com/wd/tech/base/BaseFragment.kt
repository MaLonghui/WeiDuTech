

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


open abstract class BaseFragment<in V : BaseContract.BaseView, P : BaseContract.BasePresenter<V>> : Fragment(), View.OnClickListener {

    protected var mPresenter: P? = null
    var  _context:Context? = null
    override fun getContext():Context{

        return _context!!

    }

    var activity:Activity = Activity()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(getLayoutId(), container, false)
        if (NetWorkUtils.isNetworkAvailable(_context!!)) {
            mPresenter = initPresenter()
            mPresenter!!.attachView(this as V)
            initData()
        }
        connectionReceiver = connectionReceiver
        var intentFilter: IntentFilter = IntentFilter()
        intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION)
        _context!!.registerReceiver(connectionReceiver, intentFilter)
        return view
    }

    override fun onDestroy() {
        super.onDestroy()
        _context!!.unregisterReceiver(connectionReceiver)
        mPresenter!!.detachView()
    }

    protected abstract fun getLayoutId(): Int
    protected abstract fun initPresenter(): P
    protected abstract fun initData()
}

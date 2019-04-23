package com.wd.tech.activity

import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.text.TextUtils
import com.wd.tech.R
import com.wd.tech.adapter.CollectAdapter
import com.wd.tech.api.Api
import com.wd.tech.base.BaseActivity
import com.wd.tech.bean.CollectBean
import com.wd.tech.mvp.Constanct
import com.wd.tech.mvp.Presenter
import kotlinx.android.synthetic.main.activity_collect.*

class CollectActivity : BaseActivity<Constanct.View, Constanct.Presenter>(), Constanct.View {
    var adapter: CollectAdapter? = null
    var page: Int = 1
    var count: Int = 10
    override fun getLayoutId(): Int {
        return R.layout.activity_collect
    }

    override fun initPresenter(): Constanct.Presenter {
        return Presenter()
    }

    override fun initData() {
        recyclerView.layoutManager = LinearLayoutManager(this@CollectActivity, LinearLayoutManager.VERTICAL, false)
        adapter = CollectAdapter(this)
        recyclerView.adapter = adapter
        val sp = getSharedPreferences("config", Context.MODE_PRIVATE)
        val userId = sp.getString("userId", "")
        val sessionId = sp.getString("sessionId", "")
        if (!TextUtils.isEmpty(userId)||!TextUtils.isEmpty(sessionId)) {
            val map: Map<String, String> = mapOf(Pair("userId", userId), Pair("sessionId", sessionId))
            val mapparameter: Map<String, Int> = mapOf(Pair("page", page), Pair("count", count))
            mPresenter!!.getPresenter(Api.COLLECT, map, CollectBean::class.java, mapparameter)
        }else{
            val map: Map<String, String> = mapOf()
            val mapparameter: Map<String, Int> = mapOf(Pair("page", page), Pair("count", count))
            mPresenter!!.getPresenter(Api.COLLECT, map, CollectBean::class.java, mapparameter)
        }
    }
    override fun View(any: Any) {
        if (any is CollectBean) {
            val bean: CollectBean = any
            if (bean.result != null) {
                val result = bean.result
                adapter!!.setData(result)
            }
        }
    }

}

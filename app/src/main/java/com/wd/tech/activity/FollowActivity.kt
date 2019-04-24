package com.wd.tech.activity

import android.content.Context
import android.content.SharedPreferences
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.text.TextUtils
import com.wd.tech.R
import com.wd.tech.adapter.FollowAdapter
import com.wd.tech.api.Api
import com.wd.tech.base.BaseActivity
import com.wd.tech.bean.CollectBean
import com.wd.tech.bean.FollowBean
import com.wd.tech.mvp.Constanct
import com.wd.tech.mvp.Presenter
import kotlinx.android.synthetic.main.activity_follow.*

class FollowActivity : BaseActivity<Constanct.View, Constanct.Presenter>(), Constanct.View {
    var adapter: FollowAdapter? = null
    override fun getLayoutId(): Int {
        return R.layout.activity_follow
    }

    override fun initPresenter(): Constanct.Presenter {
        return Presenter()
    }

    override fun initData() {
        recycler_follow.layoutManager = LinearLayoutManager(this@FollowActivity, LinearLayoutManager.VERTICAL, false)
        adapter = FollowAdapter(this)
        recycler_follow.adapter = adapter
        val pf: SharedPreferences = getSharedPreferences("config", Context.MODE_PRIVATE)
        val userId = pf.getString("userId", "")
        val sessionId = pf.getString("sessionId", "")
        if (!TextUtils.isEmpty(userId)||!TextUtils.isEmpty(sessionId)) {
            val map: Map<String, Any> = mapOf(Pair("userId", userId), Pair("sessionId", sessionId))
            val mappedbuy: Map<String, Int> = mapOf(Pair("page", 1), Pair("count", 20))
            mPresenter!!.getPresenter(Api.FOLLOW, map, FollowBean::class.java, mappedbuy)
        }else{
            val map: Map<String, String> = mapOf()
            val mapparameter: Map<String, Int> = mapOf(Pair("page", 1), Pair("count", 20))
            mPresenter!!.getPresenter(Api.COLLECT, map, CollectBean::class.java, mapparameter)

        }
        back_setting.setOnClickListener {
            onBackPressed()
        }
    }

    override fun View(any: Any) {
        if (any is FollowBean) {
            val bean: FollowBean = any
            if(bean.result!=null){
            val result = bean.result
            adapter!!.setData(result)
            }
        }
    }

}

package com.wd.tech.activity

import android.content.Context
import android.content.SharedPreferences
import android.support.v7.widget.LinearLayoutManager
import com.wd.tech.R
import com.wd.tech.adapter.CardAdapter
import com.wd.tech.api.Api
import com.wd.tech.base.BaseActivity
import com.wd.tech.bean.CardBean
import com.wd.tech.bean.Follow
import com.wd.tech.bean.FollowBean
import com.wd.tech.mvp.Constanct
import com.wd.tech.mvp.Presenter
import kotlinx.android.synthetic.main.activity_card.*

class CardActivity : BaseActivity<Constanct.View, Constanct.Presenter>(), Constanct.View {
    var adapter: CardAdapter? = null
    override fun getLayoutId(): Int {
        return R.layout.activity_card
    }

    override fun initPresenter(): Constanct.Presenter {
        return Presenter()
    }

    override fun initData() {
        recycler_card.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        adapter = CardAdapter(this@CardActivity)
        recycler_card.adapter = adapter
        val pf: SharedPreferences = getSharedPreferences("config", Context.MODE_PRIVATE)
        val userId = pf.getString("userId", "")
        val sessionId = pf.getString("sessionId", "")
        val map: Map<String, Any> = mapOf(Pair("userId", userId), Pair("sessionId", sessionId))
        val mappedbuy: Map<String, Int> = mapOf(Pair("page", 1), Pair("count", 20))
        mPresenter!!.getPresenter(Api.CARD, map, CardBean::class.java, mappedbuy)
        back_setting.setOnClickListener {
            finish()
        }
    }

    override fun View(any: Any) {
        if(any is CardBean){
            val bean:CardBean=any
            val result = bean.result
            adapter!!.setData(result)
        }
    }
}

package com.wd.tech.activity

import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.text.TextUtils
import android.widget.Toast
import com.wd.tech.R
import com.wd.tech.adapter.IntegralAdapter
import com.wd.tech.api.Api
import com.wd.tech.base.BaseActivity
import com.wd.tech.bean.DefiniteBean
import com.wd.tech.bean.IntegralBean
import com.wd.tech.bean.SigninTurnBean
import com.wd.tech.mvp.Constanct
import com.wd.tech.mvp.Presenter
import kotlinx.android.synthetic.main.activity_integral.*

class IntegralActivity : BaseActivity<Constanct.View, Constanct.Presenter>(), Constanct.View {
    var page: Int = 1
    var count: Int = 10
    var adapter: IntegralAdapter? = null
    override fun View(any: Any) {
        if (any is IntegralBean) {
            val bean: IntegralBean = any
            if (bean.result != null) {
                text_integral.text = bean.result.amount.toString()
            }else{
                Toast.makeText(this,"暂无数据",Toast.LENGTH_LONG).show()
            }
        }
        if (any is SigninTurnBean) {
            val bean: SigninTurnBean = any
            text_day.text = "你已连续签到${bean.result}天"
        }
        if (any is DefiniteBean) {
            val bean: DefiniteBean = any
            if (bean.result != null) {
                val result = bean.result
                adapter!!.setData(result)
            }
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_integral
    }

    override fun initPresenter(): Constanct.Presenter {
        return Presenter()
    }

    override fun initData() {
        val sp = getSharedPreferences("config", Context.MODE_PRIVATE)
        val userId = sp.getString("userId", "")
        val sessionId = sp.getString("sessionId", "")
        recycler_integral.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        adapter = IntegralAdapter(this)
        recycler_integral.adapter = adapter
        if (!TextUtils.isEmpty(userId) || !TextUtils.isEmpty(sessionId)) {
            val map: Map<String, String> = mapOf(Pair("userId", userId), Pair("sessionId", sessionId))
            val mapsum: Map<String, Int> = mapOf(Pair("page", page), Pair("count", count))
            val mapcan: Map<String, Any> = mapOf()
            mPresenter!!.getPresenter(Api.INTEGRAL, map, IntegralBean::class.java, mapcan)
            mPresenter!!.getPresenter(Api.DAYSIGN, map, SigninTurnBean::class.java, mapcan)
            mPresenter!!.getPresenter(Api.DEFINITE, map, DefiniteBean::class.java, mapsum)
        }else{
            val mapnull:Map<String ,String> = mapOf()
            val mapcan: Map<String, Any> = mapOf()
            val mapsum: Map<String, Int> = mapOf(Pair("page", page), Pair("count", count))
            mPresenter!!.getPresenter(Api.INTEGRAL, mapnull, IntegralBean::class.java, mapcan)
            mPresenter!!.getPresenter(Api.DAYSIGN, mapnull, SigninTurnBean::class.java, mapcan)
            mPresenter!!.getPresenter(Api.DEFINITE, mapnull, DefiniteBean::class.java, mapsum)
        }
    }
}

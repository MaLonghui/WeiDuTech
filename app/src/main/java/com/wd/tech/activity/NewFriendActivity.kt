package com.wd.tech.activity

import android.content.Context
import android.content.SharedPreferences
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.support.v7.widget.LinearLayoutManager
import android.widget.Toast
import com.jcodecraeer.xrecyclerview.XRecyclerView
import com.wd.tech.R
import com.wd.tech.adapter.NewFriendsAdapter
import com.wd.tech.api.Api
import com.wd.tech.base.BaseActivity
import com.wd.tech.bean.FriendNoticeBean
import com.wd.tech.bean.UserPublicBean
import com.wd.tech.mvp.Constanct
import com.wd.tech.mvp.Presenter
import kotlinx.android.synthetic.main.activity_new_friend.*

class NewFriendActivity : BaseActivity<Constanct.View, Constanct.Presenter>(), Constanct.View {
    var page = 1
    var count = 5
    var adapter: NewFriendsAdapter? = null
    override fun getLayoutId(): Int {
        return R.layout.activity_new_friend
    }

    override fun initPresenter(): Constanct.Presenter {
        return Presenter()
    }

    override fun initData() {
        val sp: SharedPreferences = getSharedPreferences("config", Context.MODE_PRIVATE)
        val userId = sp.getString("userId", "")
        val sessionId = sp.getString("sessionId", "")
        val headmap: Map<String, Any> = mapOf(Pair("userId", userId), Pair("sessionId", sessionId))
        val prams: Map<String, Any> = mapOf(Pair("page", page), Pair("count", count))
        new_friend_back.setOnClickListener {
            onBackPressed()
        }
        new_friend_xrecycler.setPullRefreshEnabled(true)
        new_friend_xrecycler.setPullRefreshEnabled(true)
        mPresenter!!.getPresenter(Api.FRIEND_NOTICE, headmap, FriendNoticeBean::class.java, prams)
        adapter = NewFriendsAdapter(this)
        new_friend_xrecycler.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        new_friend_xrecycler.adapter = adapter
        adapter!!.setRadioClickListener { flag, noticeId ->
            mPresenter!!.putPresenter(Api.FRIEND_SHENHE, headmap, UserPublicBean::class.java, mapOf(Pair("noticeId", noticeId), Pair("flag", flag)))
        }
        new_friend_xrecycler.setLoadingListener(object : XRecyclerView.LoadingListener {
            override fun onLoadMore() {
                val prams: Map<String, Any> = mapOf(Pair("page", page), Pair("count", count))
                count += 5
                Handler().postDelayed(object : Runnable {
                    override fun run() {
                        mPresenter!!.getPresenter(Api.FRIEND_NOTICE, headmap, FriendNoticeBean::class.java, prams)
                        new_friend_xrecycler.loadMoreComplete()
                    }

                }, 2000)
            }

            override fun onRefresh() {
                Handler().postDelayed(object : Runnable {
                    override fun run() {
                        mPresenter!!.getPresenter(Api.FRIEND_NOTICE, headmap, FriendNoticeBean::class.java, prams)
                        new_friend_xrecycler.refreshComplete()
                    }
                }, 2000)
            }

        })

    }

    override fun View(any: Any) {
        val sp: SharedPreferences = getSharedPreferences("config", Context.MODE_PRIVATE)
        val userId = sp.getString("userId", "")
        val sessionId = sp.getString("sessionId", "")
        val headmap: Map<String, Any> = mapOf(Pair("userId", userId), Pair("sessionId", sessionId))
        val prams: Map<String, Any> = mapOf(Pair("page", page), Pair("count", count))
        if (any is FriendNoticeBean) {
            var friendNoticeBean: FriendNoticeBean = any
            val noticeResult = friendNoticeBean.result
            adapter!!.setNoticeList(noticeResult)

        } else if (any is UserPublicBean) {
            var userPublicBean: UserPublicBean = any
            Toast.makeText(this, userPublicBean.message, Toast.LENGTH_LONG).show()
            if (userPublicBean.status.equals("0000")) {
                mPresenter!!.getPresenter(Api.FRIEND_NOTICE, headmap, FriendNoticeBean::class.java, prams)
            }
        }


    }


}

package com.wd.tech.activity

import android.content.Context
import android.content.SharedPreferences
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.support.v7.widget.LinearLayoutManager
import android.view.View.VISIBLE
import android.widget.Toast
import com.jcodecraeer.xrecyclerview.ProgressStyle
import com.jcodecraeer.xrecyclerview.XRecyclerView
import com.wd.tech.R
import com.wd.tech.adapter.FindUserPostAdapter
import com.wd.tech.api.Api
import com.wd.tech.base.BaseActivity
import com.wd.tech.bean.FindUserPostBean
import com.wd.tech.mvp.Constanct
import com.wd.tech.mvp.Presenter
import kotlinx.android.synthetic.main.activity_find_user.*

class FindUserActivity : BaseActivity<Constanct.View, Constanct.Presenter>(), Constanct.View {
    var page = 1
    var count = 5
    var adapter: FindUserPostAdapter? = null
    override fun getLayoutId(): Int {
        return R.layout.activity_find_user
    }

    override fun initPresenter(): Constanct.Presenter {
        return Presenter()
    }

    override fun initData() {
        val fromUid = intent.getIntExtra("i", 1)
        val sp: SharedPreferences = getSharedPreferences("config", Context.MODE_PRIVATE)
        val userid = sp.getString("userId", "")
        val sessionId = sp.getString("sessionId", "")
        val map: Map<String, Any> = mapOf(Pair("userId", userid), Pair("sessionId", sessionId))
        val prams: Map<String, Any> = mapOf(Pair("fromUid", fromUid), Pair("page", page), Pair("count", count))
        mPresenter!!.getPresenter(Api.FIND_USER_POST, map, FindUserPostBean::class.java, prams)
        user_xrecycler_view.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        adapter = FindUserPostAdapter(this)
        user_xrecycler_view.adapter = adapter
        user_xrecycler_view.setLoadingMoreEnabled(true)
        user_xrecycler_view.setPullRefreshEnabled(false)
        user_xrecycler_view.setRefreshProgressStyle(ProgressStyle.BallTrianglePath)
        user_xrecycler_view.setLoadingMoreProgressStyle(ProgressStyle.BallTrianglePath)

        user_xrecycler_view.setLoadingListener(object : XRecyclerView.LoadingListener {
            override fun onLoadMore() {

                Handler().postDelayed(object : Runnable {
                    override fun run() {
                        count += 5
                        val prams: Map<String, Any> = mapOf(Pair("fromUid", fromUid), Pair("page", page), Pair("count", count))
                        mPresenter!!.getPresenter(Api.FIND_USER_POST, map, FindUserPostBean::class.java, prams)
                        user_xrecycler_view.loadMoreComplete()

                    }
                }, 2000)

            }

            override fun onRefresh() {
                Handler().postDelayed(object : Runnable {
                    override fun run() {
                        val prams: Map<String, Any> = mapOf(Pair("fromUid", fromUid), Pair("page", page), Pair("count", count))
                        mPresenter!!.getPresenter(Api.FIND_USER_POST, map, FindUserPostBean::class.java, prams)
                        user_xrecycler_view.refreshComplete()
                    }
                }, 2000)

            }

        })

        user_more_img.setOnClickListener {
            user_linear_add.visibility = VISIBLE
        }
    }

    override fun View(any: Any) {
        if (any is FindUserPostBean) {
            var findUserPostBean: FindUserPostBean = any
            val userResult = findUserPostBean.result
            val headPic = userResult[0].communityUserVo.headPic
            val communityUserPostVoList = userResult[0].communityUserPostVoList
            val uri = Uri.parse(headPic)
            user_icon_back.setImageURI(uri)
            user_head.setImageURI(uri)
            user_name.text = userResult[0].communityUserVo.nickName
            user_ge.text = userResult[0].communityUserVo.signature
            adapter!!.setUserList(communityUserPostVoList)
        }

    }


}

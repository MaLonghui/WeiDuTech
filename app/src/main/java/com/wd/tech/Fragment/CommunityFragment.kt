package com.wd.tech.Fragment

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.support.v7.widget.LinearLayoutManager
import android.text.TextUtils
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Toast
import com.jcodecraeer.xrecyclerview.ProgressStyle
import com.jcodecraeer.xrecyclerview.XRecyclerView
import com.wd.tech.R
import com.wd.tech.activity.CommunityReleaseActivity
import com.wd.tech.activity.FindUserActivity
import com.wd.tech.adapter.CommunityAdapter
import com.wd.tech.api.Api
import com.wd.tech.base.BaseFragment
import com.wd.tech.bean.*
import com.wd.tech.mvp.Constanct
import com.wd.tech.mvp.Presenter
import com.wd.tech.utils.JumpActivityUtils
import kotlinx.android.synthetic.main.fragment_community.*
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

/**
 * 社区
 */
class CommunityFragment : BaseFragment<Constanct.View, Constanct.Presenter>(), Constanct.View {
    var adapter: CommunityAdapter? = null
    var page: Int = 1
    var count: Int = 5
    var cId: Int = 0
    var communityId = 0
    override fun getLayoutId(): Int {
        return R.layout.fragment_community
    }

    override fun initPresenter(): Constanct.Presenter {
        return Presenter()
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    open fun eventBusReceive(communityId: Int) {
        cId = communityId
    }

    override fun onResume() {
        super.onResume()
        val sp: SharedPreferences = activity!!.getSharedPreferences("config", Context.MODE_PRIVATE)
        val userid = sp.getString("userId", "")
        val sessionId = sp.getString("sessionId", "")
        val map: Map<String, Any> = mapOf(Pair("userId", userid), Pair("sessionId", sessionId))
        val mapone: Map<String, Any> = mapOf(Pair("page", page), Pair("count", count))
        if (userid.equals("") || sessionId.equals("")) {
            val noMap: Map<String, Any> = mapOf()
            mPresenter!!.getPresenter(Api.COMMUNITY, noMap, Community::class.java, mapone)
        } else {
            mPresenter!!.getPresenter(Api.COMMUNITY, map, Community::class.java, mapone)
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        recycler_community.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        adapter = CommunityAdapter(this.context!!)
        val sp: SharedPreferences = activity!!.getSharedPreferences("config", Context.MODE_PRIVATE)
        val userid = sp.getString("userId", "")
        val sessionId = sp.getString("sessionId", "")
        val map: Map<String, Any> = mapOf(Pair("userId", userid), Pair("sessionId", sessionId))
        val mapone: Map<String, Any> = mapOf(Pair("page", page), Pair("count", count))
        if (userid.equals("") || sessionId.equals("")) {
            val noMap: Map<String, Any> = mapOf()
            mPresenter!!.getPresenter(Api.COMMUNITY, noMap, Community::class.java, mapone)
        } else {
            mPresenter!!.getPresenter(Api.COMMUNITY, map, Community::class.java, mapone)
        }
        adapter!!.setIconClickListener { i->
            var map:HashMap<String,Any> = hashMapOf(Pair("i",i))
            JumpActivityUtils.skipValueActivity(context as Activity, FindUserActivity::class.java,map)
        }
        recycler_community.adapter = adapter
        recycler_community.setPullRefreshEnabled(true)
        recycler_community.setLoadingMoreEnabled(true)
        recycler_community.setRefreshProgressStyle(ProgressStyle.BallTrianglePath)
        recycler_community.setLoadingMoreProgressStyle(ProgressStyle.BallTrianglePath)
        recycler_community.setLoadingListener(object : XRecyclerView.LoadingListener {
            override fun onLoadMore() {
                Handler().postDelayed(object : Runnable {
                    override fun run() {
                        count += 5
                        val map: Map<String, Any> = mapOf(Pair("userId", userid), Pair("sessionId", sessionId))
                        val mapone: Map<String, Any> = mapOf(Pair("page", page), Pair("count", count))
                        if (userid.equals("") || sessionId.equals("")) {
                            val noMap: Map<String, Any> = mapOf()
                            mPresenter!!.getPresenter(Api.COMMUNITY, noMap, Community::class.java, mapone)
                        } else {
                            mPresenter!!.getPresenter(Api.COMMUNITY, map, Community::class.java, mapone)
                        }
                        recycler_community.loadMoreComplete()
                    }
                }, 2000)

            }

            override fun onRefresh() {
                Handler().postDelayed(object : Runnable {
                    override fun run() {
                        val map: Map<String, Any> = mapOf(Pair("userId", userid), Pair("sessionId", sessionId))
                        val mapone: Map<String, Any> = mapOf(Pair("page", page), Pair("count", count))
                        if (userid.equals("") || sessionId.equals("")) {
                            val noMap: Map<String, Any> = mapOf()
                            mPresenter!!.getPresenter(Api.COMMUNITY, noMap, Community::class.java, mapone)
                        } else {
                            mPresenter!!.getPresenter(Api.COMMUNITY, map, Community::class.java, mapone)
                        }
                        recycler_community.refreshComplete()
                    }
                }, 2000)

            }

        })

        adapter!!.setCommListener {
            community_relative.visibility = VISIBLE
            communityId = it
        }

        comment_send.setOnClickListener {
            val content = comment_edit.text.toString()
            if (TextUtils.isEmpty(content)) {
                Toast.makeText(context, "请输入评论内容", Toast.LENGTH_LONG).show()
            } else {
                var prams = mapOf(Pair("communityId", communityId), Pair("content", content))
                mPresenter!!.postPresenter(Api.INFO_COMMENT_ADD, map, UserPublicBean::class.java, prams)
                community_relative.visibility = GONE
                //评论任务
                val mapcantwo: Map<String, Int> = mapOf(Pair("taskId",1002))
                mPresenter!!.postPresenter(Api.ZUOTASK, map, TaskBean::class.java, mapcantwo)
            }
        }
        //发布帖子
        comment_release.setOnClickListener {
           startActivity(Intent(context as Activity,CommunityReleaseActivity::class.java))
        }


    }

    override fun initData() {


    }

    override fun onPause() {
        super.onPause()
        community_relative.visibility = GONE
    }

    override fun View(any: Any) {
        val sp: SharedPreferences = activity!!.getSharedPreferences("config", Context.MODE_PRIVATE)
        val userid = sp.getString("userId", "")
        val sessionId = sp.getString("sessionId", "")
        if (any is Community) {
            var community = any
            var result = community.result
            adapter!!.setResult(result)
            adapter!!.setPriseListener { id, i ->
                if (userid.equals("") || sessionId.equals("")) {
                    Toast.makeText(context, "还没有登录哦", Toast.LENGTH_LONG).show()
                } else {
                    val map: Map<String, Any> = mapOf(Pair("userId", userid), Pair("sessionId", sessionId))
                    val mapone: Map<String, Any> = mapOf(Pair("communityId", id))
                    if (result[i].whetherGreat == 2) {
                        //点赞
                        mPresenter!!.postPresenter(Api.GIVEALIKE, map, UserPublicBean::class.java, mapone)
                    } else if (result[i].whetherGreat == 1) {
                        //取消点赞
                        mPresenter!!.deletePresenter(Api.GIVEDELETE, map, UserPublicBean::class.java, mapone)
                    }
                }
            }

        } else if (any is UserPublicBean) {
            var publicBean: UserPublicBean = any
            Toast.makeText(context, publicBean.message, Toast.LENGTH_LONG).show()
            if (publicBean.status.equals("0000")) {
                val map: Map<String, Any> = mapOf(Pair("userId", userid), Pair("sessionId", sessionId))
                val mapone: Map<String, Int> = mapOf(Pair("page", page), Pair("count", count))
                mPresenter!!.getPresenter(Api.COMMUNITY, map, Community::class.java, mapone)
            }
        }
    }

}

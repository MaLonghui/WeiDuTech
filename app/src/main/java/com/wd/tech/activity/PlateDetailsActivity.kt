package com.wd.tech.activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.Toast
import com.jcodecraeer.xrecyclerview.XRecyclerView
import com.wd.tech.R
import com.wd.tech.adapter.InfoItemAdapter
import com.wd.tech.adapter.InformationAdapter
import com.wd.tech.api.Api
import com.wd.tech.base.BaseActivity
import com.wd.tech.bean.InfoBean
import com.wd.tech.bean.InfoResult
import com.wd.tech.mvp.Constanct
import com.wd.tech.mvp.Presenter
import com.wd.tech.utils.JumpActivityUtils
import kotlinx.android.synthetic.main.activity_plate_details.*

class PlateDetailsActivity : BaseActivity<Constanct.View, Constanct.Presenter>(), Constanct.View {
    var plateId = 0
    var page = 1
    var count = 5
    private var isRefresh = true
    var allList: MutableList<InfoResult> = ArrayList()
    var infoitemAdapter: InfoItemAdapter? = null

    override fun getLayoutId(): Int {
        return R.layout.activity_plate_details
    }

    override fun initPresenter(): Constanct.Presenter {
        return Presenter()
    }

    override fun initData() {
        val id = intent.getIntExtra("id", 1)
        plateId = id
        val title = intent.getStringExtra("title")
        var sp = getSharedPreferences("config", Context.MODE_PRIVATE)
        var userId = sp.getString("userId", "")
        var sessionId = sp.getString("sessionId", "")
        val nHeadMap: Map<String, Any> = mapOf()
        var sHeadMap = mapOf(Pair("userId", userId), Pair("sessionId", sessionId))
        var infoPrams = mapOf(Pair("plateId", plateId), Pair("page", page), Pair("count", count))
        if (userId.equals("") || sessionId.equals("")) {
            mPresenter!!.getPresenter(Api.TECH_INFOR, nHeadMap, InfoBean::class.java, infoPrams)
        } else {
            mPresenter!!.getPresenter(Api.TECH_INFOR, sHeadMap, InfoBean::class.java, infoPrams)
        }
        plate_details_title.text = title
        plate_details_search.setOnClickListener {
            JumpActivityUtils.skipAnotherActivity(this, SearchActivity::class.java)
        }
        plate_details_back.setOnClickListener {
            onBackPressed()
        }
        plate_xrecycler_view.setLoadingMoreEnabled(true)
        plate_xrecycler_view.setPullRefreshEnabled(true)
        plate_xrecycler_view.setLoadingListener(object : XRecyclerView.LoadingListener {
            override fun onLoadMore() {
                Handler().postDelayed(object : Runnable {
                    override fun run() {
                        page++
                        isRefresh = false
                        var sHeadMap = mapOf(Pair("userId", userId), Pair("sessionId", sessionId))
                        var infoPrams = mapOf(Pair("plateId", plateId), Pair("page", page), Pair("count", count))
                        if (userId.equals("") || sessionId.equals("")) {
                            mPresenter!!.getPresenter(Api.TECH_INFOR, nHeadMap, InfoBean::class.java, infoPrams)
                        } else {
                            mPresenter!!.getPresenter(Api.TECH_INFOR, sHeadMap, InfoBean::class.java, infoPrams)
                        }
                        plate_xrecycler_view.loadMoreComplete()
                    }
                }, 2500)
            }

            override fun onRefresh() {
                Handler().postDelayed(object : Runnable {
                    override fun run() {

                        isRefresh = true
                        var sHeadMap = mapOf(Pair("userId", userId), Pair("sessionId", sessionId))
                        var infoPrams = mapOf(Pair("plateId", plateId), Pair("page", page), Pair("count", count))
                        if (userId.equals("") || sessionId.equals("")) {
                            mPresenter!!.getPresenter(Api.TECH_INFOR, nHeadMap, InfoBean::class.java, infoPrams)
                        } else {
                            mPresenter!!.getPresenter(Api.TECH_INFOR, sHeadMap, InfoBean::class.java, infoPrams)
                        }
                        plate_xrecycler_view.refreshComplete()
                    }
                }, 2500)
            }

        })

        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        plate_xrecycler_view.layoutManager = layoutManager
        infoitemAdapter = InfoItemAdapter(this)
        if (!infoitemAdapter!!.hasObservers()) {
            plate_xrecycler_view.adapter = infoitemAdapter
        } else {
            infoitemAdapter!!.notifyDataSetChanged()
        }


    }

    override fun View(any: Any) {
        if (any != null) {
            var infoBean = any as InfoBean
            var infoList = infoBean.result
            if (infoList.size > 0) {
                allList.addAll(infoList)
            } else {
                isRefresh = true
                Toast.makeText(this, "没有更多了", Toast.LENGTH_LONG).show()
            }

            infoitemAdapter!!.setInfoItemResult(allList)
            if (!infoitemAdapter!!.hasObservers()) {
                if (isRefresh) {
                    plate_xrecycler_view.adapter = infoitemAdapter
                    infoitemAdapter!!.refresh(allList)
                    plate_xrecycler_view.refreshComplete()
                } else {
                    if (infoitemAdapter != null) {
                        infoitemAdapter!!.loadMore(allList)
                        plate_xrecycler_view.loadMoreComplete()
                    }
                }
            } else {
                infoitemAdapter!!.notifyDataSetChanged()
            }


            infoitemAdapter!!.setItemClickListener {
                var prams: HashMap<String, Any> = hashMapOf(Pair("id", it))
                JumpActivityUtils.skipValueActivity(this, InfoDetailsActivity::class.java, prams)
            }
        }
    }


}

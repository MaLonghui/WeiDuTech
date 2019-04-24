package com.wd.tech.activity

import android.content.Context
import android.os.Handler
import android.support.v7.widget.LinearLayoutManager
import android.widget.Toast
import com.jcodecraeer.xrecyclerview.XRecyclerView
import com.wd.tech.R
import com.wd.tech.adapter.InfoItemAdapter
import com.wd.tech.api.Api
import com.wd.tech.base.BaseActivity
import com.wd.tech.bean.InfoBean
import com.wd.tech.bean.UserPublicBean
import com.wd.tech.mvp.Constanct
import com.wd.tech.mvp.Presenter
import com.wd.tech.utils.JumpActivityUtils
import kotlinx.android.synthetic.main.activity_plate_details.*

class PlateDetailsActivity : BaseActivity<Constanct.View, Constanct.Presenter>(), Constanct.View {
    var plateId = 0
    var page = 1
    var count = 5
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
                        count += 5
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
//收藏
        infoitemAdapter!!.setCollectListener { id, collect ->
            var prams = mapOf(Pair("infoId", id))
            if (collect == 2) {
                mPresenter!!.postPresenter(Api.INFO_COLLECT, sHeadMap, UserPublicBean::class.java, prams)
            } else if (collect == 1) {
                mPresenter!!.deletePresenter(Api.INFO_CANCEl_COLLECT, sHeadMap, UserPublicBean::class.java, prams)
            }
        }

    }

    override fun View(any: Any) {
        if (any != null) {
            if (any is InfoBean) {
                var infoBean = any as InfoBean
                var infoList = infoBean.result
                infoitemAdapter!!.setInfoItemResult(infoList)
                infoitemAdapter!!.setItemClickListener {
                    var prams: HashMap<String, Any> = hashMapOf(Pair("id", it))
                    JumpActivityUtils.skipValueActivity(this, InfoDetailsActivity::class.java, prams)
                }
            } else if (any is UserPublicBean) {
                var userPublicBean: UserPublicBean = any
                Toast.makeText(this, userPublicBean.message, Toast.LENGTH_LONG).show()
                var sp = getSharedPreferences("config", Context.MODE_PRIVATE)
                var userId = sp.getString("userId", "")
                var sessionId = sp.getString("sessionId", "")
                var sHeadMap = mapOf(Pair("userId", userId), Pair("sessionId", sessionId))
                var infoPrams = mapOf(Pair("plateId", plateId), Pair("page", page), Pair("count", count))
                mPresenter!!.getPresenter(Api.TECH_INFOR, sHeadMap, InfoBean::class.java, infoPrams)
            }
        }
    }
}

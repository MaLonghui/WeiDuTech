package com.wd.tech.Fragment

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.support.v4.content.ContextCompat
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import com.jcodecraeer.xrecyclerview.XRecyclerView
import com.wd.tech.R
import com.wd.tech.adapter.InformationAdapter
import com.wd.tech.api.Api
import com.wd.tech.base.BaseFragment
import com.wd.tech.bean.BannerBean
import com.wd.tech.bean.InfoBean
import com.wd.tech.mvp.Constanct
import com.wd.tech.mvp.Presenter
import kotlinx.android.synthetic.main.fragment_information.*

/**
 * 资讯页面
 */
class InformationFragment : BaseFragment<Constanct.View, Constanct.Presenter>(), Constanct.View {
    var plateId = 0
    var page = 1
    var count = 10
    var informationAdapter: InformationAdapter? = null

    override fun getLayoutId(): Int {
        return R.layout.fragment_information
    }

    override fun initPresenter(): Presenter {
        return Presenter()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        var sp = context!!.getSharedPreferences("config", Context.MODE_PRIVATE)
        var userId = sp.getString("userId", "")
        var sessionId = sp.getString("sessionId", "")
        val nHeadMap: Map<String, Any> = mapOf()
        val bannerPrams: Map<String, Any> = mapOf()
        var sHeadMap = mapOf(Pair("userId", userId), Pair("sessionId", sessionId))
        var infoPrams = mapOf(Pair("plateId", plateId), Pair("page", page), Pair("count", count))
        mPresenter!!.getPresenter(Api.TECH_BANNER, nHeadMap, BannerBean::class.java, bannerPrams)

        if (userId.equals("") || sessionId.equals("")) {
            mPresenter!!.getPresenter(Api.TECH_INFOR, nHeadMap, InfoBean::class.java, infoPrams)
        } else {
            mPresenter!!.getPresenter(Api.TECH_INFOR, sHeadMap, InfoBean::class.java, infoPrams)
        }
        informationAdapter = InformationAdapter(this.context!!)
        //布局管理器
        val layoutManager = LinearLayoutManager(context)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        xrecycler_view.layoutManager = layoutManager
        xrecycler_view.setPullRefreshEnabled(true)
        xrecycler_view.setLoadingMoreEnabled(true)
        xrecycler_view.setLoadingListener(object :XRecyclerView.LoadingListener{
            override fun onLoadMore() {

                Handler().postDelayed(object :Runnable{
                    override fun run() {
                        plateId++
                        var sHeadMap = mapOf(Pair("userId", userId), Pair("sessionId", sessionId))
                        var infoPrams = mapOf(Pair("plateId", plateId), Pair("page", page), Pair("count", count))
                        if (userId.equals("") || sessionId.equals("")) {
                            mPresenter!!.getPresenter(Api.TECH_INFOR, nHeadMap, InfoBean::class.java, infoPrams)
                        } else {
                            mPresenter!!.getPresenter(Api.TECH_INFOR, sHeadMap, InfoBean::class.java, infoPrams)
                        }
                        xrecycler_view.loadMoreComplete()
                    }

                },2500)

            }

            override fun onRefresh() {
                Handler().postDelayed(object :Runnable{
                    override fun run() {
                        plateId = 0
                        var sHeadMap = mapOf(Pair("userId", userId), Pair("sessionId", sessionId))
                        var infoPrams = mapOf(Pair("plateId", plateId), Pair("page", page), Pair("count", count))
                        if (userId.equals("") || sessionId.equals("")) {
                            mPresenter!!.getPresenter(Api.TECH_INFOR, nHeadMap, InfoBean::class.java, infoPrams)
                        } else {
                            mPresenter!!.getPresenter(Api.TECH_INFOR, sHeadMap, InfoBean::class.java, infoPrams)
                        }
                        xrecycler_view.refreshComplete()
                    }

                },2500)


            }

        })

        if (!informationAdapter!!.hasObservers()){
            xrecycler_view.adapter = informationAdapter
        }else {
            informationAdapter!!.notifyDataSetChanged();
        }
    }

    override fun initData() {


    }

    override fun View(any: Any) {

        if (any is BannerBean) {//banner数据
            var bannerBean = any
            val bannerList = bannerBean.result
            informationAdapter!!.setBannerResult(bannerList)
        }else if(any is InfoBean){//资讯集合
            var infoBean = any
            val infoList = infoBean.result
            //Toast.makeText(context,infoList[1].summary,Toast.LENGTH_LONG).show()
            informationAdapter!!.setInfoResult(infoList)
        }

    }


}

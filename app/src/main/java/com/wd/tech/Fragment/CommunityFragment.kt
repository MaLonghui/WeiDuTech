package com.wd.tech.Fragment

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.widget.Toast
import com.jcodecraeer.xrecyclerview.XRecyclerView
import com.wd.tech.R
import com.wd.tech.adapter.CommunityAdapter
import com.wd.tech.api.Api
import com.wd.tech.base.BaseFragment
import com.wd.tech.bean.CancelthethumbupBean
import com.wd.tech.bean.Community
import com.wd.tech.bean.CommunityBean
import com.wd.tech.bean.GiveALikeBean
import com.wd.tech.mvp.Constanct
import com.wd.tech.mvp.Presenter
import kotlinx.android.synthetic.main.fragment_community.*

/**
 * 社区
 */
class CommunityFragment : BaseFragment<Constanct.View, Constanct.Presenter>(), Constanct.View {
    var adapter: CommunityAdapter? = null
    var page: Int = 1
    var count: Int = 5
    override fun getLayoutId(): Int {
        return R.layout.fragment_community
    }

    override fun initPresenter(): Constanct.Presenter {
        return Presenter()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        recycler_community.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        adapter = CommunityAdapter(this.context!!)
        val sp: SharedPreferences = activity!!.getSharedPreferences("confit", Context.MODE_PRIVATE)
        val userid = sp.getString("userId", "")
        val sessionId = sp.getString("sessionId", "")
        val map: Map<String, String> = mapOf(Pair("userId", userid), Pair("sessionId", sessionId))
        val mapone: Map<String, Int> = mapOf(Pair("page", page), Pair("count", count))
        mPresenter!!.getPresenter(Api.COMMUNITY, map, Community::class.java, mapone)
        recycler_community.adapter = adapter
        adapter!!.setOnPriseClickListenter(object : CommunityAdapter.OnPriseClickListenter {
            override fun onPriseClick(position: Int, b: Boolean) {
                if (b) {
                    val maptwo: Map<String, Int> = mapOf(Pair("communityId", position))
                    mPresenter!!.postPresenter(Api.GIVEALIKE, map, GiveALikeBean::class.java, maptwo)
                    adapter!!.notifyDataSetChanged()
                } else {
                    val maptwo: Map<String, Int> = mapOf(Pair("communityId", position))
                    mPresenter!!.deletePresenter(Api.GIVEDELETE, map, CancelthethumbupBean::class.java, maptwo)
                    adapter!!.notifyDataSetChanged()
                }
            }
        })
        recycler_community.setLoadingListener(object : XRecyclerView.LoadingListener {
            override fun onLoadMore() {

            }

            override fun onRefresh() {

            }

        })
    }

    override fun initData() {

    }

    override fun View(any: Any) {
        if (any is Community) {
            var Community: Community = any
            val result = Community.result
            adapter!!.setResult(result)
        }
        if (any is GiveALikeBean) {
            var bean: GiveALikeBean = any
            if (bean.status.equals("0000")) {
                Toast.makeText(context, bean.message, Toast.LENGTH_LONG).show()
            }
        } else if (any is CancelthethumbupBean) {
            var bean: CancelthethumbupBean = any
            if (bean.status.equals("0000")) {
                Toast.makeText(context, bean.message, Toast.LENGTH_LONG).show()
            }
        }

    }

}

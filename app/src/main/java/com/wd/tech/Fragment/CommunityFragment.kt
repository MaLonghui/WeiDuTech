package com.wd.tech.Fragment

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.widget.Toast
import com.wd.tech.R
import com.wd.tech.adapter.CommunityAdapter
import com.wd.tech.api.Api
import com.wd.tech.base.BaseFragment
import com.wd.tech.bean.Community
import com.wd.tech.bean.CommunityBean
import com.wd.tech.mvp.Constanct
import com.wd.tech.mvp.Presenter
import kotlinx.android.synthetic.main.fragment_community.*

/**
 * 社区
 */
class CommunityFragment : BaseFragment<Constanct.View,Constanct.Presenter>(),Constanct.View {
    var adapter:CommunityAdapter ?=null
    var page:Int =1
    var count:Int=5
    override fun getLayoutId(): Int {
        return R.layout.fragment_community
    }

    override fun initPresenter(): Constanct.Presenter {
        return Presenter()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        recycler_community.layoutManager= LinearLayoutManager(context,LinearLayoutManager.VERTICAL, false)
        adapter = CommunityAdapter(this.context!!)
        val sp: SharedPreferences = activity!!.getSharedPreferences("confit", Context.MODE_PRIVATE)
        val userid = sp.getString("userId", "")
        val sessionId = sp.getString("sessionId", "")
        val map:Map<String,String> = mapOf(Pair("userId",userid), Pair("sessionId",sessionId))
        val mapone: Map<String, Int> = mapOf(Pair("page",page), Pair("count",count))
        mPresenter!!.getPresenter(Api.COMMUNITY,map, Community::class.java,mapone)
        recycler_community.adapter=adapter
    }
    override fun initData() {

    }

    override fun View(any: Any) {
        var Community: Community =any as Community
        if(Community!=null){
            val result = Community.result
            adapter!!.setResult(result)
        }

    }

}

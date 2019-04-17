package com.wd.tech.activity

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.StaggeredGridLayoutManager
import android.text.TextUtils
import android.view.KeyEvent
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import android.widget.Toast
import com.wd.tech.R
import com.wd.tech.adapter.InfoSearchAdapter
import com.wd.tech.adapter.ReSouCiAdapter
import com.wd.tech.api.Api
import com.wd.tech.base.BaseActivity
import com.wd.tech.bean.InfoSearchBean
import com.wd.tech.mvp.Constanct
import com.wd.tech.mvp.Presenter
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_search.*

class SearchActivity : BaseActivity<Constanct.View, Constanct.Presenter>(), Constanct.View {
    var page = 1
    var count = 5
    var title = ""
    var infoSearchAdapter: InfoSearchAdapter? = null
    override fun getLayoutId(): Int {
        return R.layout.activity_search
    }

    override fun initPresenter(): Constanct.Presenter {
        return Presenter()
    }

    override fun initData() {
        //热搜词
        reSouCi()
        info_search_edit.setOnFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                search_linear.visibility = GONE
                search_recycler_sou.visibility = VISIBLE
            }
        }
        info_search_back.setOnClickListener {
            onBackPressed()
        }
        info_search_edit.setOnEditorActionListener(object : TextView.OnEditorActionListener {
            override fun onEditorAction(text: TextView?, i: Int, event: KeyEvent?): Boolean {
                if (i == EditorInfo.IME_ACTION_UNSPECIFIED) {
                    title = text!!.text.toString()
                    var headMap: Map<String, Any> = mapOf()
                    var prams: Map<String, Any> = mapOf(Pair("title", title), Pair("page", page), Pair("count", count))
                    if (!TextUtils.isEmpty(text.toString())) {
                        mPresenter!!.getPresenter(Api.INFO_TITLE, headMap, InfoSearchBean::class.java, prams)
                    }
                    //点击回车隐藏软键盘
                    val service: InputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    service.hideSoftInputFromWindow(info_search_edit.windowToken,0)
                }
                return false
            }

        })



        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        search_recycler_sou.layoutManager = layoutManager
        infoSearchAdapter = InfoSearchAdapter(this)
        search_recycler_sou.adapter = infoSearchAdapter
        search_recycler_sou.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
    }




    override fun View(any: Any) {
        if (any != null) {
            var infoSearchBean: InfoSearchBean = any as InfoSearchBean
            val searchResult = infoSearchBean.result
            if (searchResult.size>0){
                search_no_data.visibility= GONE
                search_recycler_sou.visibility = VISIBLE
                infoSearchAdapter!!.setSearChList(searchResult)
            }else{
                search_no_data.visibility= VISIBLE
                search_recycler_sou.visibility = GONE
            }
        }
    }

    private fun reSouCi() {
        var reList: MutableList<String> = ArrayList()
        reList.add("区块链")
        reList.add("中年危机")
        reList.add("锤子科技")
        reList.add("子弹短信")
        reList.add("民营企业")
        reList.add("特斯拉")
        reList.add("支付宝")
        reList.add("资本市场")
        reList.add("电视剧")
        val manager = StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL)
        search_recycler_re.layoutManager = manager
        var rscAdapter = ReSouCiAdapter(this)
        rscAdapter.setSearList(reList)
        search_recycler_re.adapter = rscAdapter

        rscAdapter.setItemListener {
            info_search_edit.setText(it)
            search_linear.visibility = GONE
            search_recycler_sou.visibility = VISIBLE
            var headMap: Map<String, Any> = mapOf()
            var prams: Map<String, Any> = mapOf(Pair("title", it), Pair("page", page), Pair("count", count))
            mPresenter!!.getPresenter(Api.INFO_TITLE, headMap, InfoSearchBean::class.java, prams)
        }

    }

}

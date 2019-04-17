package com.wd.tech.activity

import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.view.View.GONE
import android.view.View.VISIBLE
import com.wd.tech.R
import com.wd.tech.adapter.InfoCommentAdapter
import com.wd.tech.bean.InfoCommentListBean
import com.wd.tech.adapter.InfoTuiJianAdapter
import com.wd.tech.api.Api
import com.wd.tech.base.BaseActivity
import com.wd.tech.bean.InfoDetailsBean
import com.wd.tech.mvp.Constanct
import com.wd.tech.mvp.Presenter
import kotlinx.android.synthetic.main.activity_info_details.*
import java.text.SimpleDateFormat
import java.util.*

/**
 * 资讯详情页
 */
class InfoDetailsActivity : BaseActivity<Constanct.View, Constanct.Presenter>(), Constanct.View {

    var page: Int = 1
    var count: Int = 5
    override fun getLayoutId(): Int {
        return R.layout.activity_info_details
    }

    override fun initPresenter(): Constanct.Presenter {
        return Presenter()
    }

    override fun initData() {
        val id = intent.getIntExtra("id", 1)
        val sp = getSharedPreferences("config", Context.MODE_PRIVATE)
        val userId = sp.getString("userId", "")
        val sessionId = sp.getString("sessionId", "")
        var headMap: Map<String, Any> = mapOf(Pair("userId", userId), Pair("sessionId", sessionId))
        var prams: Map<String, Any> = mapOf(Pair("id", id))
        var commentPrams: Map<String, Any> = mapOf(Pair("infoId", id), Pair("page", page), Pair("count", count))
        if (userId.equals("") || sessionId.equals("")) {
            var nHeadMap: Map<String, Any> = mapOf()
            mPresenter!!.getPresenter(Api.INFO_DETAILS, nHeadMap, InfoDetailsBean::class.java, prams)
            mPresenter!!.getPresenter(Api.INFO_DETAILS_COMMENT, nHeadMap, InfoCommentListBean::class.java, commentPrams)
        } else {
            mPresenter!!.getPresenter(Api.INFO_DETAILS, headMap, InfoDetailsBean::class.java, prams)
            mPresenter!!.getPresenter(Api.INFO_DETAILS_COMMENT, headMap, InfoCommentListBean::class.java, commentPrams)
        }
        info_details_back.setOnClickListener {
            onBackPressed()
        }

    }

    override fun View(any: Any) {
        if (any is InfoDetailsBean) {
            var infoDetailsBean = any
            val result = infoDetailsBean.result
            info_details_title.text = result.title
            info_details_source.text = result.source
            val date = Date(result.releaseTime)
            val sdf = SimpleDateFormat("yyyy-MM-dd  hh:mm")
            val s = sdf.format(date)
            info_details_time.text = s
            if (result.whetherGreat == 1) {
                info_details_prise_icon.setImageResource(R.mipmap.common_icon_praise_s)
            } else if (result.whetherGreat == 2) {
                info_details_prise_icon.setImageResource(R.mipmap.common_icon_prise_n)
            }
            if (result.whetherCollection == 1) {
                info_details_collect_icon.setImageResource(R.mipmap.common_icon_collect_s)
            } else {
                info_details_collect_icon.setImageResource(R.mipmap.common_icon_collect_n)
            }
            val settings = info_details_webview.settings
            settings.javaScriptEnabled = true
            val js = "<script type=\"text/javascript\">" +
                    "var imgs = document.getElementsByTagName('img');" + // 找到img标签

                    "for(var i = 0; i<imgs.length; i++){" +  // 逐个改变

                    "imgs[i].style.width = '100%';" +  // 宽度改为100%

                    "imgs[i].style.height = 'auto';" +
                    "}" +
                    "</script>"

            info_details_webview.loadDataWithBaseURL(null, result.content + js, "text/html", "utf-8", null)
            info_details_tuijian.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
            info_details_pl_recycler.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
            if (result.readPower == 1) {
                val informationList = infoDetailsBean.result.informationList
                val infoTuiJianAdapter = InfoTuiJianAdapter(this)
                infoTuiJianAdapter.setTuiJianList(informationList)
                info_details_tuijian.adapter = infoTuiJianAdapter
                info_tuijian_linear.visibility = VISIBLE
                info_details_webview.visibility = VISIBLE
                info_no_power.visibility = GONE
            } else {
                info_tuijian_linear.visibility = GONE
                info_details_webview.visibility = GONE
                info_no_power.visibility = VISIBLE
            }
        } else if (any is InfoCommentListBean) {
            var infoCommentListBean: InfoCommentListBean = any
            val commentList = infoCommentListBean.result
            if (commentList.size > 0) {
                info_no_comment.visibility = GONE
                info_details_pl_recycler.visibility = VISIBLE
                val infoCommentAdapter = InfoCommentAdapter(this)
                infoCommentAdapter.setComList(commentList)
                info_details_pl_recycler.adapter = infoCommentAdapter
            } else {
                info_no_comment.visibility = VISIBLE
                info_details_pl_recycler.visibility = GONE
            }
        }
    }
}

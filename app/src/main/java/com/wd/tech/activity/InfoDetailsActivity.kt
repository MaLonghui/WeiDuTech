package com.wd.tech.activity

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.support.v7.widget.LinearLayoutManager
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.ImageView
import android.widget.PopupWindow
import android.widget.TextView
import android.widget.Toast
import com.wd.tech.R
import com.wd.tech.adapter.InfoCommentAdapter
import com.wd.tech.bean.InfoCommentListBean
import com.wd.tech.adapter.InfoTuiJianAdapter
import com.wd.tech.api.Api
import com.wd.tech.base.BaseActivity
import com.wd.tech.bean.InfoDetailsBean
import com.wd.tech.bean.UserPublicBean
import com.wd.tech.mvp.Constanct
import com.wd.tech.mvp.Presenter
import com.wd.tech.utils.JumpActivityUtils
import kotlinx.android.synthetic.main.activity_info_details.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.HashMap

/**
 * 资讯详情页
 */
class InfoDetailsActivity : BaseActivity<Constanct.View, Constanct.Presenter>(), Constanct.View, View.OnClickListener {
    override fun onClick(v: View?) {
        when(v!!.id){
            R.id.text_btn_ji->{//积分兑换
                var id = intent.getIntExtra("id", 1)
                var infoId: HashMap<String, Any> = hashMapOf(Pair("id", id))
                JumpActivityUtils.skipValueActivity(this,IntegralActivity::class.java, infoId)
                closePopupWindow()
            }
            R.id.text_btn_vip->{
                JumpActivityUtils.skipAnotherActivity(this,BuyVipActivity::class.java)
            }
            R.id.pay_qu->{
                closePopupWindow()
            }
        }
    }

    var page: Int = 1
    var count: Int = 5
    var pop: PopupWindow? = null
    override fun getLayoutId(): Int {
        return R.layout.activity_info_details
    }

    override fun initPresenter(): Constanct.Presenter {
        return Presenter()
    }

    override fun onResume() {
        super.onResume()
        var id = intent.getIntExtra("id", 1)
        var sp = getSharedPreferences("config", Context.MODE_PRIVATE)
        var userId = sp.getString("userId", "")
        var sessionId = sp.getString("sessionId", "")
        var infoId: Map<String, Any> = mapOf(Pair("infoId", id))
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
    }

    override fun initData() {
        var id = intent.getIntExtra("id", 1)
        var sp = getSharedPreferences("config", Context.MODE_PRIVATE)
        var userId = sp.getString("userId", "")
        var sessionId = sp.getString("sessionId", "")
        var infoId: Map<String, Any> = mapOf(Pair("infoId", id))
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

        //付费
        no_power_btn.setOnClickListener {
            showPayPop()
        }
    }
    private fun closePopupWindow() {
        if (pop != null && pop!!.isShowing()) {
            pop!!.dismiss()
            pop = null
        }
    }
    private fun showPayPop() {
        val view = LayoutInflater.from(this).inflate(R.layout.pay_pop_layout, null)
        val btn_ji = view.findViewById<TextView>(R.id.text_btn_ji)
        val btn_vip = view.findViewById<TextView>(R.id.text_btn_vip)
        val pay_qu = view.findViewById<ImageView>(R.id.pay_qu)
        pop = PopupWindow(view, -1, -2);
        pop!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        pop!!.isOutsideTouchable = true
        pop!!.isFocusable = true
        val lp = window.attributes
        lp.alpha = 0.5f
        window.attributes = lp
        pop!!.setOnDismissListener {
            val lp = window.attributes
            lp.alpha = 1f
            window.attributes = lp
        }
        pop!!.animationStyle = R.style.main_menu_photo_anim
        pop!!.showAtLocation(window.decorView, Gravity.BOTTOM, 0, 0)
        btn_ji.setOnClickListener(this)
        btn_vip.setOnClickListener(this)
        pay_qu.setOnClickListener(this)

    }

    override fun View(any: Any) {

        var id = intent.getIntExtra("id", 1)
        var sp = getSharedPreferences("config", Context.MODE_PRIVATE)
        var userId = sp.getString("userId", "")
        var sessionId = sp.getString("sessionId", "")
        var infoId: Map<String, Any> = mapOf(Pair("infoId", id))
        var headMap: Map<String, Any> = mapOf(Pair("userId", userId), Pair("sessionId", sessionId))
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

            //点赞
            info_details_prise_icon.setOnClickListener {

                if (userId.equals("") || sessionId.equals("")) {
                    Toast.makeText(this, "还没有登录哦", Toast.LENGTH_LONG).show()
                } else {
                    if (result.whetherGreat == 2) {
                        //点赞
                        mPresenter!!.postPresenter(Api.INFO_GREAT, headMap, UserPublicBean::class.java, infoId)
                    } else if (result.whetherGreat == 1) {
                        //取消点赞
                        mPresenter!!.deletePresenter(Api.INFO_CANCEL_GREAT, headMap, UserPublicBean::class.java, infoId)
                    }

                }
            }
            //收藏
            info_details_collect_icon.setOnClickListener {

                if (userId.equals("") || sessionId.equals("")) {
                    Toast.makeText(this, "还没有登录哦", Toast.LENGTH_LONG).show()
                } else {
                    if (result.whetherCollection == 1) {
                        //取消收藏
                        mPresenter!!.deletePresenter(Api.INFO_CANCEl_COLLECT, headMap, UserPublicBean::class.java, infoId)
                    } else if (result.whetherCollection == 2) {
                        //收藏
                        mPresenter!!.postPresenter(Api.INFO_COLLECT, headMap, UserPublicBean::class.java, infoId)
                    }
                }
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
        } else if (any is UserPublicBean) {
            var publicBean: UserPublicBean = any
            Toast.makeText(this, publicBean.message, Toast.LENGTH_LONG).show()
            if (publicBean.status.equals("0000")) {
                var id = intent.getIntExtra("id", 1)
                var sp = getSharedPreferences("config", Context.MODE_PRIVATE)
                var userId = sp.getString("userId", "")
                var sessionId = sp.getString("sessionId", "")
                var headMap: Map<String, Any> = mapOf(Pair("userId", userId), Pair("sessionId", sessionId))
                var prams: Map<String, Any> = mapOf(Pair("id", id))
                mPresenter!!.getPresenter(Api.INFO_DETAILS, headMap, InfoDetailsBean::class.java, prams)
            }
        }
    }

}

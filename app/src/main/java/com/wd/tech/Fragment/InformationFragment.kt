package com.wd.tech.Fragment

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.Handler
import android.support.v7.widget.LinearLayoutManager
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.PopupWindow
import android.widget.TextView
import android.widget.Toast
import com.jcodecraeer.xrecyclerview.ProgressStyle
import com.jcodecraeer.xrecyclerview.XRecyclerView
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage
import com.tencent.mm.opensdk.modelmsg.WXTextObject
import com.tencent.mm.opensdk.openapi.IWXAPI
import com.tencent.mm.opensdk.openapi.WXAPIFactory
import com.wd.tech.R
import com.wd.tech.activity.InfoAllPlateActivity
import com.wd.tech.activity.SearchActivity
import com.wd.tech.adapter.InformationAdapter
import com.wd.tech.api.Api
import com.wd.tech.base.BaseFragment
import com.wd.tech.bean.BannerBean
import com.wd.tech.bean.InfoBean
import com.wd.tech.bean.UserPublicBean
import com.wd.tech.mvp.Constanct
import com.wd.tech.mvp.Presenter
import com.wd.tech.utils.MD5Utils
import kotlinx.android.synthetic.main.fragment_information.*

/**
 * 资讯页面
 */
class InformationFragment : BaseFragment<Constanct.View, Constanct.Presenter>(), Constanct.View {
    var plateId = 0
    var page = 1
    var count = 5
    private var api: IWXAPI? = null
    var pop1: PopupWindow? = null
    var informationAdapter: InformationAdapter? = null

    override fun getLayoutId(): Int {
        return R.layout.fragment_information
    }

    override fun initPresenter(): Presenter {
        return Presenter()
    }

    override fun onResume() {
        super.onResume()
        var sp = context!!.getSharedPreferences("config", Context.MODE_PRIVATE)
        var userId = sp.getString("userId", "")
        var sessionId = sp.getString("sessionId", "")
        var nHeadMap: Map<String, Any> = mapOf()
        var sHeadMap = mapOf(Pair("userId", userId), Pair("sessionId", sessionId))
        var infoPrams = mapOf(Pair("plateId", plateId), Pair("page", page), Pair("count", count))
        if (userId.equals("") || sessionId.equals("")) {
            mPresenter!!.getPresenter(Api.TECH_INFOR, nHeadMap, InfoBean::class.java, infoPrams)
        } else {
            mPresenter!!.getPresenter(Api.TECH_INFOR, sHeadMap, InfoBean::class.java, infoPrams)
        }
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
        //收藏
        informationAdapter!!.setCollectClick { id, collect ->
            // Toast.makeText(context,"${id}",Toast.LENGTH_LONG).show()
            var prams = mapOf(Pair("infoId", id))
            if (collect == 2) {
                mPresenter!!.postPresenter(Api.INFO_COLLECT, sHeadMap, UserPublicBean::class.java, prams)
            } else if (collect == 1) {
                mPresenter!!.deletePresenter(Api.INFO_CANCEl_COLLECT, sHeadMap, UserPublicBean::class.java, prams)
            }

        }
        //分享
        informationAdapter!!.setShareClick {
            showSharePop(it)
        }
        //布局管理器
        val layoutManager = LinearLayoutManager(context)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        xrecycler_view.layoutManager = layoutManager
        xrecycler_view.setPullRefreshEnabled(true)
        xrecycler_view.setLoadingMoreEnabled(true)
        xrecycler_view.setRefreshProgressStyle(ProgressStyle.BallTrianglePath)
        xrecycler_view.setLoadingMoreProgressStyle(ProgressStyle.BallTrianglePath)
        xrecycler_view.setLoadingListener(object : XRecyclerView.LoadingListener {
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

                        xrecycler_view.loadMoreComplete()
                    }
                }, 2500)


            }


            override fun onRefresh() {
                Handler().postDelayed(object : Runnable {
                    override fun run() {
                        count = 10
                        var sHeadMap = mapOf(Pair("userId", userId), Pair("sessionId", sessionId))
                        var infoPrams = mapOf(Pair("plateId", plateId), Pair("page", page), Pair("count", count))
                        if (userId.equals("") || sessionId.equals("")) {
                            mPresenter!!.getPresenter(Api.TECH_INFOR, nHeadMap, InfoBean::class.java, infoPrams)
                        } else {
                            mPresenter!!.getPresenter(Api.TECH_INFOR, sHeadMap, InfoBean::class.java, infoPrams)
                        }
                        xrecycler_view.refreshComplete()
                    }
                }, 2500)
            }
        })

        image_menu.setOnClickListener {
            startActivity(Intent(activity, InfoAllPlateActivity::class.java))
        }
        image_search.setOnClickListener {
            startActivity(Intent(this!!.activity!!, SearchActivity::class.java))
        }
        if (!informationAdapter!!.hasObservers()) {
            xrecycler_view.adapter = informationAdapter
        } else {
            informationAdapter!!.notifyDataSetChanged()
        }

    }

    private fun showSharePop(it1: String) {
        val view = LayoutInflater.from(context).inflate(R.layout.layout_share_pop, null)
        val tv_friend = view.findViewById<LinearLayout>(R.id.tv_friend)
        val tv_pyq = view.findViewById<LinearLayout>(R.id.tv_pyq)
        val tv_qx = view.findViewById<TextView>(R.id.tv_qx)
        pop1 = PopupWindow(view, -1, -2);
        pop1!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        pop1!!.isOutsideTouchable = true
        pop1!!.isFocusable = true
        val lp = activity!!.window.attributes

        lp.alpha = 0.5f
        activity!!.window.attributes = lp
        pop1!!.setOnDismissListener {
            val lp = activity!!.window.attributes
            lp.alpha = 1f
            activity!!.window.attributes = lp
        }
        pop1!!.animationStyle = R.style.main_menu_photo_anim
        pop1!!.showAtLocation(activity!!.window.decorView, Gravity.BOTTOM, 0, 0)
        tv_friend.setOnClickListener {
            share(false, it1)
            pop1!!.dismiss()
        }
        tv_pyq.setOnClickListener {
            share(true, it1)
            pop1!!.dismiss()
        }
        tv_qx.setOnClickListener {
            pop1!!.dismiss()
        }
    }

    private fun share(friendsCircle: Boolean, it1: String) {
        //初始化一个 WXTextObject 对象，填写分享的文本内容
        val textObj = WXTextObject()
        textObj.text = it1
        //用 WXTextObject 对象初始化一个 WXMediaMessage 对象
        val msg = WXMediaMessage()
        msg.mediaObject = textObj
        msg.description = it1
        val req = SendMessageToWX.Req()
        req.transaction = System.currentTimeMillis().toString()
        req.message = msg
        req.scene = if (friendsCircle) SendMessageToWX.Req.WXSceneTimeline else SendMessageToWX.Req.WXSceneSession;
        api!!.sendReq(req)

    }

    override fun initData() {
        api = WXAPIFactory.createWXAPI(context, "wx4c96b6b8da494224", true);
        api!!.registerApp("wx4c96b6b8da494224")
        var sp = context!!.getSharedPreferences("config", Context.MODE_PRIVATE)
        var userId = sp.getString("userId", "")
        var sessionId = sp.getString("sessionId", "")
        var nHeadMap: Map<String, Any> = mapOf()
        var sHeadMap = mapOf(Pair("userId", userId), Pair("sessionId", sessionId))
        var infoPrams = mapOf(Pair("plateId", plateId), Pair("page", page), Pair("count", count))
        if (userId.equals("") || sessionId.equals("")) {
            mPresenter!!.getPresenter(Api.TECH_INFOR, nHeadMap, InfoBean::class.java, infoPrams)
        } else {
            mPresenter!!.getPresenter(Api.TECH_INFOR, sHeadMap, InfoBean::class.java, infoPrams)
        }
    }

    override fun View(any: Any) {
        var sp = context!!.getSharedPreferences("config", Context.MODE_PRIVATE)
        var userId = sp.getString("userId", "")
        var sessionId = sp.getString("sessionId", "")
        var nHeadMap: Map<String, Any> = mapOf()
        var sHeadMap = mapOf(Pair("userId", userId), Pair("sessionId", sessionId))
        var infoPrams = mapOf(Pair("plateId", plateId), Pair("page", page), Pair("count", count))
        if (any is BannerBean) {//banner数据
            var bannerBean = any
            val bannerList = bannerBean.result
            informationAdapter!!.setBannerResult(bannerList)
        } else if (any is InfoBean) {//资讯集合
            var infoBean = any
            var infoList = infoBean.result
            informationAdapter!!.setInfoResult(infoList)

        } else if (any is UserPublicBean) {
            var userPublicBean: UserPublicBean = any
            Toast.makeText(context, userPublicBean.message, Toast.LENGTH_LONG).show()
            if (userId.equals("") || sessionId.equals("")) {
                mPresenter!!.getPresenter(Api.TECH_INFOR, nHeadMap, InfoBean::class.java, infoPrams)
            } else {
                mPresenter!!.getPresenter(Api.TECH_INFOR, sHeadMap, InfoBean::class.java, infoPrams)
            }

        }
    }
}

package com.wd.tech.activity

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Handler
import android.os.Message
import android.support.v7.widget.GridLayoutManager
import android.text.TextUtils
import android.widget.Toast
import com.alipay.sdk.app.PayTask
import com.wd.tech.R
import com.wd.tech.adapter.VipGoodsAdapter
import com.wd.tech.api.Api
import com.wd.tech.base.BaseActivity
import com.wd.tech.bean.PayResult
import com.wd.tech.bean.PaySuccessBean
import com.wd.tech.bean.VipGoodsBean
import com.wd.tech.bean.XiaDanBean
import com.wd.tech.mvp.Constanct
import com.wd.tech.mvp.Presenter
import com.wd.tech.utils.MD5Utils
import com.wd.tech.wxapi.WXPayEntryActivity
import kotlinx.android.synthetic.main.activity_buy_vip.*

class BuyVipActivity : BaseActivity<Constanct.View, Constanct.Presenter>(), Constanct.View {
    var adapter: VipGoodsAdapter? = null
    var type = 1
    var commodityId = 1004
    var price = 0.08
    private val SDK_PAY_FLAG = 1001

    @SuppressLint("HandlerLeak")
    private val mHandler = object : Handler() {
        override fun handleMessage(msg: Message) {
            when (msg.what) {
                SDK_PAY_FLAG -> {
                    val payResult = PayResult(msg.obj as Map<String, String>)
                    /**
                     * 对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                     */
                    val resultInfo = payResult.result// 同步返回需要验证的信息
                    val resultStatus = payResult.resultStatus
                    // 判断resultStatus 为9000则代表支付成功
                    if (TextUtils.equals(resultStatus, "9000")) {
                        // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                        Toast.makeText(this@BuyVipActivity, "支付成功", Toast.LENGTH_SHORT).show()
                    } else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                        Toast.makeText(this@BuyVipActivity, "支付失败", Toast.LENGTH_SHORT).show()
                    }
                }
                else -> {
                }
            }
        }
    }



    override fun getLayoutId(): Int {
        return R.layout.activity_buy_vip
    }

    override fun initPresenter(): Constanct.Presenter {
        return Presenter()

    }

    override fun initData() {
        var sp = getSharedPreferences("config", Context.MODE_PRIVATE)
        var userId = sp.getString("userId", "")
        var sessionId = sp.getString("sessionId", "")
        var headMap: Map<String, Any> = mapOf(Pair("userId", userId), Pair("sessionId", sessionId))
        var nHeadMap: Map<String, Any> = mapOf()
        var nprams: Map<String, Any> = mapOf()
        mPresenter!!.getPresenter(Api.VIP_GOODS, nHeadMap, VipGoodsBean::class.java, nprams)
        adapter = VipGoodsAdapter(this)
        vip_recycler_view.layoutManager = GridLayoutManager(this, 2)
        vip_recycler_view.adapter = adapter
        //选择商品
        adapter!!.setItemClickListener { i, d ->
            commodityId = i
            price = d
            text_money.text = "${d}"
        }
        wx_relative.setOnClickListener {
            wx_radio.isChecked = true
            zfb_radio.isChecked = false
            type = 1
        }
        zfb_relative.setOnClickListener {
            zfb_radio.isChecked = true
            wx_radio.isChecked = false
            type = 2
        }
        zfb_radio.setOnClickListener {
            wx_radio.isChecked = false
            type = 2
        }
        wx_radio.setOnClickListener {
            zfb_radio.isChecked = false
            type = 1
        }

        var mD5Utils:MD5Utils = MD5Utils()
        var signStr:String = userId+commodityId+"tech"
        var sign = mD5Utils.string2MD5(signStr)
        var prams = mapOf(Pair("commodityId",commodityId), Pair("sign",sign))
        text_kai.setOnClickListener {
            mPresenter!!.postPresenter(Api.BUY_VIP,headMap,XiaDanBean::class.java,prams)
        }
    }

    override fun View(any: Any) {
        var sp = getSharedPreferences("config", Context.MODE_PRIVATE)
        var userId = sp.getString("userId", "")
        var sessionId = sp.getString("sessionId", "")
        var headMap: Map<String, Any> = mapOf(Pair("userId", userId), Pair("sessionId", sessionId))

        if (any is VipGoodsBean) {
            var vipGoodsBean: VipGoodsBean = any
            val vipResult = vipGoodsBean.result
            adapter!!.setList(vipResult)
        }else if(any is XiaDanBean){
            var xiaDanBean : XiaDanBean = any
            var payPrams = mapOf(Pair("orderId",xiaDanBean.orderId), Pair("payType",type))
            mPresenter!!.postPresenter(Api.VIP_PAY,headMap,PaySuccessBean::class.java,payPrams)
            Toast.makeText(this,xiaDanBean.message,Toast.LENGTH_LONG).show()

        }else if(any is PaySuccessBean){
            var payBean : PaySuccessBean = any
            if (payBean !=null){
                val payRunnable = Runnable {
                    val alipay = PayTask(this)
                    val result:Map<String,Any> = alipay.payV2(payBean.result, true)
                    val msg = Message()
                    msg.what = SDK_PAY_FLAG
                    msg.obj = result
                    mHandler.sendMessage(msg)
                }
                // 必须异步调用
                val payThread = Thread(payRunnable)
                payThread.start()
            }else{
                //带值到微信支付页
                val intent = Intent(this, WXPayEntryActivity::class.java)
                intent.putExtra("appId", payBean.appId)
                intent.putExtra("nonceStr", payBean.nonceStr)
                intent.putExtra("partnerId", payBean.partnerId)
                intent.putExtra("prepayId", payBean.prepayId)
                intent.putExtra("sign", payBean.sign)
                intent.putExtra("timeStamp", payBean.timeStamp)
                intent.putExtra("packageValue", payBean.packageValue)
                startActivity(intent)
            }
        }
    }


}

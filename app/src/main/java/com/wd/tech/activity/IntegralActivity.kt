package com.wd.tech.activity

import android.content.Context
import android.net.Uri
import android.support.v7.app.AlertDialog
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.wd.tech.R
import com.wd.tech.api.Api
import com.wd.tech.base.BaseActivity
import com.wd.tech.bean.InfoDetailsBean
import com.wd.tech.bean.UserIntegralBean
import com.wd.tech.bean.UserPublicBean
import com.wd.tech.mvp.Constanct
import com.wd.tech.mvp.Presenter
import kotlinx.android.synthetic.main.activity_integral.*
import org.greenrobot.eventbus.Subscribe
import java.text.SimpleDateFormat
import java.util.*

class IntegralActivity : BaseActivity<Constanct.View, Constanct.Presenter>(), Constanct.View {
    var collectNum: String = "100"
    override fun getLayoutId(): Int {
        return R.layout.activity_integral
    }

    override fun initPresenter(): Constanct.Presenter {
        return Presenter()
    }

    @Subscribe(sticky = true)
    fun getEventBus(collect: String) {
        collectNum = collect
    }

    override fun initData() {
        var id = intent.getIntExtra("id", 1)
        var sp = getSharedPreferences("config", Context.MODE_PRIVATE)
        var userId = sp.getString("userId", "")
        var sessionId = sp.getString("sessionId", "")
        var headMap: Map<String, Any> = mapOf(Pair("userId", userId), Pair("sessionId", sessionId))
        var prams: Map<String, Any> = mapOf(Pair("id", id))

        var map: Map<String, Any> = mapOf()
        if (userId.equals("") || sessionId.equals("")) {
            var nHeadMap: Map<String, Any> = mapOf()
            mPresenter!!.getPresenter(Api.INFO_DETAILS, nHeadMap, InfoDetailsBean::class.java, prams)
        } else {
            mPresenter!!.getPresenter(Api.INFO_DETAILS, headMap, InfoDetailsBean::class.java, prams)
            mPresenter!!.getPresenter(Api.USER_INTEGRAL, headMap, UserIntegralBean::class.java, map)
        }
        integral_back.setOnClickListener {
            onBackPressed()
        }

    }

    override fun View(any: Any) {
        var id = intent.getIntExtra("id", 1)
        var sp = getSharedPreferences("config", Context.MODE_PRIVATE)
        var userId = sp.getString("userId", "")
        var sessionId = sp.getString("sessionId", "")
        var headMap: Map<String, Any> = mapOf(Pair("userId", userId), Pair("sessionId", sessionId))

        if (any is InfoDetailsBean) {
            var infoDetailsBean: InfoDetailsBean = any
            // Toast.makeText(this, infoDetailsBean.message, Toast.LENGTH_LONG).show()
            val result = infoDetailsBean.result
            val uri = Uri.parse(result.thumbnail)
            integral_simpleview.setImageURI(uri)
            integral_summary.text = result.summary
            integral_title.text = result.title
            integral_source.text = result.source
            val date = Date(result.releaseTime)
            val sdf = SimpleDateFormat("yyyy-MM-dd  hh:mm")
            val s = sdf.format(date)
            integral_time.text = s
            integral_share_num.text = "${result.share}"
            integral_collect_num.text = "${result.praise}"
            need_integral.text = "${result.integralCost}"
            if (result.whetherCollection == 1) {
                integral_collect.setImageResource(R.mipmap.common_icon_collect_s)
            } else {
                integral_collect.setImageResource(R.mipmap.common_icon_collect_n)
            }
            var prams: Map<String, Any> = mapOf(Pair("infoId", id), Pair("integralCost", result.integralCost))
            btn_dui.setOnClickListener {
                mPresenter!!.postPresenter(Api.INFO_PAY_INTEGRRAL, headMap, UserPublicBean::class.java, prams)
            }

        } else if (any is UserIntegralBean) {
            var userIntegralBean: UserIntegralBean = any
            //Toast.makeText(this, userIntegralBean.message, Toast.LENGTH_LONG).show()
            val integralResult = userIntegralBean.result
            my_integral.text = "${integralResult.amount}"
        } else if (any is UserPublicBean) {
            var userPublicBean: UserPublicBean = any
            if (userPublicBean.message.equals("兑换成功")) {
                showMyDialog()
            } else {
                showErrorDialog(userPublicBean.message)
            }

        }
    }

    private fun showErrorDialog(message: String) {
        val builder = AlertDialog.Builder(this)
        val view = android.view.View.inflate(this, R.layout.dialog_layout, null)
        builder.setView(view)
        builder.setCancelable(false)
        val dialog = builder.create()
        dialog.show()
        val alert_error_qu = view.findViewById<ImageView>(R.id.alert_error_qu)
        val error_text = view.findViewById<TextView>(R.id.error_text)
        val text_error_go = view.findViewById<TextView>(R.id.text_error_go)
        val text_error_qu = view.findViewById<TextView>(R.id.text_error_qu)
        alert_error_qu.setOnClickListener {
            dialog.dismiss()
        }
        error_text.text = message
        text_error_go.setOnClickListener {
            dialog.dismiss()
        }
        text_error_qu.setOnClickListener {
            dialog.dismiss()
        }

    }

    private fun showMyDialog() {
        val builder = AlertDialog.Builder(this)
        val view = android.view.View.inflate(this, R.layout.dialog_layout, null)
        builder.setView(view)
        builder.setCancelable(false)
        val dialog = builder.create()
        dialog.show()
        val alert_qu = view.findViewById<ImageView>(R.id.alert_qu)
        val alert_btn = view.findViewById<TextView>(R.id.alert_btn)
        alert_btn.setOnClickListener {
            finish()
        }
        alert_qu.setOnClickListener {
            dialog.dismiss()
        }
    }


}

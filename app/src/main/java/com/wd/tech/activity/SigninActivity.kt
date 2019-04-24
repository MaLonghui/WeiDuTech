package com.wd.tech.activity

import android.content.Context
import android.widget.Toast
import com.wd.tech.R
import com.wd.tech.api.Api
import com.wd.tech.base.BaseActivity
import com.wd.tech.bean.SigninTurnBean
import com.wd.tech.bean.TaskBean
import com.wd.tech.bean.TimeBean
import com.wd.tech.bean.UserPublicBean
import com.wd.tech.mvp.Constanct
import com.wd.tech.mvp.Presenter
import kotlinx.android.synthetic.main.activity_signin.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class SigninActivity : BaseActivity<Constanct.View, Constanct.Presenter>(), Constanct.View {
    var year: Int? = null
    var month: Int? = null
    var list: ArrayList<String>? = null
    override fun getLayoutId(): Int {
        return R.layout.activity_signin
    }

    override fun initPresenter(): Constanct.Presenter {
        return Presenter()
    }

    override fun initData() {
        val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd")
        val date = Date(System.currentTimeMillis())
        val format = simpleDateFormat.format(date)
        tv_sign_year_month.text = format
        i8show_attention_back.setOnClickListener {
            onBackPressed()
        }
        val sp = getSharedPreferences("config", Context.MODE_PRIVATE)
        val userId = sp.getString("userId", "")
        val sessionId = sp.getString("sessionId", "")
        val map: Map<String, String> = mapOf(Pair("userId", userId), Pair("sessionId", sessionId))
        val mapcan: Map<String, Any> = mapOf()
//        签到日期显示
        mPresenter!!.getPresenter(Api.TIMEDAYSIGN, map, TimeBean::class.java, mapcan)
        btn_sign.setOnClickListener {
            //           连续签到
            mPresenter!!.postPresenter(Api.SIGNIN, map, UserPublicBean::class.java, mapcan)
            btn_sign.text = "已签到"
        }
//        签到状态
        mPresenter!!.getPresenter(Api.SIGNINTURN, map, SigninTurnBean::class.java, mapcan)
    }

    override fun View(any: Any) {
        if (any is UserPublicBean) {
            var bean: UserPublicBean = any
            if (bean.status.equals("0000")) {
                Toast.makeText(this@SigninActivity, bean.message, Toast.LENGTH_LONG).show()
                val sp = getSharedPreferences("config", Context.MODE_PRIVATE)
                val userId = sp.getString("userId", "")
                val sessionId = sp.getString("sessionId", "")
                val map: Map<String, String> = mapOf(Pair("userId", userId), Pair("sessionId", sessionId))
                //签到任务
                val mapcanone: Map<String, Int> = mapOf(Pair("taskId", 1001))
                mPresenter!!.postPresenter(Api.ZUOTASK, map, TaskBean::class.java, mapcanone)
                val mapcan: Map<String, String> = mapOf()
                mPresenter!!.getPresenter(Api.TIMEDAYSIGN, map, TimeBean::class.java, mapcan)
            } else {
                Toast.makeText(this@SigninActivity, "已签到", Toast.LENGTH_LONG).show()
            }
        } else if (any is SigninTurnBean) {
            var bean: SigninTurnBean = any
            if (bean.result == 1) {
                btn_sign.text = "已签到"
            } else {
                btn_sign.text = "签到"
            }
        }
//       连续签到日期及显示
        if (any is TimeBean) {
            val bean: TimeBean = any
            if (bean != null) {
                list = ArrayList<String>()
                val result = bean.result
                for (i in result) {
                    list!!.add(result.toString())
                }
                sc_main.addMarks(result, 0)
            }

        }
    }

}

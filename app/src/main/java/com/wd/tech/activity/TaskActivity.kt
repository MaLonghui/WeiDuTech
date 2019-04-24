package com.wd.tech.activity

import android.content.Context
import com.wd.tech.R
import com.wd.tech.api.Api
import com.wd.tech.base.BaseActivity
import com.wd.tech.bean.TaskBean
import com.wd.tech.mvp.Constanct
import com.wd.tech.mvp.Presenter
import kotlinx.android.synthetic.main.activity_task.*

class TaskActivity : BaseActivity<Constanct.View, Constanct.Presenter>(), Constanct.View {

    override fun getLayoutId(): Int {
        return R.layout.activity_task
    }

    override fun initPresenter(): Constanct.Presenter {
        return Presenter()
    }

    override fun initData() {
        val sp = getSharedPreferences("config", Context.MODE_PRIVATE)
        val userId = sp.getString("userId", "")
        val sessionId = sp.getString("sessionId", "")
        val map: Map<String, String> = mapOf(Pair("userId", userId), Pair("sessionId", sessionId))
        val mapcan: Map<String, Any> = mapOf()
        mPresenter!!.getPresenter(Api.TASK, map, TaskBean::class.java, mapcan)

        val mapcanthress: Map<String, Int> = mapOf(Pair("taskId",1003))
        val mapcanfour: Map<String, Int> = mapOf(Pair("taskId",1004))

        val mapcansix: Map<String, Int> = mapOf(Pair("taskId",1006))
        val mapcanseven: Map<String, Int> = mapOf(Pair("taskId",1007))

        val mapcantwo: Map<String, Int> = mapOf(Pair("taskId",1002))
        mPresenter!!.postPresenter(Api.ZUOTASK, map, TaskBean::class.java, mapcantwo)
        mPresenter!!.postPresenter(Api.ZUOTASK, map, TaskBean::class.java, mapcanthress)
        mPresenter!!.postPresenter(Api.ZUOTASK, map, TaskBean::class.java, mapcanfour)


        mPresenter!!.postPresenter(Api.ZUOTASK, map, TaskBean::class.java, mapcansix)
        mPresenter!!.postPresenter(Api.ZUOTASK, map, TaskBean::class.java, mapcanseven)

    }

    override fun View(any: Any) {
        if (any is TaskBean) {
            val bean: TaskBean = any
            val result = bean.result
            if (result != null) {
                text_signin.text = "${result!![0].taskName}"
                text_signin_integral.text = "${result!![0].taskIntegral}"
                if (result!![0].status == 1) {
                    but_signin.text = "已做"
                } else if (result!![0].status == 2) {
                    but_signin.text = "未做"
                }
                text_first.text = "${result!![1].taskName}"
                text_first_integral.text = "${result!![1].taskIntegral}"
                if (result!![1].status == 1) {
                    but_first.text = "已做"
                } else if (result!![1].status == 2) {
                    but_first.text = "未做"
                }
                text_posting.text = "${result!![2].taskName}"
                text_posting_integral.text = "${result!![2].taskIntegral}"
                if (result!![2].status == 1) {
                    but_posting.text = "已做"
                } else if (result!![2].status == 2) {
                    but_posting.text = "未做"
                }
                text_share.text = "${result!![3].taskName}"
                text_share_integral.text = "${result!![3].taskIntegral}"
                if (result!![3].status == 1) {
                    but_share.text = "已做"
                } else if (result!![3].status == 2) {
                    but_share.text = "未做"
                }
                text_advertising.text = "${result!![4].taskName}"
                text_advertising_integral.text = "${result!![4].taskIntegral}"
                if (result!![4].status == 1) {
                    but_advertising.text = "已做"
                } else if (result!![4].status == 2) {
                    but_advertising.text = "未做"
                }
                text_message.text = "${result!![5].taskName}"
                text_message_integral.text = "${result!![5].taskIntegral}"
                if (result!![5].status == 1) {
                    but_message.text = "已做"
                } else if (result!![5].status == 2) {
                    but_message.text = "未做"
                }
                text_bind.text = "${result!![6].taskName}"
                text_bind_integral.text = "${result!![6].taskIntegral}"
                if (result!![6].status == 1) {
                    but_bind.text = "已做"
                } else if (result!![6].status == 2) {
                    but_bind.text = "未做"
                }
            }
        }
    }
}

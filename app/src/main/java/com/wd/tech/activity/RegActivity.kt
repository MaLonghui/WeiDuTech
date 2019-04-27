package com.wd.tech.activity

import android.widget.Toast
import cn.jpush.im.android.api.JMessageClient
import cn.jpush.im.api.BasicCallback
import com.wd.tech.R
import com.wd.tech.api.Api
import com.wd.tech.base.BaseActivity
import com.wd.tech.bean.RegBean
import com.wd.tech.mvp.Constanct
import com.wd.tech.mvp.Presenter
import com.wd.tech.utils.JumpActivityUtils
import com.wd.tech.utils.RsaCoder
import kotlinx.android.synthetic.main.activity_reg.*

/**
 * date:2019/4/12
 * author:冯泽林{2019/4/12}
 * function:
 * 注册
 */
class RegActivity : BaseActivity<Constanct.View, Constanct.Presenter>(), Constanct.View {
    override fun View(any: Any) {
        var regBean = any as RegBean
        if (regBean.status.equals("0000"))
            Toast.makeText(this, regBean.message, Toast.LENGTH_LONG).show()
        JumpActivityUtils.skipAnotherActivity(this@RegActivity, LoginActivity::class.java)

    }

    override fun getLayoutId(): Int {
        return R.layout.activity_reg
    }

    override fun initPresenter(): Constanct.Presenter {
        return Presenter()
    }

    override fun initData() {
//        注册页面
        but_reg.setOnClickListener {
            val name = edit_name.text.toString().trim()
            val phone = edit_phone.text.toString().trim()
            val pwd = edit_pwd.text.toString().trim()
            val pwd1 = RsaCoder.encryptByPublicKey(pwd)
            if (name != null && phone != null && pwd1 != null) {
                var map: Map<String, Any> = mapOf()
                var pair: Map<String, Any> = mapOf(Pair("nickName", name), Pair("phone", phone), Pair("pwd", pwd1))
                mPresenter!!.postPresenter(Api.REG_URL, map, RegBean::class.java, pair)
            }
            JMessageClient.register(phone, pwd, object : BasicCallback() {
                override fun gotResult(p0: Int, p1: String?) {
                    if (p0 == 0) {
                        Toast.makeText(this@RegActivity, "J注册成功", Toast.LENGTH_LONG).show()
                    }
                }
            })
        }

    }
}
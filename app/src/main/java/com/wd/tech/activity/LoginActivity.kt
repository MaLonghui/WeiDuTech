package com.wd.tech.activity

import android.content.Context
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.widget.Toast
import cn.jpush.im.android.api.JMessageClient
import cn.jpush.im.api.BasicCallback
import com.tencent.mm.opensdk.modelmsg.SendAuth
import com.wd.tech.R
import com.wd.tech.api.Api
import com.wd.tech.base.BaseActivity
import com.wd.tech.bean.LoginBean
import com.wd.tech.mvp.Constanct
import com.wd.tech.mvp.Presenter
import com.wd.tech.utils.JumpActivityUtils
import com.wd.tech.utils.RsaCoder
import com.wd.tech.utils.WeiXinUtil
import kotlinx.android.synthetic.main.activity_my_login.*

/**
 * date:2019/4/12
 * author:冯泽林{2019/4/12}
 * function:
 */
class LoginActivity : BaseActivity<Constanct.View, Constanct.Presenter>(), Constanct.View {
    var flag: Boolean = true
    override fun View(any: Any) {
        val loginBean = any as LoginBean
        if (loginBean.status.equals("0000")) {
            val sp = getSharedPreferences("config", Context.MODE_PRIVATE)
            val edit = sp.edit()
            edit.putString("userId", loginBean.result.userId.toString())
            edit.putString("sessionId", loginBean.result.sessionId.toString())
            edit.putString("headPic", loginBean.result.headPic)
            edit.putString("nickName", loginBean.result.nickName)
            edit.putString("signature",loginBean.result.signature)
            edit.putString("phone",loginBean.result.phone)
            edit.commit()
            if (loginBean.result != null)
              //  Toast.makeText(this, loginBean.message, Toast.LENGTH_SHORT).show()
            finish()
            edit.putString("signature", loginBean.result.signature)
            edit.commit()
            if (loginBean.result != null) {
                Toast.makeText(this, loginBean.message, Toast.LENGTH_SHORT).show()
                finish()
            }

            edit.putString("signature",loginBean.result.signature)
            edit.commit()
            if (loginBean.result != null)
                Toast.makeText(this, loginBean.message, Toast.LENGTH_SHORT).show()
            finish()
        } else {
            Toast.makeText(this, loginBean.message, Toast.LENGTH_SHORT).show()
        }

    }

    override fun getLayoutId(): Int {
        return R.layout.activity_my_login
    }

    override fun initPresenter(): Constanct.Presenter {
        return Presenter()
    }

    override fun initData() {

        but_login.setOnClickListener {
            var phone = edit_phone.text.toString().trim()
            var pwd = edit_pwd.text.toString().trim()
            val pwd1 = RsaCoder.encryptByPublicKey(pwd)
            var sp = getSharedPreferences("config", Context.MODE_PRIVATE)

            val edit = sp.edit()
            edit.putString("phone",phone)
            edit.putString("pwd",pwd)
            edit.commit()


            if (phone != null && pwd1 != null) {
                var map: Map<String, Any> = mapOf()
                var map1: Map<String, Any> = mapOf(Pair("phone", phone), Pair("pwd", pwd1))
                mPresenter!!.postPresenter(Api.LOGIN_URL, map, LoginBean::class.java, map1)
                JMessageClient.login(phone, pwd, object : BasicCallback(){
                    override fun gotResult(p0: Int, p1: String?) {
                        if (p0 == 0){
                            Toast.makeText(this@LoginActivity,"J登录成功",Toast.LENGTH_LONG).show()
                        }
                    }

                })
            }
        }
        iamge_eye.setOnClickListener {
            if (flag) {
                edit_pwd.transformationMethod = HideReturnsTransformationMethod.getInstance()
            } else {
                edit_pwd.transformationMethod = PasswordTransformationMethod.getInstance()
            }
            flag = !flag

        }
//        跳转到注册页面
        text_reg.setOnClickListener {
            JumpActivityUtils.skipAnotherActivity(this@LoginActivity, RegActivity::class.java)
        }
        image_wx.setOnClickListener {
            if (!WeiXinUtil.success(this)) {
                Toast.makeText(this, "请先安装应用", Toast.LENGTH_SHORT).show()
            } else {
                //  验证
                val req = SendAuth.Req()
                req.scope = "snsapi_userinfo"
                req.state = "wechat_sdk_demo_test_neng"
                WeiXinUtil.reg(this@LoginActivity)!!.sendReq(req)
            }
        }
        iamge_face.setOnClickListener {

        }
    }
}
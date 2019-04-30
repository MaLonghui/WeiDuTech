package com.wd.tech.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.widget.Toast
import cn.jpush.im.android.api.JMessageClient
import cn.jpush.im.api.BasicCallback
import cn.smssdk.EventHandler
import cn.smssdk.SMSSDK
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

    var i: Int = 30
    override fun View(any: Any) {
        if (any is RegBean) {
            var regBean = any
            Toast.makeText(this,regBean.message,Toast.LENGTH_LONG).show()
            if (regBean.status.equals("0000")) {
                Toast.makeText(this, regBean.message, Toast.LENGTH_LONG).show()
                JumpActivityUtils.skipAnotherActivity(this@RegActivity, LoginActivity::class.java)
            }

        }

        SMSSDK.setAskPermisionOnReadContact(true)
        val eventHandler = object : EventHandler() {

            override fun afterEvent(event: Int, result: Int, data: Any?) {

                super.afterEvent(event, result, data)
                var message = Message()
                message.arg1 = event
                message.arg2 = result
                message.obj = data
                //发送给Handler
                handler.sendMessage(message)

            }

        }
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
                val note = edit_note.text.toString().trim()
                if (name != null && phone != null && pwd1 != null && note != null) {
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
            but_note.setOnClickListener {
                var phone = edit_phone.text.toString()
                SMSSDK.getVerificationCode("86", phone)
                Thread(object : Runnable {
                    override fun run() {
                        while (i > 0) {
                            handler.sendEmptyMessage(-9)
                            if (i <= 0) {
                                break
                            }
                            try {
                                Thread.sleep(1000)
                            } catch (e: InterruptedException) {
                                e.printStackTrace()
                            }
                            i--
                        }
                        handler.sendEmptyMessage(-8)
                    }

                }).start()
            }
        }
    }
        val handler = object : Handler() {

            override fun handleMessage(msg: Message?) {
                super.handleMessage(msg)
                if (msg!!.what == -9) {
                    but_note.setText("重新发送($i)")
                    //Toast.makeText(applicationContext,"重新发送($i)",Toast.LENGTH_SHORT).show()
                } else if (msg.what == -8) {
                    but_note.setText("获取验证码")
                    but_note.setClickable(true)
                    i = 30
                } else {
                    val event = msg.arg1
                    val result = msg.arg2
                    val data = msg.obj
                    if (result == SMSSDK.RESULT_COMPLETE) {
                        if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {// 提交验证码成功
                            Toast.makeText(applicationContext, "提交验证码成功", Toast.LENGTH_SHORT).show()

                            val intent = Intent(this@RegActivity, LoginActivity::class.java)

                            val bundle = Bundle()

                            bundle.putString("userName", edit_phone.getText().toString().trim())

                            intent.putExtras(bundle)

                            startActivity(intent)

                        }

                    } else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {

                        Toast.makeText(applicationContext, "正在获取验证码",

                                Toast.LENGTH_SHORT).show()


                    } else {

                        Toast.makeText(this@RegActivity, "验证码不正确", Toast.LENGTH_SHORT).show()

                        (data as Throwable).printStackTrace()

                    }

                }
            }
        }

}
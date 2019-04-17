package com.wd.tech.activity

import android.content.Context
import android.content.Intent
import android.text.InputType
import android.view.MotionEvent
import android.view.View
import android.widget.EditText
import android.widget.Toast
import com.wd.tech.R
import com.wd.tech.R.id.*
import com.wd.tech.api.Api
import com.wd.tech.base.BaseActivity
import com.wd.tech.bean.AltruserBean
import com.wd.tech.bean.IndividualBean
import com.wd.tech.mvp.Constanct
import com.wd.tech.mvp.Presenter
import com.wd.tech.utils.PhoneFormatCheckUtils
import kotlinx.android.synthetic.main.activity_individual_information.*
import java.lang.reflect.InvocationTargetException
import java.lang.reflect.Method


/*
*
*设置个人页面
*/
class IndividualInformationActivity : BaseActivity<Constanct.View, Constanct.Presenter>(), Constanct.View {
    override fun getLayoutId(): Int {
        return R.layout.activity_individual_information
    }

    override fun initPresenter(): Constanct.Presenter {
        return Presenter()
    }

    override fun initData() {
//        返回
        back_setting.setOnClickListener {
            finish()
        }
        val sp = this.getSharedPreferences("config", Context.MODE_PRIVATE)
        val userid = sp.getString("userId", "")
        val session = sp.getString("sessionId", "")
//        请求头
        val map: Map<String, Any> = mapOf(Pair("userId", userid), Pair("sessionId", session))
//        参数
        val mapcan: Map<String, Any> = mapOf()

        mPresenter!!.getPresenter(Api.INDIVIDUALINFORMATION, map, IndividualBean::class.java, mapcan)
        setting_name.setOnClickListener {
            val name = setting_name.text.toString().trim()
            //请求头
            val map: Map<String, Any> = mapOf(Pair("userId", userid), Pair("sessionId", session))
//        参数
            val mapcan: Map<String, Any> = mapOf(Pair("nickName", name))
            mPresenter!!.putPresenter(Api.ALTERUSER, map, AltruserBean::class.java, mapcan)
        }
//       修改密码
        res_pwd.setOnClickListener {
            startActivity(Intent(this@IndividualInformationActivity, ChangePasswordActivity::class.java))
        }
//        修改签名
        next_to.setOnClickListener {
            startActivity(Intent(this@IndividualInformationActivity, NextActivity::class.java))
        }

    }


    //    隐藏键盘
    fun hideSoftInputMethod(ed: EditText) {
        val currentVersion: Int = android.os.Build.VERSION.SDK_INT
        var methodName: String? = null
        if (currentVersion >= 16) {
//                4.2
            methodName = "setShowSoftInputOnFocus"
        } else if (currentVersion >= 14) {
//                4.0
            methodName = "setSoftInputShownOnFocus"
        }
        if (methodName == null) {
            ed.setInputExtras(InputType.TYPE_NULL)
        } else {
            val cls: Class<EditText> = EditText::class.java
            var setShowSoftInputOnFocus: Method? = null
            try {
                setShowSoftInputOnFocus = cls.getMethod(methodName, Boolean::class.java)
                setShowSoftInputOnFocus.isAccessible = true
                setShowSoftInputOnFocus.invoke(ed, false)
            } catch (e: NoSuchMethodException) {
                ed.setInputType(InputType.TYPE_NULL);
                e.printStackTrace()
            } catch (e: InvocationTargetException) {
                e.printStackTrace()
            } catch (e: IllegalAccessException) {
                e.printStackTrace()
            }
        }
    }

    override fun View(any: Any) {
        if (any is IndividualBean) {
            val bean: IndividualBean = any
            if (bean.status.equals("0000")) {
                setting_image.setImageURI(bean.result.headPic)
                setting_name.setText(bean.result.nickName)
                setting_phone.setText(bean.result.phone)
                val sex = bean.result.sex
                if (sex == 1) {
                    user_sex.setText("男")
                } else {
                    user_sex.setText("女")
                }
                email.setText("")
                time.setText("")
                JiFen.setText("${bean.result.integral}")
                if (bean.result.whetherVip == 2) {
                    vip.setText("普通用户")
                } else {
                    vip.setText("vip用户")
                }
            }
        }
        if (any is AltruserBean) {
            val alterBean: AltruserBean = any
            if (alterBean.status.equals("0000")) {
                Toast.makeText(this@IndividualInformationActivity, alterBean.message, Toast.LENGTH_LONG).show()
            }
        }
    }
}



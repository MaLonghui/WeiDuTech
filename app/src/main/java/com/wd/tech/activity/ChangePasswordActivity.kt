package com.wd.tech.activity

import android.content.Context
import android.widget.Toast
import com.wd.tech.R
import com.wd.tech.api.Api
import com.wd.tech.base.BaseActivity
import com.wd.tech.bean.UserPublicBean
import com.wd.tech.mvp.Constanct
import com.wd.tech.mvp.Presenter
import com.wd.tech.utils.RsaCoder
import kotlinx.android.synthetic.main.activity_change_password.*

class ChangePasswordActivity : BaseActivity<Constanct.View,Constanct.Presenter>(),Constanct.View {
    override fun getLayoutId(): Int {
        return R.layout.activity_change_password
    }

    override fun initPresenter(): Constanct.Presenter {
        return Presenter()
    }

    override fun initData() {
        //返回
        back_setting.setOnClickListener {
            finish()
        }
        val sp = getSharedPreferences("config", Context.MODE_PRIVATE)
        val userId = sp.getString("userId", "")
        val sessionId = sp.getString("sessionId", "")
        val map:Map<String,String> = mapOf(Pair("userId",userId), Pair("sessionId",sessionId))
        //修改密码
        but_comment.setOnClickListener {
            val oldpwd = edit_oldpwd.text.toString().trim()
            val newpwd = edit_newpwd.text.toString().trim()
            val oldpwdone = RsaCoder.encryptByPublicKey(oldpwd)
            val newpwdone = RsaCoder.encryptByPublicKey(newpwd)
            if(oldpwdone!=null&&newpwdone!=null){
                if(newpwdone.length<6){
                    Toast.makeText(this@ChangePasswordActivity,"密码不可低于6位",Toast.LENGTH_LONG).show()
                }else{
                   var mapcan:Map<String,Any> = mapOf(Pair("oldPwd",oldpwdone), Pair("newPwd",newpwdone))
                    mPresenter!!.putPresenter(Api.PWD,map,UserPublicBean::class.java,mapcan)
                }
            }
        }


    }

    override fun View(any: Any) {
        if(any is UserPublicBean){
            var bean:UserPublicBean=any
            Toast.makeText(this@ChangePasswordActivity,bean.message,Toast.LENGTH_LONG).show()
            finish()
        }
    }

}

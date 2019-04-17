package com.wd.tech.activity

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import com.wd.tech.R
import com.wd.tech.api.Api
import com.wd.tech.base.BaseActivity
import com.wd.tech.bean.AltruserBean
import com.wd.tech.mvp.Constanct
import com.wd.tech.mvp.Presenter
import kotlinx.android.synthetic.main.activity_next.*

class NextActivity : BaseActivity<Constanct.View,Constanct.Presenter>(),Constanct.View{
    override fun getLayoutId(): Int {
        return R.layout.activity_next
    }

    override fun initPresenter(): Constanct.Presenter {
        return Presenter()
    }

    override fun initData() {
        but_autograph.setOnClickListener{
            val trim = text_autograph.text.toString().trim()
            if(!TextUtils.isEmpty(trim)){
                val sp = getSharedPreferences("config", Context.MODE_PRIVATE)
                val userId = sp.getString("userId", "")
                val sessionId = sp.getString("sessionId", "")
                val map:Map<String ,Any> = mapOf(Pair("userId",userId), Pair("sessionId",sessionId))

                val mappedbut:Map<String ,Any> = mapOf(Pair("signature",trim))
                mPresenter!!.putPresenter(Api.SIGNATURE,map, AltruserBean::class.java,mappedbut)
            }else{
                Toast.makeText(this@NextActivity,"内容不能为空",Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun View(any: Any) {
        if(any is AltruserBean){
            val bean:AltruserBean =any
            if (bean.status.equals("0000"))
                Toast.makeText(this@NextActivity,bean.message,Toast.LENGTH_LONG).show()
                finish()
        }
    }
}

package com.wd.tech.Fragment

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import com.bumptech.glide.load.resource.bitmap.TransformationUtils
import com.wd.tech.R
import com.wd.tech.activity.IndividualInformationActivity
import com.wd.tech.activity.LoginActivity
import com.wd.tech.base.BaseFragment
import com.wd.tech.mvp.Constanct
import com.wd.tech.mvp.Presenter
import com.wd.tech.utils.JumpActivityUtils
import kotlinx.android.synthetic.main.fragment_mine.*


class MineFragment : BaseFragment<Constanct.View, Constanct.Presenter>(), Constanct.View, View.OnClickListener {
    override fun onClick(v: View?) {
        when(v){
            item_individualinformation->{
            }
        }
    }

    override fun View(any: Any) {

    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_mine
    }

    override fun initPresenter(): Constanct.Presenter {
        return Presenter()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val pf:SharedPreferences=activity!!.getSharedPreferences("config", Context.MODE_PRIVATE)
        val userId = pf.getString("userId", "")
        val sessionId = pf.getString("sessionId", "")
        val headPic = pf.getString("headPic", "")
        val nickName = pf.getString("nickName", "")
//        头像和名字
        my_icon_simple.setImageURI(headPic)
        text_name.text=nickName
        if(!userId.equals("")&&!sessionId.equals("")){
            no_login_relative.visibility=View.GONE
            my_linear.visibility=View.VISIBLE
        }else {
            no_login_relative.visibility=View.VISIBLE
            my_linear.visibility=View.GONE
        }
//        登录
        no_login_relative.setOnClickListener {
            var it:Intent= Intent(activity,LoginActivity::class.java)
            startActivity(it)
        }
        item_collect.setOnClickListener(this)
        item_follow.setOnClickListener(this)
        item_individualinformation.setOnClickListener{
            JumpActivityUtils.skipAnotherActivity(context as Activity,IndividualInformationActivity::class.java)
        }
    }

    override fun initData() {


    }


}

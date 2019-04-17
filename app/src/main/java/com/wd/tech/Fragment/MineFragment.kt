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


class MineFragment : BaseFragment<Constanct.View, Constanct.Presenter>(), Constanct.View {

    var userId: String? = null
    var sessionId: String? = null
    var headPic: String? = null
    var nickName: String? = null

    override fun onResume() {
        super.onResume()
        val sp = activity!!.getSharedPreferences("config", Context.MODE_PRIVATE)
        userId = sp.getString("userId", "")
        sessionId = sp.getString("sessionId", "")
        headPic = sp.getString("headPic", "")
        nickName = sp.getString("nickName", "")
        my_icon_simple.setImageURI(headPic)
        text_name.text = nickName
        if (!userId.equals("") && !sessionId.equals("")) {
            no_login_relative.visibility = View.GONE
            my_linear.visibility = View.VISIBLE
        } else {
            no_login_relative.visibility = View.VISIBLE
            my_linear.visibility = View.GONE
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
        val pf: SharedPreferences = activity!!.getSharedPreferences("config", Context.MODE_PRIVATE)
        userId = pf.getString("userId", "")
        sessionId = pf.getString("sessionId", "")
        headPic = pf.getString("headPic", "")
        nickName = pf.getString("nickName", "")
//        头像和名字
        my_icon_simple.setImageURI(headPic)
        text_name.text = nickName
        if (!userId.equals("") && !sessionId.equals("")) {
            no_login_relative.visibility = View.GONE
            my_linear.visibility = View.VISIBLE
        } else {
            no_login_relative.visibility = View.VISIBLE
            my_linear.visibility = View.GONE
        }
//        登录
        no_login_relative.setOnClickListener {
            var it: Intent = Intent(activity, LoginActivity::class.java)
            startActivity(it)

        }

    }

    override fun initData() {


    }


}

package com.wd.tech.Fragment

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Toast
import com.wd.tech.R
import com.wd.tech.activity.*
import com.wd.tech.base.BaseFragment
import com.wd.tech.mvp.Constanct
import com.wd.tech.mvp.Presenter
import kotlinx.android.synthetic.main.fragment_mine.*


class MineFragment : BaseFragment<Constanct.View, Constanct.Presenter>(), Constanct.View {



    var headPic: String? = null
    var nickName: String? = null
    var signature: String? = null
    var userId: String? = null
    var sessionId: String? = null
    var pf: SharedPreferences? = null



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
        pf = activity!!.getSharedPreferences("config", Context.MODE_PRIVATE)
        userId = pf!!.getString("userId", "")
        sessionId = pf!!.getString("sessionId", "")
        headPic = pf!!.getString("headPic", "")
        nickName = pf!!.getString("nickName", "")
//        头像和名字
        my_icon_simple.setImageURI(headPic)
        text_name.text = nickName
        if (!userId.equals("") && !sessionId.equals("")) {
            no_login_relative.visibility = GONE
            my_linear.visibility = VISIBLE
        } else {
            no_login_relative.visibility = VISIBLE
            my_linear.visibility = GONE
        }
//        登录
        no_login_relative.setOnClickListener {
            var it: Intent = Intent(activity, LoginActivity::class.java)
            startActivity(it)

        }



        //签到
        img_qd.setOnClickListener {
            startActivity(Intent(this.context!!,SigninActivity::class.java))
        }
        //收藏
        item_collect.setmOnLSettingItemClick {
            startActivity(Intent(this.context!!, CollectActivity::class.java))
        }
        //关注
        item_follow.setmOnLSettingItemClick {
            startActivity(Intent(this.context!!, FollowActivity::class.java))
        }
        //帖子
        item_card.setmOnLSettingItemClick {
            startActivity(Intent(this.context!!, CardActivity::class.java))
        }
        //积分
        item_integral.setmOnLSettingItemClick {
            startActivity(Intent(this.context!!,MyIntegralActivity::class.java))
        }
        //任务
        item_task.setmOnLSettingItemClick {
            startActivity(Intent(this.context!!,TaskActivity::class.java))
        }
        //设置
        item_individualinformation.setmOnLSettingItemClick {
            startActivity(Intent(this.context!!, IndividualInformationActivity::class.java))
        }
        //我的通知
        item_notify.setmOnLSettingItemClick {
            startActivity(Intent(this.context!!,NotifyActivity::class.java))
        }
        val pf: SharedPreferences = activity!!.getSharedPreferences("config", Context.MODE_PRIVATE)
        userId = pf.getString("userId", "")
        sessionId = pf.getString("sessionId", "")
        headPic = pf.getString("headPic", "")
        nickName = pf.getString("nickName", "")
    }

    override fun onResume() {
        super.onResume()
        pf = activity!!.getSharedPreferences("config", Context.MODE_PRIVATE)
        userId = pf!!.getString("userId", "")
        sessionId = pf!!.getString("sessionId", "")
        headPic = pf!!.getString("headPic", "")
        nickName = pf!!.getString("nickName", "")
        signature = pf!!.getString("signature", "")
//        头像和名字
        my_icon_simple.setImageURI(headPic)
        text_name.text = nickName
        text_qm.text = signature

        if (userId!!.isEmpty() || sessionId!!.isEmpty()) {
            no_login_relative.visibility = VISIBLE
            my_linear.visibility = GONE
//        登录
            no_login_relative.setOnClickListener {
                var it: Intent = Intent(activity, LoginActivity::class.java)
                startActivity(it)
            }
        } else {
            no_login_relative.visibility = GONE
            my_linear.visibility = VISIBLE
        }

    }

    override fun initData() {

    }


}

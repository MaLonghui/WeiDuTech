package com.wd.tech.Fragment

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
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

        // 收藏
        item_collect.setmOnLSettingItemClick {
            startActivity(Intent(this.context!!, CollectActivity::class.java))
        }
        // 关注
        item_follow.setmOnLSettingItemClick {
            startActivity(Intent(this.context!!, FollowActivity::class.java))
        }
        //帖子
        item_card.setmOnLSettingItemClick {
            startActivity(Intent(this.context!!, CardActivity::class.java))
        }
        // 设置
        item_individualinformation.setmOnLSettingItemClick {
            startActivity(Intent(this.context!!, IndividualInformationActivity::class.java))
        }
    }

    override fun onResume() {
        super.onResume()
        pf = activity!!.getSharedPreferences("config", Context.MODE_PRIVATE)
        userId = pf!!.getString("userId", null)
        sessionId = pf!!.getString("sessionId", null)
        headPic = pf!!.getString("headPic", null)
        nickName = pf!!.getString("nickName", null)
        signature = pf!!.getString("signature", null)
//        头像和名字
        my_icon_simple.setImageURI(headPic)
        text_name.text = nickName
        text_qm.text = signature

        if (userId!!.isEmpty() || sessionId!!.isEmpty()) {
            no_login_relative.visibility = android.view.View.VISIBLE
            my_linear.visibility = android.view.View.GONE
            //        登录
            no_login_relative.setOnClickListener {
                var it: Intent = Intent(context, LoginActivity::class.java)
                startActivity(it)
            }
        } else {
            no_login_relative.visibility = android.view.View.GONE
            my_linear.visibility = android.view.View.VISIBLE
        }

    }

    override fun initData() {
    }


}

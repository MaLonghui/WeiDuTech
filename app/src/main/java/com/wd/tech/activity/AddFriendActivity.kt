package com.wd.tech.activity

import android.content.Context
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Toast
import com.wd.tech.R
import com.wd.tech.api.Api
import com.wd.tech.base.BaseActivity
import com.wd.tech.bean.CheckFriendBean
import com.wd.tech.bean.FriendInfomation
import com.wd.tech.mvp.Constanct
import com.wd.tech.mvp.Presenter
import com.wd.tech.utils.JumpActivityUtils
import kotlinx.android.synthetic.main.activity_add_friend.*
import java.text.SimpleDateFormat
import java.util.*

class AddFriendActivity : BaseActivity<Constanct.View, Constanct.Presenter>(), Constanct.View {
    override fun getLayoutId(): Int {
        return R.layout.activity_add_friend
    }

    override fun initPresenter(): Constanct.Presenter {
        return Presenter()
    }

    override fun initData() {
        val userid = intent.getIntExtra("userId", 1)
        var sp = getSharedPreferences("config", Context.MODE_PRIVATE)
        var userId = sp.getString("userId", "")
        var sessionId = sp.getString("sessionId", "")
        var sHeadMap = mapOf(Pair("userId", userId), Pair("sessionId", sessionId))
        mPresenter!!.getPresenter(Api.FRIEND_INFORMATION, sHeadMap, FriendInfomation::class.java, mapOf(Pair("friend", userid)))
        //Toast.makeText(this,phone,Toast.LENGTH_LONG).show()

        mPresenter!!.getPresenter(Api.CHECK_MYFRIEND, sHeadMap, CheckFriendBean::class.java, mapOf(Pair("friendUid", userid)))


    }

    override fun View(any: Any) {
        val userid = intent.getIntExtra("userId", 1)
        if (any is CheckFriendBean) {

            var checkFriendBean: CheckFriendBean = any
            if (checkFriendBean.flag == 1) {
                add_user_btn_n.text = "发送消息"
            } else {
                add_user_btn_n.text = "添加为好友"
            }

        } else if (any is FriendInfomation) {
            var friendInfomation: FriendInfomation = any
            val infomationResult = friendInfomation.result
            val uri = Uri.parse(infomationResult.headPic)
            add_user_head_n.setImageURI(uri)
            add_user_name_n.text = infomationResult.nickName
            add_user_integral_n.text = "(${infomationResult.integral}积分)"
            add_user_btn_n.setOnClickListener {
                if (add_user_btn_n.text.toString().equals("添加为好友")) {
                    var map = hashMapOf(Pair("friendUid", userid),
                            Pair("headPic", infomationResult.headPic),
                            Pair("nickName", infomationResult.nickName),
                            Pair("signature", infomationResult.signature)
                    )
                    JumpActivityUtils.skipValueActivity(this, SendAddFriendActivity::class.java, map)
                } else {
                    var map = hashMapOf(Pair("nickName", infomationResult.nickName)
                            , Pair("headPic", infomationResult.headPic),
                            Pair("signature", infomationResult.signature)

                    )
                    JumpActivityUtils.skipValueActivity(this, SendMesageActivity::class.java, map)

                }


            }


            if (infomationResult.whetherVip == 1) {
                add_user_vip_n.visibility = VISIBLE
            } else {
                add_user_vip_n.visibility = GONE
            }
            add_user_qian_n.text = infomationResult.signature
            val date = Date(infomationResult.birthday)
            val format = SimpleDateFormat("yyyy-MM-dd")
            val s = format.format(date)
            if (infomationResult.sex == 1) {
                add_user_sex_bir_n.text = "男（${s}）"
            } else {
                add_user_sex_bir_n.text = "女（${s}）"
            }
            val phoneNum = infomationResult.phone.substring(0, 3) + "****" + infomationResult.phone.substring(7, infomationResult.phone.length)
            add_user_phone_n.text = phoneNum
            if (infomationResult.email == null) {
                add_user_eamil_n.text = "暂无邮箱"
            } else {
                add_user_eamil_n.text = infomationResult.email
            }
            val myGroupList = infomationResult.myGroupList
            if (myGroupList!!.size > 0) {
                add_user_creat_group.text = myGroupList[0].groupName
            } else {
                add_user_creat_group.text = "Ta还没有创建过群"
            }

        }


    }


}

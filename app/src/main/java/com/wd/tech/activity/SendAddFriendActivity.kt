package com.wd.tech.activity

import android.content.Context
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import com.wd.tech.R
import com.wd.tech.api.Api
import com.wd.tech.base.BaseActivity
import com.wd.tech.bean.UserPublicBean
import com.wd.tech.mvp.Constanct
import com.wd.tech.mvp.Presenter
import com.wd.tech.utils.JumpActivityUtils
import kotlinx.android.synthetic.main.activity_send_add_friend.*

class SendAddFriendActivity : BaseActivity<Constanct.View, Constanct.Presenter>(), Constanct.View {
    override fun getLayoutId(): Int {
        return R.layout.activity_send_add_friend
    }

    override fun initPresenter(): Constanct.Presenter {
        return Presenter()
    }

    override fun initData() {
        var sp = getSharedPreferences("config", Context.MODE_PRIVATE)
        var userId = sp.getString("userId", "")
        var sessionId = sp.getString("sessionId", "")
        var sHeadMap = mapOf(Pair("userId", userId), Pair("sessionId", sessionId))

        val signature = intent.getStringExtra("signature")
        val friendUid = intent.getIntExtra("friendUid",1)
        val headPic = intent.getStringExtra("headPic")
        val nickName = intent.getStringExtra("nickName")
        val uri = Uri.parse(headPic)
        var prams: Map<String, Any>? = null
        send_friend_icon.setImageURI(uri)
        send_friend_name.text = nickName
        send_friend_qian.text = signature
        send_friend_back.setOnClickListener {
            onBackPressed()
        }
        val remark = send_friend_edit.text.toString()
        if (TextUtils.isEmpty(remark)) {
            prams = mapOf(Pair("friendUid", friendUid), Pair("remark", remark))
        } else {
            prams = mapOf(Pair("friendUid", friendUid), Pair("remark", nickName))
        }
        text_send.setOnClickListener {
            mPresenter!!.postPresenter(Api.SEND_FRIEND, sHeadMap, UserPublicBean::class.java, prams)
        }

    }

    override fun View(any: Any) {
        var userPublicBean:UserPublicBean = any as UserPublicBean
        Toast.makeText(this,userPublicBean.message,Toast.LENGTH_LONG).show()
        if (userPublicBean.status.equals("0000")){
            JumpActivityUtils.skipAnotherActivity(this,AddFriendOrGroupActivity::class.java)
            finish()
        }
    }


}

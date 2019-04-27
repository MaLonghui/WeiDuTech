package com.wd.tech.activity

import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.wd.tech.R
import com.wd.tech.base.BaseActivity
import com.wd.tech.mvp.Constanct
import com.wd.tech.mvp.Presenter
import kotlinx.android.synthetic.main.activity_user_setting.*

class UserSettingActivity : BaseActivity<Constanct.View,Constanct.Presenter>(),Constanct.View {
    override fun getLayoutId(): Int {
        return R.layout.activity_user_setting
    }

    override fun initPresenter(): Constanct.Presenter {
        return Presenter()
    }

    override fun initData() {
        val headPic = intent.getStringExtra("headPic")
        val nickName = intent.getStringExtra("nickName")
        val signature = intent.getStringExtra("signature")
        val uri = Uri.parse(headPic)
        setting_user_img.setImageURI(uri)
        setting_user_name.text = nickName
        setting_user_qian.text = signature
    }

    override fun View(any: Any) {
    }


}

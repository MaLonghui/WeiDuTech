package com.wd.tech.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.wd.tech.R
import com.wd.tech.api.Api
import com.wd.tech.base.BaseActivity
import com.wd.tech.bean.BannerBean
import com.wd.tech.mvp.Constanct
import com.wd.tech.mvp.Presenter

class MyCollectActivity : BaseActivity<Constanct.View,Constanct.Presenter>(),Constanct.View {


    override fun getLayoutId(): Int {
        return R.layout.activity_my_collect
    }

    override fun initPresenter(): Constanct.Presenter {
        return Presenter()
    }


    override fun initData() {

    }

    override fun View(any: Any) {

    }


}

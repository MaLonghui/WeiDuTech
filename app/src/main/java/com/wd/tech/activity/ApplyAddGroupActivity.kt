package com.wd.tech.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.wd.tech.R
import com.wd.tech.base.BaseActivity
import com.wd.tech.mvp.Constanct
import com.wd.tech.mvp.Presenter

class ApplyAddGroupActivity : BaseActivity<Constanct.View,Constanct.Presenter>(),Constanct.View {
    override fun getLayoutId(): Int {
        return R.layout.activity_apply_add_group
    }

    override fun initPresenter(): Constanct.Presenter {
        return Presenter()
    }

    override fun initData() {
    }

    override fun View(any: Any) {
    }


}

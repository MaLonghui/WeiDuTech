package com.wd.tech.activity

import com.wd.tech.R
import com.wd.tech.base.BaseActivity
import com.wd.tech.mvp.Constanct
import com.wd.tech.mvp.Presenter

class CollectActivity : BaseActivity<Constanct.View, Constanct.Presenter>() ,Constanct.View{
    override fun getLayoutId(): Int {
        return R.layout.activity_collect
    }

    override fun initPresenter(): Constanct.Presenter {
        return Presenter()
    }

    override fun initData() {

    }

    override fun View(any: Any) {

    }

}

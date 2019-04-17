package com.wd.tech.activity

import com.wd.tech.R
import com.wd.tech.base.BaseActivity
import com.wd.tech.mvp.Constanct
import com.wd.tech.mvp.Presenter

/**
 * 设置页面
 */
class IndividualInformationActivity : BaseActivity<Constanct.View,Constanct.Presenter>(),Constanct.View {
    override fun getLayoutId(): Int {
        return R.layout.activity_individual_information
    }

    override fun initPresenter(): Constanct.Presenter {
        return Presenter()
    }

    override fun initData() {

    }

    override fun View(any: Any) {

    }

}

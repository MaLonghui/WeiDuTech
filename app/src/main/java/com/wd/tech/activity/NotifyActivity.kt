package com.wd.tech.activity

import android.support.v7.widget.LinearLayoutManager
import com.wd.tech.R
import com.wd.tech.adapter.NotifyAdapter
import com.wd.tech.base.BaseActivity
import com.wd.tech.mvp.Constanct
import com.wd.tech.mvp.Presenter
import kotlinx.android.synthetic.main.activity_notify.*

class NotifyActivity : BaseActivity<Constanct.View,Constanct.Presenter>(),Constanct.View {
//    var adapter: NotifyAdapter?=null
    override fun getLayoutId(): Int {
        return R.layout.activity_notify
    }

    override fun initPresenter(): Constanct.Presenter {
        return Presenter()
    }

    override fun initData() {
    recycler_notifg.layoutManager=LinearLayoutManager(this@NotifyActivity,LinearLayoutManager.VERTICAL,false)
//        adapter= NotifyAdapter(this@NotifyActivity)
//        recycler_notifg.adapter=adapter
        back_setting.setOnClickListener {
            onBackPressed()
        }
    }

    override fun View(any: Any) {
    }

}

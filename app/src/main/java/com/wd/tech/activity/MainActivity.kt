package com.wd.tech.activity

import android.app.ActivityOptions
import android.content.Intent
import android.os.Looper
import com.wd.tech.R
import com.wd.tech.base.BaseActivity
import com.wd.tech.mvp.Constanct
import com.wd.tech.mvp.Presenter
import com.wd.tech.utils.JumpActivityUtils

import io.reactivex.Flowable
import java.util.concurrent.TimeUnit

class MainActivity : BaseActivity<Constanct.View, Constanct.Presenter>(),Constanct.View{
    override fun View(any: Any) {

    }

    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }

    override fun initPresenter(): Constanct.Presenter {
        return Presenter()
    }

    override fun initData() {
        Flowable.intervalRange(0, 3, 0, 1, TimeUnit.SECONDS)
                .doOnNext {

                }
                .doOnComplete {
                    Looper.prepare()
                    JumpActivityUtils.skipAnotherActivity(this@MainActivity,ShowActivity::class.java)
                    //startActivity(Intent(this@MainActivity, ShowActivity::class.java), ActivityOptions.makeSceneTransitionAnimation(this@MainActivity).toBundle())
                    Looper.loop()
                }
                .subscribe()
    }


}

package com.wd.tech.activity

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.os.Looper
import android.support.v7.app.AppCompatActivity
import com.wd.tech.R
import com.wd.tech.base.BaseActivity
import com.wd.tech.mvp.Constanct
import com.wd.tech.mvp.Presenter
import com.wd.tech.utils.JumpActivityUtils
import io.reactivex.Flowable
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Flowable.intervalRange(0, 3, 0, 1, TimeUnit.SECONDS)
                .doOnNext {
                }
                .doOnComplete {
                    Looper.prepare()
                    JumpActivityUtils.skipAnotherActivity(this@MainActivity,ShowActivity::class.java)
                    Looper.loop()
                }
                .subscribe()
    }


}

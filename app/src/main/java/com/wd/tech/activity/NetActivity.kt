
package com.wd.tech.activity
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.wd.tech.R
import com.wd.tech.utils.NetWorkUtils

import kotlinx.android.synthetic.main.activity_net.*

/**
 * 无网页面
 */
class NetActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_net)
        wang.setOnClickListener {
            if (NetWorkUtils.isNetworkAvailable(this@NetActivity)) {
                finish()
            }
        }
    }
}

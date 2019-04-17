package com.wd.tech.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.transition.Explode
import android.webkit.WebChromeClient
import android.webkit.WebViewClient
import android.widget.Toast
import com.wd.tech.R
import kotlinx.android.synthetic.main.activity_banner_details.*

/**
 * banner详情
 */
class BannerDetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_banner_details)
        val s = intent.getStringExtra("url")
        window.enterTransition = Explode().setDuration(1000)
        window.exitTransition = Explode().setDuration(1000)
        //设置在本页面加载，不调用系统浏览器
        banner_web_view.webViewClient= WebViewClient()
        val settings = banner_web_view.settings
        settings.javaScriptEnabled = true
        banner_web_view.loadUrl(s)

    }
}

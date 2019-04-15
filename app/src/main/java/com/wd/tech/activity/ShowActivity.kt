package com.wd.tech.activity

import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.transition.Explode
import com.hjm.bottomtabbar.BottomTabBar
import com.wd.tech.Fragment.CommunityFragment
import com.wd.tech.Fragment.InformationFragment
import com.wd.tech.Fragment.MessageFragment
import com.wd.tech.Fragment.MineFragment
import com.wd.tech.R
import kotlinx.android.synthetic.main.activity_show.*

class ShowActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show)
        window.enterTransition = Explode().setDuration(1000)
        window.exitTransition = Explode().setDuration(1000)
        bottom_ab_bar.init(supportFragmentManager)
                .setImgSize(90f, 90f)
                .setFontSize(12f)
                .setTabPadding(4f, 6f, 10f)
                .setChangeColor(Color.BLACK, Color.GRAY)
                .addTabItem("资讯", R.mipmap.common_tab_informatiion_s, R.mipmap.common_tab_information_n, InformationFragment::class.java)
                .addTabItem("消息", R.mipmap.common_tab_message_s, R.mipmap.common_tab_message_n, MessageFragment::class.java)
                .addTabItem("社区", R.mipmap.common_tab_community_s, R.mipmap.common_tab_community_n, CommunityFragment::class.java)
                .isShowDivider(false)
                .setOnTabChangeListener(object : BottomTabBar.OnTabChangeListener {
                    override fun onTabChange(position: Int, name: String?) {
                    }
                })
        val supportFragmentManager = supportFragmentManager
        var mineFragment = MineFragment()
        supportFragmentManager.beginTransaction()
                .replace(R.id.drawer_frame,mineFragment)
                .commit()
    }
}

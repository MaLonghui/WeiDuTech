package com.wd.tech.Fragment

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.Toast
import com.wd.tech.R
import com.wd.tech.activity.MainActivity
import com.wd.tech.activity.MyCollectActivity
import com.wd.tech.activity.ShowActivity
import com.wd.tech.base.BaseFragment
import com.wd.tech.mvp.Constanct
import com.wd.tech.mvp.Presenter
import com.wd.tech.utils.JumpActivityUtils
import kotlinx.android.synthetic.main.fragment_mine.*


class MineFragment : BaseFragment<Constanct.View, Constanct.Presenter>(), Constanct.View {
    override fun View(any: Any) {

    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_mine
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

    override fun initPresenter(): Constanct.Presenter {
        return Presenter()
    }

    override fun initData() {


    }


}

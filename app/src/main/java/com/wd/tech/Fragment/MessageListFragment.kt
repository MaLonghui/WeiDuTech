package com.wd.tech.Fragment

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.wd.tech.R
import com.wd.tech.adapter.MessageAdapter
import com.wd.tech.base.BaseFragment
import com.wd.tech.mvp.Constanct
import com.wd.tech.mvp.Presenter
import kotlinx.android.synthetic.main.fragment_message_list.*

class MessageListFragment : BaseFragment<Constanct.View,Constanct.Presenter>(),Constanct.View {
    var adapter:MessageAdapter ?=null
    override fun getLayoutId(): Int {
        return R.layout.fragment_message_list
    }

    override fun initPresenter(): Constanct.Presenter {
        return Presenter()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        recycler_message.layoutManager=LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)
//        adapter= MessageAdapter(this!!.context!!)
//        recycler_message.adapter=adapter
    }
    override fun initData() {
    }

    override fun View(any: Any) {
    }

}

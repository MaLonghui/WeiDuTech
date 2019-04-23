package com.wd.tech.Fragment


import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager

import com.wd.tech.R
import com.wd.tech.adapter.MyPagerAdapter
import com.wd.tech.base.BaseFragment
import com.wd.tech.mvp.Constanct
import com.wd.tech.mvp.Presenter
import kotlinx.android.synthetic.main.fragment_message.*

/**
 * 消息
 */
class MessageFragment : BaseFragment<Constanct.View, Constanct.Presenter>(), Constanct.View {
    var adapter: MyPagerAdapter? = null
    var mlist: MutableList<Fragment>? = null
    override fun getLayoutId(): Int {
        return R.layout.fragment_message
    }

    override fun initPresenter(): Constanct.Presenter {
        return Presenter()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        adapter = MyPagerAdapter(activity!!.supportFragmentManager)
        mlist = ArrayList<Fragment>()
        mlist!!.add(MessageListFragment())
        mlist!!.add(LinkManFragment())
        adapter!!.setFragmentList(mlist as ArrayList<Fragment>)
        view_pager.adapter = adapter
        view_pager.setOnPageChangeListener(object :ViewPager.OnPageChangeListener{
            override fun onPageScrollStateChanged(p0: Int) {

            }

            override fun onPageScrolled(p0: Int, p1: Float, p2: Int) {

            }

            override fun onPageSelected(p0: Int) {
                when(p0){
                    0->{
                        rb_msg.isChecked = true
                        rb_msg.setTextColor(Color.parseColor("#ffffff"))
                        rb_linkman.setTextColor(Color.parseColor("#333333"))
                    }
                    1->{
                        rb_linkman.isChecked = true
                        rb_msg.setTextColor(Color.parseColor("#333333"))
                        rb_linkman.setTextColor(Color.parseColor("#ffffff"))
                    }
                }
            }

        })

        radio_group.setOnCheckedChangeListener { group, checkedId ->
            when(checkedId){
                R.id.rb_msg->{
                    view_pager.currentItem = 0
                    rb_msg.setTextColor(Color.parseColor("#ffffff"))
                    rb_linkman.setTextColor(Color.parseColor("#333333"))
                }
                R.id.rb_linkman->{
                    view_pager.currentItem = 1
                    rb_msg.setTextColor(Color.parseColor("#333333"))
                    rb_linkman.setTextColor(Color.parseColor("#ffffff"))
                }
            }
        }
    }

    override fun initData() {


    }

    override fun View(any: Any) {

    }


}


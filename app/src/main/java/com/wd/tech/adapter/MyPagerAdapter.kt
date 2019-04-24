package com.wd.tech.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

class MyPagerAdapter(fm: FragmentManager?) : FragmentPagerAdapter(fm) {
    var fm: FragmentManager? = null
    var fragments: MutableList<Fragment>? = null

    init {
        this.fm = fm
        fragments = ArrayList()
    }
    fun setFragmentList(fragments: MutableList<Fragment>){
        this.fragments = fragments
        notifyDataSetChanged()
    }

    override fun getItem(p0: Int): Fragment {
        return fragments!!.get(p0)
    }

    override fun getCount(): Int {
        return fragments!!.size
    }
}
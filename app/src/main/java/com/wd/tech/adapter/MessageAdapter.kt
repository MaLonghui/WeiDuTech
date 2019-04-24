package com.wd.tech.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.wd.tech.R

/**
 * date:2019/4/23
 * author:冯泽林{2019/4/23}
 * function:
 */
class MessageAdapter(context:Context) :RecyclerView.Adapter<MessageAdapter.ViewHolder>(){
    var context:Context ?=null
//    var list:List<> ?=null
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        return ViewHolder(View.inflate(context,R.layout.item_message,null))
    }

    override fun getItemCount(): Int {
        return 0 //list!!.size
    }

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {

    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }
}
package com.wd.tech.adapter

import android.content.Context
import android.net.Uri
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.wd.tech.R
import com.wd.tech.bean.MessageBean
import kotlinx.android.synthetic.main.item_message.view.*

/**
 * date:2019/4/23
 * author:冯泽林{2019/4/23}
 * function:
 */
class MessageAdapter(context:Context) :RecyclerView.Adapter<MessageAdapter.ViewHolder>(){

    var context:Context ?=null
    var data:MutableList<MessageBean> ?=null
    init {
        this.context = context
        data= ArrayList()
    }
    fun setDataList(data:MutableList<MessageBean>){
        this.data = data
        notifyDataSetChanged()
    }
    fun addList(newList:MutableList<MessageBean>){
        if (null!=newList && newList!!.size>0){
            data!!.addAll(newList)
            notifyDataSetChanged()
        }
    }

    fun clear() {
        data!!.clear()
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        return ViewHolder(View.inflate(context,R.layout.item_message,null))
    }

    override fun getItemCount(): Int {
        if (null != data && data!!.size > 0) {
            return data!!.size
        }
        return 0
    }

    override fun onBindViewHolder(holder: ViewHolder, i: Int) {


        if (!data!!.equals("")){
            val uri = Uri.parse(data!![i].img)
            holder.itemView.message_image.setImageURI(uri)
            holder.itemView.text_name.text = data!![i].content
            holder.itemView.text_title.text = data!![i].title
            holder.itemView.text_time.text = data!![i].time
        }

    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }
}
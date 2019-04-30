package com.wd.tech.adapter

import android.content.Context
import android.net.Uri
import android.support.annotation.LayoutRes
import android.support.annotation.Nullable
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import cn.jpush.im.android.api.JMessageClient
import cn.jpush.im.android.api.model.Conversation
import cn.jpush.im.android.api.model.Message
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.wd.tech.R
import kotlinx.android.synthetic.main.message_adapter_layout.view.*

class MessagesListAdapter(@LayoutRes layoutResId: Int,@Nullable data: List<Message>)
    : BaseQuickAdapter<Message,BaseViewHolder>(layoutResId,data) {

    var friendHead:String ?= null
    var headPic:String?=null
    fun setUrl(friendHead:String,headPic:String){
        this.friendHead = friendHead
        this.headPic = headPic
    }


    override fun convert(holder: BaseViewHolder?, item: Message?) {
        val split = item!!.content.toJson().split("\"")
        val myInfo = JMessageClient.getMyInfo()
        if (myInfo.userName == item.fromID){
            holder!!.itemView.ll_send.visibility = VISIBLE
            holder!!.itemView.rl_receive.visibility = GONE
            holder!!.itemView.tv_msg_send.text = split[3]
            val parse = Uri.parse(headPic)
            holder!!.itemView.iv_avator_send.setImageURI(parse)
        }else{
            holder!!.itemView.ll_send.visibility = GONE
            holder!!.itemView.rl_receive.visibility = VISIBLE
            holder!!.itemView.tv_msg_receive.text = split[3]
            val parse = Uri.parse(friendHead)
            holder!!.itemView.iv_avator_recieve.setImageURI(parse)
        }
    }
}
package com.wd.tech.adapter

import android.content.Context
import android.net.Uri
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import com.wd.tech.R
import com.wd.tech.bean.FriendNoticeResult
import kotlinx.android.synthetic.main.new_friends_item_layout.view.*
import java.text.SimpleDateFormat
import java.util.*

class NewFriendsAdapter(context: Context) : RecyclerView.Adapter<NewFriendsAdapter.ViewHolder>() {
    var context: Context? = null
    var flag = 2
    var noticeResult: List<FriendNoticeResult>? = null
    private lateinit var listener :(Int,Int)->Unit
    fun setRadioClickListener(listener :(Int,Int)->Unit){
        this.listener = listener
    }

    init {
        this.context = context
        noticeResult = ArrayList()
    }
    fun setNoticeList(noticeResult: List<FriendNoticeResult>){
        this.noticeResult  = noticeResult
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.new_friends_item_layout, p0, false))
    }

    override fun getItemCount(): Int {
        return noticeResult!!.size
    }

    override fun onBindViewHolder(holder: ViewHolder, i: Int) {
        val uri = Uri.parse(noticeResult!![i].fromHeadPic)
        holder.itemView.newfriends_img.setImageURI(uri)
        val date = Date(noticeResult!![i].noticeTime)
        val format = SimpleDateFormat("yyyy-MM-dd hh:mm")
        var noticeId = noticeResult!![i].noticeId
        val s = format.format(date)
        holder.itemView.newfriends_time.text = s
        holder.itemView.beizhu_context.text = "请求添加好友"
        holder.itemView.newfriends_name.text = noticeResult!![i].fromNickName

        if ( noticeResult!![i].status == 1){
            holder.itemView.new_friend_radio_froup.visibility = VISIBLE
            holder.itemView.new_friend_ju.visibility = GONE
        }else if(noticeResult!![i].status == 2){
            holder.itemView.new_friend_radio_froup.visibility = GONE
            holder.itemView.new_friend_ju.visibility = VISIBLE
            holder.itemView.new_friend_ju.text = "已同意"
        }else if(noticeResult!![i].status == 3){
            holder.itemView.new_friend_radio_froup.visibility = GONE
            holder.itemView.new_friend_ju.visibility = VISIBLE
            holder.itemView.new_friend_ju.text = "已拒绝"
        }
        holder.itemView.btn_agree.setOnClickListener {
            flag = 2
            listener.invoke(flag,noticeId)
        }
        holder.itemView.btn_refuse.setOnClickListener {
            flag = 3
            listener.invoke(flag,noticeId)
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}
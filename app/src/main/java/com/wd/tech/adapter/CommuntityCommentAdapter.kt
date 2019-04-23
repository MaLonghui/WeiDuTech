package com.wd.tech.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.wd.tech.R
import com.wd.tech.bean.CommunityCommentVoList
import com.wd.tech.bean.CommutityResult
import kotlinx.android.synthetic.main.commuitity_comment_layout.view.*

class CommuntityCommentAdapter(context: Context) :RecyclerView.Adapter<CommuntityCommentAdapter.ViewHolder>(){
    var context : Context?=null
    var commutityList :List<CommunityCommentVoList>?=null
    init {
        this.context = context
        commutityList = ArrayList()
    }
    fun setComList(commutityList :List<CommunityCommentVoList>){
        this.commutityList =commutityList
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.commuitity_comment_layout,p0,false))
    }

    override fun getItemCount(): Int {
        if (commutityList!!.size>3){
            return 3
        }
        return commutityList!!.size
    }

    override fun onBindViewHolder(holder: ViewHolder, i: Int) {
        holder.itemView.comment_name.text = commutityList!![i].nickName
        holder.itemView.comment_com.text = commutityList!![i].content
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

}
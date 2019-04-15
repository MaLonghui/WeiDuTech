package com.wd.tech.adapter

import android.content.Context
import android.net.Uri
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.wd.tech.R
import com.wd.tech.bean.InfoCommentResult
import kotlinx.android.synthetic.main.info_comment_layout.view.*
import java.text.SimpleDateFormat
import java.util.*

class InfoCommentAdapter(context: Context) : RecyclerView.Adapter<InfoCommentAdapter.ViewHolder>() {
    var context: Context? = null
    var commentList: List<InfoCommentResult>? = null

    init {
        this.context = context
        commentList = ArrayList<InfoCommentResult>()
    }
    fun setComList(commentList: List<InfoCommentResult>){
        this.commentList = commentList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.info_comment_layout,viewGroup,false))
    }

    override fun getItemCount(): Int {
        return commentList!!.size
    }

    override fun onBindViewHolder(holder: ViewHolder, i: Int) {
        val uri = Uri.parse(commentList!![i].headPic)
        holder.itemView.comment_simple_view.setImageURI(uri)
        holder.itemView.comment_name.text = commentList!![i].nickName
        val date = Date(commentList!![i].commentTime)
        val sdf = SimpleDateFormat("MM-dd")
        val s = sdf.format(date)
        holder.itemView.comment_time.text = s
        holder.itemView.comment_content.text = commentList!![i].content

    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}
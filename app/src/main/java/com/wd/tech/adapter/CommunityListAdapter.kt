package com.wd.tech.adapter

import android.content.Context
import android.net.Uri
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.wd.tech.R
import com.wd.tech.bean.CommutityResult
import kotlinx.android.synthetic.main.community_list_layout.view.*
import java.text.SimpleDateFormat
import java.util.*

class CommunityListAdapter(context: Context) : RecyclerView.Adapter<CommunityListAdapter.ViewHolder>() {
    var context: Context? = null
    var list: List<CommutityResult>? = null

    init {
        this.context = context
        list = ArrayList()
    }

    fun setCommunityList(list: List<CommutityResult>) {
        this.list = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.community_list_layout,p0,false))
    }

    override fun getItemCount(): Int {
        return list!!.size
    }

    override fun onBindViewHolder(holder: ViewHolder, i: Int) {
        val uri = Uri.parse(list!![i].headPic)
        holder.itemView.p_simple_view.setImageURI(uri)
        holder.itemView.p_text_name.text = list!![i].nickName
        val date = Date(list!![i].commentTime)
        val dateFormat = SimpleDateFormat("yyyy-MM-dd")
        val s = dateFormat.format(date)
        holder.itemView.p_text_time.text = s
        holder.itemView.p_text_content.text = list!![i].content
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}
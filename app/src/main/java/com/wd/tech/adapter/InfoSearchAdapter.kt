package com.wd.tech.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.wd.tech.R
import com.wd.tech.bean.SearchResult
import kotlinx.android.synthetic.main.search_item_layout.view.*
import java.text.SimpleDateFormat

import java.util.*

class InfoSearchAdapter(context: Context) : RecyclerView.Adapter<InfoSearchAdapter.ViewHolder>() {
    var context: Context? = null
    var searResult: List<SearchResult>? = null

    init {
        this.context = context
        searResult = ArrayList()
    }

    fun setSearChList(searResult: List<SearchResult>) {
        this.searResult = searResult
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.search_item_layout, p0, false))
    }

    override fun getItemCount(): Int {
        return searResult!!.size
    }

    override fun onBindViewHolder(holder: ViewHolder, i: Int) {
        holder.itemView.search_title.text = searResult!![i].title
        holder.itemView.search_source.text = searResult!![i].source
        val date = Date(searResult!![i].releaseTime)
        val sdf = SimpleDateFormat("yyyy-MM-dd  hh:mm")
        val s = sdf.format(date)
        holder.itemView.search_time.text = s
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}
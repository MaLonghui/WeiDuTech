package com.wd.tech.adapter

import android.content.Context
import android.net.Uri
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.wd.tech.R
import kotlinx.android.synthetic.main.item_filecommunity.view.*

class FindUserImgAdapter(context: Context) : RecyclerView.Adapter<FindUserImgAdapter.ViewHolder>() {
    var context: Context? = null
    var list:List<String> ?=null
    init {
        this.context = context
        list = ArrayList()
    }
    fun setImgList(list:List<String> ){
        this.list = list
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        return ViewHolder(View.inflate(context, R.layout.item_filecommunity, null))
    }


    override fun getItemCount(): Int {
        return list!!.size
    }

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
        val uri = Uri.parse(list!![p1])
        p0.itemView.image_nicai.setImageURI(uri)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }
}
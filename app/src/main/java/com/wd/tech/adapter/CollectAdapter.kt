package com.wd.tech.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.facebook.drawee.view.SimpleDraweeView
import com.wd.tech.R
import com.wd.tech.bean.Collect
import com.wd.tech.bean.CollectBean
import java.text.SimpleDateFormat

class CollectAdapter(context:Context) : RecyclerView.Adapter<CollectAdapter.ViewHolder>() {
    var context : Context ?=null
    var list:List<Collect> ?=null
    init {
        this.context=context
        list=ArrayList<Collect>()
    }
    fun setData(result: List<Collect>) {
        list=result
        notifyDataSetChanged()
    }
    override fun getItemCount(): Int {
        return list!!.size
    }

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
        p0.info_item_simpleview.setImageURI(list!![p1].thumbnail)
        p0.info_summary_name.setText(list!![p1].title)
        val time = list!![p1].createTime
        val sdf=SimpleDateFormat("yyyy-MM-dd")
        val format = sdf.format(time)
        p0.info_time.setText(format)
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        return ViewHolder(View.inflate(context, R.layout.item_collect,null))
    }



    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var info_item_simpleview:SimpleDraweeView=itemView.findViewById(R.id.info_item_simpleview)
        var info_summary_name:TextView=itemView.findViewById(R.id.info_summary)
        var info_time:TextView=itemView.findViewById(R.id.info_time)
    }
}

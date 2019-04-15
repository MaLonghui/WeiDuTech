package com.wd.tech.adapter
import android.content.Context
import android.net.Uri
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.wd.tech.R
import com.wd.tech.bean.Information
import kotlinx.android.synthetic.main.info_tuijian_layout.view.*

class InfoTuiJianAdapter(context: Context) : RecyclerView.Adapter<InfoTuiJianAdapter.ViewHolder>() {
    var context:Context ?= null
    var informationList:List<Information> ?=null
    init {
        this.context = context
        informationList = ArrayList<Information>()
    }
    fun setTuiJianList(informationList:List<Information>){
        this.informationList = informationList
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.info_tuijian_layout,p0,false))
    }

    override fun getItemCount(): Int {
       return informationList!!.size
    }

    override fun onBindViewHolder(holder: ViewHolder, i: Int) {
        val uri = Uri.parse(informationList!![i].thumbnail)
        holder.itemView.info_tuijian_simple.setImageURI(uri)
        holder.itemView.info_tuijian_title.text = informationList!![i].title
    }

    class ViewHolder(itemView:View) : RecyclerView.ViewHolder(itemView)
}
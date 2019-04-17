package com.wd.tech.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.wd.tech.R
import kotlinx.android.synthetic.main.search_resou_layout.view.*

class ReSouCiAdapter(context: Context):RecyclerView.Adapter<ReSouCiAdapter.ViewHolder>() {
    var context:Context ?=null
    var reList:List<String> ?=null
    private lateinit var listener:((String)->Unit)
    fun setItemListener(listener:((String)->Unit)){
        this.listener = listener
    }
    init {
        this.context=context
        reList = ArrayList()
    }
    fun setSearList(reList:List<String>){
        this.reList = reList
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.search_resou_layout,p0,false))
    }

    override fun getItemCount(): Int {
        return reList!!.size
    }

    override fun onBindViewHolder(holder: ViewHolder, i: Int) {
        holder.itemView.search_text_re.text = reList!![i]
        holder.itemView.setOnClickListener {
            listener.invoke(reList!![i])
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

}
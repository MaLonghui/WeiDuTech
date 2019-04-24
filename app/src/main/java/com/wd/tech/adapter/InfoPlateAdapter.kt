package com.wd.tech.adapter

import android.content.Context
import android.net.Uri
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.wd.tech.R
import com.wd.tech.bean.InfoPlateResult
import kotlinx.android.synthetic.main.info_plate_layout.view.*

class InfoPlateAdapter(context: Context) : RecyclerView.Adapter<InfoPlateAdapter.ViewHolder>() {
    var context: Context? = null
    var plateList: List<InfoPlateResult>? = null
    private lateinit var listener: (Int, String) -> Unit

    //条目点击
    fun setItemListener(listener: (Int, String) -> Unit) {
        this.listener = listener
    }

    init {
        this.context = context
        plateList = ArrayList()
    }


    fun setPlaList(plateList: List<InfoPlateResult>) {
        this.plateList = plateList
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): InfoPlateAdapter.ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.info_plate_layout, viewGroup, false))
    }

    override fun getItemCount(): Int {
        return plateList!!.size
    }

    override fun onBindViewHolder(holder: InfoPlateAdapter.ViewHolder, i: Int) {
        val uri = Uri.parse(plateList!![i].pic)
        holder.itemView.info_plate_simple.setImageURI(uri)
        holder.itemView.info_plate_name.text = plateList!![i].name
        holder.itemView.setOnClickListener {
            listener.invoke(plateList!![i].id, plateList!![i].name)
        }

    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}
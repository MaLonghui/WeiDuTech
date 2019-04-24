package com.wd.tech.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import com.wd.tech.R
import com.wd.tech.bean.VipGoodsResult
import kotlinx.android.synthetic.main.vip_adapter_layout.view.*

class VipGoodsAdapter(context: Context) :RecyclerView.Adapter<VipGoodsAdapter.ViewHolder>() {
    var context:Context ?=null
    var vipList : List<VipGoodsResult> ?=null
    private lateinit var listener:(Int,Double)->Unit
    fun setItemClickListener(listener:(Int,Double)->Unit){
        this.listener = listener
    }

    init {
        this.context = context
        vipList = ArrayList()
    }
    fun setList(vipList : List<VipGoodsResult>){
        this.vipList = vipList
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.vip_adapter_layout,p0,false))
    }

    override fun getItemCount(): Int {
       return vipList!!.size
    }

    override fun onBindViewHolder(holder: ViewHolder, i: Int) {
        if (i == 0){
            holder.itemView.vip_icon.setImageResource(R.mipmap.vip_icon_z)
            holder.itemView.text_vip_type.text = "周VIP"
        }else if(i ==1){
            holder.itemView.vip_icon.setImageResource(R.mipmap.vip_icon_y)
            holder.itemView.text_vip_type.text = "月VIP"
        }else if(i ==2){
            holder.itemView.vip_icon.setImageResource(R.mipmap.vip_icon_j)
            holder.itemView.text_vip_type.text = "季VIP"
        }else if(i ==3){
            holder.itemView.vip_icon.setImageResource(R.mipmap.vip_icon_n)
            holder.itemView.text_vip_type.text = "年VIP"

        }

        holder.itemView.setOnClickListener {
            listener.invoke(vipList!![i].commodityId,vipList!![i].price)
        }

    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }
}
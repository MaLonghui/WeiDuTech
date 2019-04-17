package com.wd.tech.adapter

import android.content.Context
import android.net.Uri
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.VectorEnabledTintResources
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.wd.tech.R
import com.wd.tech.bean.InfoResult
import kotlinx.android.synthetic.main.fragment_information.view.*
import kotlinx.android.synthetic.main.info_item_show.view.*
import java.text.SimpleDateFormat
import java.util.*

/**
 * 资讯条目列表adapter
 */
class InfoItemAdapter(context: Context) : RecyclerView.Adapter<InfoItemAdapter.ViewHodder>() {
    var context: Context? = null
    var infoList: MutableList<InfoResult>? = null
    var listener:(Int)->Unit = {  }
    var collect:(Int)->Unit = {}


    init {
        this.context = context
        infoList = ArrayList<InfoResult>()

    }

    fun setInfoItemResult(infoList: MutableList<InfoResult>) {
        this.infoList = infoList
        notifyDataSetChanged()
    }
    fun setItemClickListener(id :(Int)->Unit){
        this.listener = id
    }
    fun setCollectClickListener(collect:(Int)->Unit){
        this.collect = collect
    }

    //刷新
    fun refresh(temList:MutableList<InfoResult>){
        this.infoList!!.clear()
        this.infoList!!.addAll(temList)
    }
    //加载
    fun loadMore(list:MutableList<InfoResult>){
        this.infoList!!.addAll(list)
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(viewGroup: ViewGroup, p1: Int): ViewHodder {
        return ViewHodder(LayoutInflater.from(context).inflate(R.layout.info_item_show, viewGroup, false))
    }

    override fun getItemCount(): Int {
        return infoList!!.size
    }

    override fun onBindViewHolder(holder: ViewHodder, i: Int) {
        if (infoList!![i].whetherAdvertising == 2){
            holder.itemView.info_item_relative.visibility = View.VISIBLE
            holder.itemView.info_linear.visibility = View.GONE
            var uri: Uri = Uri.parse(infoList!![i].thumbnail + "")
            holder.itemView.info_item_simpleview.setImageURI(uri)
            holder.itemView.info_summary.text = infoList!![i].summary
            holder.itemView.info_title.text = infoList!![i].title
            holder.itemView.info_source.text = infoList!![i].source
            if (infoList!![i].collection == 1){
                holder.itemView.info_collect.setImageResource(R.mipmap.common_icon_collect_s)
            }else{
                holder.itemView.info_collect.setImageResource(R.mipmap.common_icon_collect_n)
            }
            //点击收藏
            holder.itemView.info_collect.setOnClickListener {
                if(infoList!![i].collection == 1){//取消收藏
                    collect.invoke(infoList!![i].id)
                }else if(infoList!![i].collection == 2){//收藏
                    collect.invoke(infoList!![i].id)
                }

            }
            if(infoList!![i].whetherPay == 1){
                holder.itemView.info_pay_img.visibility = View.VISIBLE
            }else{
                holder.itemView.info_pay_img.visibility = View.GONE
            }
            //long类型转换date
            val date = Date(infoList!![i].releaseTime)
            val sdf = SimpleDateFormat("yyyy-MM-dd")
            val s = sdf.format(date)
            holder.itemView.info_time.text = s
            holder.itemView.info_collect_num.text = "${infoList!![i].collection}"
            holder.itemView.info_share_num.text = "${infoList!![i].share}"
        }else if(infoList!![i].whetherAdvertising == 1){
            holder.itemView.info_item_relative.visibility = View.GONE
            holder.itemView.info_linear.visibility = View.VISIBLE
            holder.itemView.info_adv_title.text = infoList!![i].infoAdvertisingVo.content
            var uri: Uri = Uri.parse(infoList!![i].infoAdvertisingVo.pic+"")
            holder.itemView.info_adv_simpleview.setImageURI(uri)
        }
        holder.itemView.setOnClickListener {
            if(infoList!![i].whetherAdvertising == 2){
                listener(infoList!![i].id)
            }else if(infoList!![i].whetherAdvertising == 1){
                listener(infoList!![i].infoAdvertisingVo.id)
            }

        }
    }

    class ViewHodder(inflate: View) : RecyclerView.ViewHolder(inflate)

}
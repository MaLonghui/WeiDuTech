package com.wd.tech.adapter

import android.content.Context
import android.content.SharedPreferences
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.wd.tech.R
import com.wd.tech.bean.Card
import java.text.SimpleDateFormat

/**
 * date:2019/4/17
 * author:冯泽林{2019/4/17}
 * function:
 */
class CardAdapter(context:Context) : RecyclerView.Adapter<CardAdapter.ViewHolder>() {
    var context:Context ?=null
    var list:List<Card> ?=null
    init {
        this.context=context
        list=ArrayList<Card>()
    }
    fun setData(result: List<Card>) {
        this.list=result
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        return ViewHolder(View.inflate(context, R.layout.item_card,null))
    }

    override fun getItemCount(): Int {
        return list!!.size
    }

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
        p0.text_title.text=list!![p1].content
        p0.text_comment.text="${list!![p1].comment}"
        p0.text_praise.text="${list!![p1].praise}"
        val time = list!![p1].publishTime
        var sdp= SimpleDateFormat("dd")
        val format = sdp.format(time)
        p0.text_time.text="${format}分钟前"
        p0.text_delete.setOnClickListener {
            if(mOnClickDelete!=null){
                mOnClickDelete!!.clickDelete(p1)
                notifyDataSetChanged()
            }

        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var text_title:TextView =itemView.findViewById(R.id.text_title)
        var text_time:TextView =itemView.findViewById(R.id.text_time)
        var text_praise:TextView =itemView.findViewById(R.id.text_praise)
        var text_comment:TextView =itemView.findViewById(R.id.text_comment)
        var image_praise:ImageView =itemView.findViewById(R.id.image_praise)
        var image_comment:ImageView =itemView.findViewById(R.id.image_comment)
        var text_delete:TextView=itemView.findViewById(R.id.text_delete)
    }
    interface OnClickDelete{
        fun clickDelete(i:Int)
    }
    var mOnClickDelete:OnClickDelete ?=null
    fun setOnClickDelete(onClickDelete:OnClickDelete){
       mOnClickDelete=onClickDelete
    }

}
package com.wd.tech.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.facebook.drawee.view.SimpleDraweeView
import com.wd.tech.R
import com.wd.tech.bean.Follow
import com.wd.tech.bean.FollowBean
import org.w3c.dom.Text

/**
 * date:2019/4/16
 * author:冯泽林{2019/4/16}
 * function:
 */
class FollowAdapter (context:Context): RecyclerView.Adapter<FollowAdapter.ViewHolder>() {
    var context:Context ?=null
    var list:List<Follow>  ?=null
    init {
        this.context=context
        list=ArrayList<Follow>()
        notifyDataSetChanged()
    }
    fun setData(result: List<Follow>) {
        this.list=result
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        return ViewHolder(View.inflate(context, R.layout.item_follow,null))
    }

    override fun getItemCount(): Int {
        return list!!.size
    }

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
        p0.simPle_follow.setImageURI(list!![p1].headPic)
        p0.text_Nickname_follow.text=list!![p1].nickName
        p0.text_name_follow.text=list!![p1].signature
    }



    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val simPle_follow:SimpleDraweeView=itemView.findViewById(R.id.simPle_follow)
        val text_Nickname_follow:TextView=itemView.findViewById(R.id.text_Nickname_follow)
        val text_name_follow:TextView=itemView.findViewById(R.id.text_name_follow)
    }
}
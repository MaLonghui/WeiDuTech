package com.wd.tech.adapter

import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.facebook.drawee.view.SimpleDraweeView
import com.wd.tech.R
import com.wd.tech.bean.Community
import com.wd.tech.bean.CommunityBean


/**
 * date:2019/4/14
 * author:冯泽林{2019/4/14}
 * function:
 */
class CommunityAdapter(context: Context) : RecyclerView.Adapter<CommunityAdapter.ViewHolder>() {
    var context:Context ?=null
    var list:List<CommunityBean> ?=null
    init {
        this.context=context
        list=ArrayList<CommunityBean>()
        notifyDataSetChanged()
    }
    fun setResult(result: List<CommunityBean>) {
        this.list=result
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        return  ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_community,p0,false))
    }

    override fun getItemCount(): Int {
        return list!!.size
    }

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
        p0.head_image.setImageURI(list!![p1].headPic)
        p0.text_name.text=list!![p1].nickName
        p0.text_details.text=list!![p1].content
        p0.text_time.text= list!![p1].publishTime.toString()
        p0.recycler_image.layoutManager=LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false)
        val file = list!![p1].file
        val split = file.split(",".toRegex())

        p0.recycler_image.adapter=CommunityFileAdapter(this.context!!,split)
        p0.text_comment.setText("${list!![p1].comment}")
        p0.text_praise.setText("${list!![p1].praise}")
        if(list!!.get(p1).whetherGreat==1){
            p0.image_praise.setImageResource(R.mipmap.common_icon_praise_s)
        }else{
            p0.image_praise.setImageResource(R.mipmap.common_icon_prise_n)
        }
        p0.image_praise.setOnClickListener {
            if(mOnPriseClickListenter!=null){
                if(list!![p1].whetherGreat==1){
                    list!![p1].whetherGreat==2
                    list!![p1].praise=list!![p1].praise-1
                }else if(list!![p1].whetherGreat==2){
                    list!![p1 ].whetherGreat=1
                    list!![p1].praise=list!![p1].praise+1
                }
                mOnPriseClickListenter?.onPriseClick(list!![p1].id,list!![p1].whetherGreat==1)
                notifyDataSetChanged()
            }
        }
    }



    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var head_image:SimpleDraweeView =itemView.findViewById(R.id.head_image)
        var text_name:TextView=itemView.findViewById(R.id.text_name)
        var text_time:TextView=itemView.findViewById(R.id.text_time)
        var text_details:TextView=itemView.findViewById(R.id.text_details)
        var recycler_image:RecyclerView=itemView.findViewById(R.id.recycler_image)
        var text_praise:TextView = itemView.findViewById(R.id.text_praise)
        var image_praise:ImageView =itemView.findViewById(R.id.image_praise)
        var text_comment:TextView=itemView.findViewById(R.id.text_comment)
    }
    /**
     * 接口回调
     */
    interface OnPriseClickListenter {
        fun onPriseClick(position : Int,b : Boolean)
    }

    var mOnPriseClickListenter : OnPriseClickListenter?=null

    fun setOnPriseClickListenter(onPriseClickListenter : OnPriseClickListenter){
        this.mOnPriseClickListenter=onPriseClickListenter
    }
}
package com.wd.tech.adapter

import android.content.Context
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.wd.tech.R
import com.wd.tech.bean.CommunityUserPostVo
import kotlinx.android.synthetic.main.find_user_adapterlayout.view.*

class FindUserPostAdapter(context: Context) : RecyclerView.Adapter<FindUserPostAdapter.ViewHolder>() {
    var context: Context? = null
    var communityUserPostVoList: List<CommunityUserPostVo>? = null
    var adapter: FindUserImgAdapter? = null

    init {
        this.context = context
        communityUserPostVoList = ArrayList()
    }

    fun setUserList(communityUserPostVoList: List<CommunityUserPostVo>) {
        this.communityUserPostVoList = communityUserPostVoList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.find_user_adapterlayout, p0, false))
    }

    override fun getItemCount(): Int {
        return communityUserPostVoList!!.size
    }

    override fun onBindViewHolder(holder: ViewHolder, i: Int) {
        holder.itemView.user_content.text = communityUserPostVoList!![i].content
        holder.itemView.comment_num.text = "${communityUserPostVoList!![i].comment}"
        holder.itemView.user_prise_num.text = "${communityUserPostVoList!![i].praise}"

        if (communityUserPostVoList!![i].whetherGreat == 1) {
            holder.itemView.user_prise_img.setImageResource(R.mipmap.common_icon_praise_s)
        } else {
            holder.itemView.user_prise_img.setImageResource(R.mipmap.common_icon_prise_n)
        }
        adapter = FindUserImgAdapter(this!!.context!!)

        val file = communityUserPostVoList!![i].file
        val split = file.split(",".toRegex())
        holder.itemView.user_imgs_recycler.layoutManager = GridLayoutManager(context, 3)
        holder.itemView.user_imgs_recycler.adapter = adapter
        adapter!!.setImgList(split)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}
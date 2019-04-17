package com.wd.tech.adapter

import android.app.Activity
import android.content.Context
import android.support.v4.view.ViewPager
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.wd.tech.R
import com.wd.tech.activity.InfoDetailsActivity
import com.wd.tech.bean.BannerBean
import com.wd.tech.bean.BannerResult
import com.wd.tech.bean.InfoResult
import com.wd.tech.utils.JumpActivityUtils
import kotlinx.android.synthetic.main.banner_item_layout.view.*
import kotlinx.android.synthetic.main.fragment_information.*
import kotlinx.android.synthetic.main.information_item_layout.view.*


class InformationAdapter(context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var context:Context ?=null
    var bannerList:List<BannerResult> ?= null
    var infoList:List<InfoResult>? = null
    val TYPE_ONE = 0
    val TYPE_TWO = 1
    init {
        this.context = context
        bannerList = ArrayList<BannerResult>()
        infoList = ArrayList<InfoResult>()
    }

    fun setBannerResult(bannerList: List<BannerResult>) {
        this.bannerList = bannerList
        notifyDataSetChanged()
    }
    fun setInfoResult(infoList:List<InfoResult>) {
        this.infoList = infoList
        notifyDataSetChanged()
    }


    override fun getItemViewType(position: Int): Int {
        when(position){
            0 ->  {
                return TYPE_ONE
            }
        }
        return TYPE_TWO
    }


    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): RecyclerView.ViewHolder {
       if (i == TYPE_ONE){
           return ItemOneViewHolder(LayoutInflater.from(context).inflate(R.layout.banner_item_layout,viewGroup,false))
       }else{
           return ItemTwoViewHolder(LayoutInflater.from(context).inflate(R.layout.information_item_layout,viewGroup,false))
       }
    }



    override fun getItemCount(): Int {
        return bannerList!!.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, i: Int) {
        if (holder is ItemOneViewHolder){
            //设置图片圆角角度
            val roundedCorners = RoundedCorners(8)
            //通过RequestOptions扩展功能,override:采样率,因为ImageView就这么大,可以压缩图片,降低内存消耗
            val options = RequestOptions.bitmapTransform(roundedCorners).override(300, 300)
            holder.itemView.banner.setData(this!!.bannerList!!,null)
            holder.itemView.banner.loadImage { banner, model, view, position ->
                Glide.with(this!!.context!!).load(bannerList!![position].imageUrl).apply(options).into(view as ImageView)
                banner.setPageChangeDuration(1000)
            }
            holder.itemView.banner.setOnPageChangeListener(object : ViewPager.OnPageChangeListener{
                override fun onPageScrollStateChanged(p0: Int) {

                }

                override fun onPageScrolled(p0: Int, p1: Float, p2: Int) {

                }

                override fun onPageSelected(p0: Int) {
                    holder.itemView.banner_name.text = bannerList!![p0].title
                }
            })
            holder.itemView.banner.setOnItemClickListener { banner, model, view, position ->
                Toast.makeText(context, bannerList!![position].title,Toast.LENGTH_LONG).show()
            }
        }else if(holder is ItemTwoViewHolder){
            val layoutManager = LinearLayoutManager(context)
            layoutManager.orientation = LinearLayoutManager.VERTICAL
            holder.itemView.info_recycler_view.layoutManager = layoutManager
            holder.itemView.info_recycler_view.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
            val infoItemAdapter = InfoItemAdapter(this!!.context!!)
            holder.itemView.info_recycler_view.adapter = infoItemAdapter
            infoItemAdapter.setInfoItemResult(this!!.infoList!!)
            infoItemAdapter.setItemClickListener {
                var prams : HashMap<String,Any> = hashMapOf(Pair("id",it))
                JumpActivityUtils.skipValueActivity(context as Activity,InfoDetailsActivity::class.java,prams)
            }
        }


    }
    class ItemOneViewHolder(inflate: View) : RecyclerView.ViewHolder(inflate) {

    }
    class ItemTwoViewHolder(inflate: View) : RecyclerView.ViewHolder(inflate) {

    }
}
package com.wd.tech.adapter

import android.content.Context
import android.content.Intent
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.facebook.drawee.view.SimpleDraweeView
import com.wd.tech.R
import com.wd.tech.activity.CommunityListActivity
import com.wd.tech.bean.CommunityBean
import kotlinx.android.synthetic.main.item_community.view.*
import org.greenrobot.eventbus.EventBus
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


/**
 * date:2019/4/14
 * author:冯泽林{2019/4/14}
 * function:
 */
class CommunityAdapter(context: Context) : RecyclerView.Adapter<CommunityAdapter.ViewHolder>() {


    var context: Context? = null
    var list: List<CommunityBean>? = null
    private lateinit var priseListener: (Int, Int) -> Unit
    private lateinit var commentListener: (Int) -> Unit
    private lateinit var iconListener:(Int)->Unit
    fun setIconClickListener(iconListener:(Int)->Unit){
        this.iconListener = iconListener
    }

    fun setPriseListener(priseListener: (Int, Int) -> Unit) {
        this.priseListener = priseListener
    }

    fun setCommListener(commentListener: (Int) -> Unit) {
        this.commentListener = commentListener
    }

    init {
        this.context = context
        list = ArrayList<CommunityBean>()

    }

    fun setResult(result: List<CommunityBean>) {
        this.list = result
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_community, p0, false))
    }

    override fun getItemCount(): Int {
        return list!!.size
    }

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
        EventBus.getDefault().postSticky(list!![p1].id)
        p0.head_image.setImageURI(list!![p1].headPic)
        p0.text_name.text = list!![p1].nickName
        p0.text_details.text = list!![p1].content
        p0.itemView.text_signature.text = list!![p1].signature
        val date = Date(list!![p1].publishTime)
        val format = SimpleDateFormat("yyyy-MM-dd hh:mm")
        val s = format.format(date)
        p0.text_time.text = s
        p0.recycler_image.layoutManager = GridLayoutManager(context, 3)
        val file = list!![p1].file
        val split = file.split(",".toRegex())
        p0.recycler_image.adapter = CommunityFileAdapter(this.context!!, split)
        p0.recycler_image.adapter=CommunityFileAdapter(this.context!!,split)
        p0.recycler_image.adapter=CommunityFileAdapter(this.context!!,split)
        p0.recycler_image.adapter = CommunityFileAdapter(this.context!!, split)
        p0.recycler_image.adapter=CommunityFileAdapter(this.context!!,split)
        p0.recycler_image.adapter = CommunityFileAdapter(this.context!!, split)
        p0.recycler_image.adapter = CommunityFileAdapter(this.context!!, split)
        p0.recycler_image.adapter=CommunityFileAdapter(this.context!!,split)
        p0.text_comment.setText("${list!![p1].comment}")
        p0.text_praise.setText("${list!![p1].praise}")



        if (list!![p1].whetherGreat == 1) {
            p0.image_praise.setImageResource(R.mipmap.common_icon_praise_s)
        } else if (list!![p1].whetherGreat == 2) {
            p0.image_praise.setImageResource(R.mipmap.common_icon_prise_n)
        }
        //点赞
        p0.image_praise.setOnClickListener {
            priseListener.invoke(list!![p1].id, p1)
        }

        p0.itemView.head_image.setOnClickListener {
            iconListener.invoke(list!![p1].userId)
        }
        val communityCommentVoList = list!![p1].communityCommentVoList
        val commentAdapter = CommuntityCommentAdapter(context!!)
        p0.itemView.comment_recycler.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        p0.itemView.comment_recycler.adapter = commentAdapter
        commentAdapter.setComList(communityCommentVoList)
        if (communityCommentVoList.size > 0) {
            p0.itemView.comment_recycler.visibility = VISIBLE
            p0.itemView.comment_text_come.visibility = GONE
            p0.itemView.comment_text_more.visibility = VISIBLE
        } else if (communityCommentVoList.size == 0) {
            p0.itemView.comment_text_come.visibility = VISIBLE
            p0.itemView.comment_text_more.visibility = GONE
            p0.itemView.comment_recycler.visibility = GONE

        }

        //评论
        p0.itemView.image_comment.setOnClickListener {
            commentListener.invoke(list!![p1].id)
        }

        p0.itemView.comment_text_more.setOnClickListener {
           val intent = Intent(context, CommunityListActivity::class.java)
            intent.putExtra("fHeadPic",list!![p1].headPic)
            intent.putExtra("fNickName",list!![p1].nickName)
            intent.putExtra("communityId",list!![p1].id)
            context!!.startActivity(intent)
        }
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var head_image: SimpleDraweeView = itemView.findViewById(R.id.head_image)
        var text_name: TextView = itemView.findViewById(R.id.text_name)
        var text_time: TextView = itemView.findViewById(R.id.text_time)
        var text_details: TextView = itemView.findViewById(R.id.text_details)
        var recycler_image: RecyclerView = itemView.findViewById(R.id.recycler_image)
        var text_praise: TextView = itemView.findViewById(R.id.text_praise)
        var image_praise: ImageView = itemView.findViewById(R.id.image_praise)
        var text_comment: TextView = itemView.findViewById(R.id.text_comment)
    }

}
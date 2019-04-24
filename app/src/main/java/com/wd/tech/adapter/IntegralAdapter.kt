package com.wd.tech.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.wd.tech.R
import com.wd.tech.bean.Definite
import com.wd.tech.bean.Integral
import com.wd.tech.utils.PhoneFormatCheckUtils
import kotlinx.android.synthetic.main.item_integral.view.*
import org.w3c.dom.Text
import java.text.SimpleDateFormat

/**
 * date:2019/4/21
 * author:冯泽林{2019/4/21}
 * function:
 */
class IntegralAdapter(context: Context) : RecyclerView.Adapter<IntegralAdapter.ViewHolder>() {
    var context: Context? = null
    var list: List<Definite>? = null

    init {
        this.context = context
        list = ArrayList<Definite>()
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        return ViewHolder(View.inflate(context, R.layout.item_integral, null))
    }

    override fun getItemCount(): Int {
        return list!!.size
    }

    fun setData(result: List<Definite>) {
        list = result
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
        val amount = list!![p1].amount
        if (amount > 0) {
            p0.itemView.text_integral.text = "+${amount.toString()}"
        } else {
            p0.itemView.text_integral.setTextColor(Color.parseColor("#20affa"))
            p0.itemView.text_integral.text = "${amount.toString()}"
        }
        if (list!![p1].type == 1) {
            p0.itemView.text_name.text = "签到"
        } else if (list!![p1].type == 2) {
            p0.itemView.text_name.text = "评论"
        } else if (list!![p1].type == 3) {
            p0.itemView.text_name.text = "分享"
        } else if (list!![p1].type == 4) {
            p0.itemView.text_name.text = "发帖"
        } else if (list!![p1].type == 5) {
            p0.itemView.text_name.text = "抽奖收入"
        } else if (list!![p1].type == 6) {
            p0.itemView.text_name.text = "付费资讯"
        } else if (list!![p1].type == 7) {
            p0.itemView.text_name.text = "抽奖支出"
        } else if (list!![p1].type == 8) {
            p0.itemView.text_name.text = "完善个人信息"
        } else if (list!![p1].type == 9) {
            p0.itemView.text_name.text = "查看广告"
        } else if (list!![p1].type == 10) {
            p0.itemView.text_name.text = "绑定第三方"
        }
        val time = list!![p1].createTime
        val format = SimpleDateFormat("yyyy-MM-dd")
        val s = format.format(time)
        p0.itemView.text_time.text = s


    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }
}
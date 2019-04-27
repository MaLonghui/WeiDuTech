package com.wd.tech.adapter

import android.content.Context
import android.database.DataSetObserver
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ExpandableListAdapter
import android.widget.ImageView
import com.wd.tech.bean.FriendResult
import com.wd.tech.bean.GroupResult
import com.wd.tech.bean.MyFriendsBean
import com.wd.tech.bean.MyGroupBean
import android.widget.TextView
import com.facebook.drawee.view.SimpleDraweeView
import com.wd.tech.R
import com.wd.tech.adapter.LinkManAdapter.GroupViewHolder
import org.w3c.dom.Text
import retrofit2.http.Url


class LinkManAdapter(context: Context, groupResult: List<GroupResult>, myFriendList: MutableList<MyFriendsBean>) : ExpandableListAdapter {
    var context: Context? = context
    var groupResult: List<GroupResult> = groupResult
    var myFriendList: MutableList<MyFriendsBean> = myFriendList

    //获取groupPosition分组，子列表数量
    override fun getChildrenCount(groupPosition: Int): Int {
        var ret = 0
        if (myFriendList != null) {
            ret = myFriendList!![groupPosition].result.size
        }
        return ret
    }


    override fun getGroup(groupPosition: Int): Any {
        return groupResult!!.get(groupPosition)
    }

    override fun onGroupCollapsed(groupPosition: Int) {

    }

    override fun isEmpty(): Boolean {
        return false
    }

    override fun registerDataSetObserver(observer: DataSetObserver?) {
    }

    override fun getChild(groupPosition: Int, childPosition: Int): Any {
        return myFriendList!!.get(groupPosition).result.get(childPosition)
    }

    override fun onGroupExpanded(groupPosition: Int) {
    }

    override fun getCombinedChildId(groupId: Long, childId: Long): Long {
        return 0
    }

    override fun getGroupId(groupPosition: Int): Long {
        return groupPosition.toLong()
    }

    override fun isChildSelectable(groupPosition: Int, childPosition: Int): Boolean {
        return true
    }

    override fun hasStableIds(): Boolean {
        return true
    }

    override fun getChildView(groupPosition: Int, childPosition: Int, isLastChild: Boolean, convertView: View?, parent: ViewGroup?): View {
        var convertView = convertView
        var holder: ChildViewHolder? = null
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_child_expandablelistview, null)
            holder = ChildViewHolder()
            holder!!.child_icon = convertView!!.findViewById(R.id.child_icon)
            holder!!.child_name = convertView!!.findViewById(R.id.child_name)
            holder!!.child_qian = convertView!!.findViewById(R.id.child_qian)
            convertView.tag = holder
        } else {
            holder = convertView.tag as ChildViewHolder
        }
        val myFriendList = this.myFriendList!!.get(groupPosition)
        val uri = Uri.parse(myFriendList.result[childPosition].headPic)
        holder!!.child_icon!!.setImageURI(uri)
        holder!!.child_name!!.text = myFriendList.result[childPosition].nickName
        holder!!.child_qian!!.text = myFriendList.result[childPosition].remarkName

        return convertView!!
    }

    override fun areAllItemsEnabled(): Boolean {
        return false
    }

    override fun getChildId(groupPosition: Int, childPosition: Int): Long {
        return childPosition.toLong()
    }

    override fun getCombinedGroupId(groupId: Long): Long {
        return 0
    }

    override fun getGroupView(groupPosition: Int, isExpanded: Boolean, convertView: View?, parent: ViewGroup?): View {
        var convertView = convertView
        var holder: GroupViewHolder? = null
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_group_expandablelistview, null)
            holder = GroupViewHolder()
            holder!!.group_expand_icon = convertView!!.findViewById<ImageView>(R.id.group_expand_icon)
            holder!!.group_name = convertView!!.findViewById<TextView>(R.id.group_name)
            holder!!.group_num = convertView!!.findViewById<TextView>(R.id.group_num)

            convertView.setTag(holder)
        } else {
            holder = convertView.tag as GroupViewHolder
        }

        //是否展开
        if (isExpanded) {
            holder!!.group_expand_icon!!.setImageResource(R.drawable.groupdown)
        } else {
            holder!!.group_expand_icon!!.setImageResource(R.drawable.groupright)
        }
        holder!!.group_name!!.text = groupResult!![groupPosition].groupName
        holder!!.group_num!!.text = "${groupResult!![groupPosition].currentNumber}/10"
        return convertView!!


    }

    override fun unregisterDataSetObserver(observer: DataSetObserver?) {

    }

    //获取分组个数
    override fun getGroupCount(): Int {
        var reg = 0
        if (groupResult != null) {
            reg = groupResult!!.size
        }
        return reg
    }

    class GroupViewHolder {
        var group_expand_icon: ImageView? = null
        var group_name: TextView? = null
        var group_num: TextView? = null
    }

    class ChildViewHolder {
        var child_icon: SimpleDraweeView? = null
        var child_name: TextView? = null
        var child_qian: TextView? = null
    }

}
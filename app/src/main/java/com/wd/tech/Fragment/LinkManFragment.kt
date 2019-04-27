package com.wd.tech.Fragment


import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import com.wd.tech.R
import com.wd.tech.adapter.LinkManAdapter
import com.wd.tech.api.Api
import com.wd.tech.base.BaseFragment
import com.wd.tech.mvp.Constanct
import com.wd.tech.mvp.Presenter
import kotlinx.android.synthetic.main.fragment_link_man.*
import android.widget.PopupWindow
import com.wd.tech.bean.*
import android.provider.SyncStateContract.Helpers.update
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.ExpandableListView
import com.wd.tech.activity.NewFriendActivity
import com.wd.tech.activity.SendMesageActivity
import com.wd.tech.activity.UserSettingActivity
import com.wd.tech.utils.JumpActivityUtils
import kotlinx.android.synthetic.main.activity_send_mesage.*


class LinkManFragment : BaseFragment<Constanct.View, Constanct.Presenter>(), Constanct.View {
    var groupResult: List<GroupResult>? = null
    var myFriendList: MutableList<MyFriendsBean> = ArrayList()
    var adapter: LinkManAdapter? = null
    var i: Int = 0
    override fun getLayoutId(): Int {
        return R.layout.fragment_link_man
    }

    override fun initPresenter(): Constanct.Presenter {
        return Presenter()
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val sp: SharedPreferences = context!!.getSharedPreferences("config", Context.MODE_PRIVATE)
        val userId = sp.getString("userId", "")
        val sessionId = sp.getString("sessionId", "")

        lin_newfriends.setOnClickListener {
            if (userId.equals("") || sessionId.equals("")) {
                Toast.makeText(context, "还未登陆哦！", Toast.LENGTH_LONG).show()
            } else {
                msg_num_rela.visibility = GONE
                message_num.text = ""
                startActivity(Intent(context, NewFriendActivity::class.java))
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    override fun initData() {
        val sp: SharedPreferences = context!!.getSharedPreferences("config", Context.MODE_PRIVATE)
        val userId = sp.getString("userId", "")
        val sessionId = sp.getString("sessionId", "")
        val headmap: Map<String, Any> = mapOf(Pair("userId", userId), Pair("sessionId", sessionId))
        var nMap: Map<String, Any> = mapOf()
        if (userId.equals("") || sessionId.equals("")) {

        } else {
            mPresenter!!.getPresenter(Api.FRIEND_GROUP_LIST, headmap, MyGroupBean::class.java, nMap)
        }

    }


    override fun View(any: Any) {
        val sp: SharedPreferences = context!!.getSharedPreferences("config", Context.MODE_PRIVATE)
        val userId = sp.getString("userId", "")
        val sessionId = sp.getString("sessionId", "")
        val headmap: Map<String, Any> = mapOf(Pair("userId", userId), Pair("sessionId", sessionId))
        if (any is MyGroupBean) {
            myFriendList = ArrayList()
            i = 0
            var myGroupBean: MyGroupBean = any
            groupResult = myGroupBean.result

            groupResult!!.forEach {
                mPresenter!!.getPresenter(Api.MY_FRIEND_LIST, headmap, MyFriendsBean::class.java, mapOf(Pair("groupId", it.groupId)))
                Thread.sleep(500)
            }


        } else if (any is MyFriendsBean) {
            myFriendList.add(any)
            i++
            if (i == groupResult!!.size) {
                adapter = LinkManAdapter(this!!.activity!!, groupResult!!, myFriendList!!)
                expandableListView.setAdapter(adapter)
            }
            expandableListView.setOnChildClickListener(object : ExpandableListView.OnChildClickListener {
                override fun onChildClick(parent: ExpandableListView?, v: View?, groupPosition: Int, childPosition: Int, id: Long): Boolean {
                    val friendsBean = myFriendList[groupPosition]
                    val result = friendsBean.result
                    if (result != null) {
                        var intent: Intent = Intent(activity, SendMesageActivity::class.java)
                        intent.putExtra("id", result[childPosition].friendUid)
                        intent.putExtra("nickName", result[childPosition].nickName)
                        intent.putExtra("headPic", result[childPosition].headPic)
                        intent.putExtra("signature", result[childPosition].remarkName)
                        startActivity(intent)
                    }
                    return false
                }
            })
        }
    }

}

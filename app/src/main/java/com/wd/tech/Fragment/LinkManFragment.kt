package com.wd.tech.Fragment


import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import com.wd.tech.R
import com.wd.tech.adapter.LinkManAdapter
import com.wd.tech.api.Api
import com.wd.tech.base.BaseFragment
import com.wd.tech.bean.FriendResult
import com.wd.tech.bean.GroupResult
import com.wd.tech.bean.MyFriendsBean
import com.wd.tech.bean.MyGroupBean
import com.wd.tech.mvp.Constanct
import com.wd.tech.mvp.Presenter
import kotlinx.android.synthetic.main.fragment_link_man.*
import android.widget.PopupWindow




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

    }

    override fun onResume() {
        super.onResume()
        val sp: SharedPreferences = context!!.getSharedPreferences("config", Context.MODE_PRIVATE)
        val userId = sp.getString("userId", "")
        val sessionId = sp.getString("sessionId", "")
        val headmap: Map<String, Any> = mapOf(Pair("userId", userId), Pair("sessionId", sessionId))
        var nMap: Map<String, Any> = mapOf()
        if (userId.equals("") || sessionId.equals("")) {

        }else{
            mPresenter!!.getPresenter(Api.FRIEND_GROUP_LIST, headmap, MyGroupBean::class.java, nMap)
        }
    }

    override fun initData() {
        val sp: SharedPreferences = context!!.getSharedPreferences("config", Context.MODE_PRIVATE)
        val userId = sp.getString("userId", "")
        val sessionId = sp.getString("sessionId", "")
        val headmap: Map<String, Any> = mapOf(Pair("userId", userId), Pair("sessionId", sessionId))
        var nMap: Map<String, Any> = mapOf()
        if (userId.equals("") || sessionId.equals("")) {

        }else{
            mPresenter!!.getPresenter(Api.FRIEND_GROUP_LIST, headmap, MyGroupBean::class.java, nMap)
        }

    }



    override fun View(any: Any) {
        val sp: SharedPreferences = context!!.getSharedPreferences("config", Context.MODE_PRIVATE)
        val userId = sp.getString("userId", "")
        val sessionId = sp.getString("sessionId", "")
        val headmap: Map<String, Any> = mapOf(Pair("userId", userId), Pair("sessionId", sessionId))
        if (any is MyGroupBean) {
            var myGroupBean: MyGroupBean = any
            groupResult = myGroupBean.result
            //Toast.makeText(context, "${groupResult!!.size}", Toast.LENGTH_LONG).show()
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
        }
    }

}

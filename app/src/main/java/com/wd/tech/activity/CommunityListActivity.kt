package com.wd.tech.activity

import android.content.Context
import android.content.SharedPreferences
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.text.TextUtils
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import com.wd.tech.R
import com.wd.tech.adapter.CommunityListAdapter
import com.wd.tech.api.Api
import com.wd.tech.base.BaseActivity
import com.wd.tech.bean.CommutityCommentBean
import com.wd.tech.bean.UserPublicBean
import com.wd.tech.mvp.Constanct
import com.wd.tech.mvp.Presenter
import com.wd.tech.utils.JumpActivityUtils
import kotlinx.android.synthetic.main.activity_community_list.*
import kotlinx.android.synthetic.main.activity_search.*

class CommunityListActivity : BaseActivity<Constanct.View, Constanct.Presenter>(), Constanct.View {
    var page: Int = 1
    var count: Int = 20
    var adapter: CommunityListAdapter? = null
    override fun getLayoutId(): Int {
        return R.layout.activity_community_list
    }

    override fun initPresenter(): Constanct.Presenter {
        return Presenter()
    }

    override fun initData() {
        val communityId = intent.getIntExtra("communityId", 0)
        val fHeadPic = intent.getStringExtra("fHeadPic")
        val fNickName = intent.getStringExtra("fNickName")
        val sp: SharedPreferences = getSharedPreferences("config", Context.MODE_PRIVATE)
        val userid = sp.getString("userId", "")
        val sessionId = sp.getString("sessionId", "")
        val map: Map<String, Any> = mapOf(Pair("userId", userid), Pair("sessionId", sessionId))
        val mapone: Map<String, Any> = mapOf(Pair("communityId", communityId), Pair("page", page), Pair("count", count))
        mPresenter!!.getPresenter(Api.COMMUNITY_LIST, map, CommutityCommentBean::class.java, mapone)
        val uri = Uri.parse(fHeadPic)
        f_simple_view.setImageURI(uri)
        f_text_name.text = fNickName
        val linearLayoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        more_comm_recycler.layoutManager = linearLayoutManager
        adapter = CommunityListAdapter(this)
        more_comm_recycler.adapter = adapter
        more_send.setOnClickListener {
            val content = more_edit.text.toString()
            if (TextUtils.isEmpty(content)) {
                Toast.makeText(this, "请输入评论内容", Toast.LENGTH_LONG).show()
            } else {
                var prams = mapOf(Pair("communityId", communityId), Pair("content", content))
                mPresenter!!.postPresenter(Api.INFO_COMMENT_ADD, map, UserPublicBean::class.java, prams)
            }
        }
        more_comm_back.setOnClickListener {
            onBackPressed()
        }


    }
    override fun View(any: Any) {
        val communityId = intent.getIntExtra("communityId", 0)
        val sp: SharedPreferences = getSharedPreferences("config", Context.MODE_PRIVATE)
        val userid = sp.getString("userId", "")
        val sessionId = sp.getString("sessionId", "")
        val map: Map<String, Any> = mapOf(Pair("userId", userid), Pair("sessionId", sessionId))
        val mapone: Map<String, Any> = mapOf(Pair("communityId", communityId), Pair("page", page), Pair("count", count))
        if (any is CommutityCommentBean) {
            var commutityCommentBean: CommutityCommentBean = any
            val commutityList = commutityCommentBean.result
            comm_num.text = "${commutityList.size}条评论"
            adapter!!.setCommunityList(commutityList)
        } else if (any is UserPublicBean) {
            var publicBean: UserPublicBean = any
            Toast.makeText(this, publicBean.message, Toast.LENGTH_LONG).show()
            mPresenter!!.getPresenter(Api.COMMUNITY_LIST, map, CommutityCommentBean::class.java, mapone)
            more_edit.text = null
            val service: InputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            service.hideSoftInputFromWindow(more_edit.windowToken,0)
        }
    }
}

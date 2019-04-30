package com.wd.tech.activity

import android.content.Context
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import android.widget.Toast
import com.wd.tech.R
import com.wd.tech.api.Api
import com.wd.tech.base.BaseActivity
import com.wd.tech.bean.GroupInfoBean
import com.wd.tech.bean.UserByPhoneBean
import com.wd.tech.mvp.Constanct
import com.wd.tech.mvp.Presenter
import com.wd.tech.utils.JumpActivityUtils
import kotlinx.android.synthetic.main.activity_add_friend_or_group.*
import kotlinx.android.synthetic.main.activity_search.*
import java.util.HashMap

class AddFriendOrGroupActivity : BaseActivity<Constanct.View, Constanct.Presenter>(), Constanct.View {
    override fun getLayoutId(): Int {
        return R.layout.activity_add_friend_or_group
    }

    override fun initPresenter(): Constanct.Presenter {
        return Presenter()
    }

    override fun initData() {
        var sp = getSharedPreferences("config", Context.MODE_PRIVATE)
        var userId = sp.getString("userId", "")
        var sessionId = sp.getString("sessionId", "")
        var headMap: Map<String, Any> = mapOf(Pair("userId", userId), Pair("sessionId", sessionId))
        radio_group_find.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.radio_btn_people -> {
                    view_people.visibility = VISIBLE
                    lin_findPeople.visibility = VISIBLE
                    view_qun.visibility = GONE
                    lin_findQun.visibility = GONE
                    lin_search_result.visibility = GONE
                    qun_edit_search.text = null
                }
                R.id.radio_btn_qun -> {
                    view_qun.visibility = VISIBLE
                    lin_findQun.visibility = VISIBLE
                    view_people.visibility = GONE
                    lin_findPeople.visibility = GONE
                    lin_search_result.visibility = GONE
                    people_edit_search.text = null
                }
            }
        }

        people_edit_search.setOnEditorActionListener(object : TextView.OnEditorActionListener {
            override fun onEditorAction(v: TextView?, actionId: Int, event: KeyEvent?): Boolean {
                if (actionId == EditorInfo.IME_ACTION_UNSPECIFIED) {
                    val phone = v!!.text.toString()
                    mPresenter!!.getPresenter(Api.FIND_USER_PHONE, headMap, UserByPhoneBean::class.java, mapOf(Pair("phone", phone)))
                    //点击回车隐藏软键盘
                    val service: InputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    service.hideSoftInputFromWindow(people_edit_search.windowToken, 0)
                }
                return false
            }

        })
        qun_edit_search.setOnEditorActionListener(object : TextView.OnEditorActionListener {
            override fun onEditorAction(v: TextView?, actionId: Int, event: KeyEvent?): Boolean {
                if (actionId == EditorInfo.IME_ACTION_UNSPECIFIED) {
                    val groupId = v!!.text.toString()
                    mPresenter!!.getPresenter(Api.GROUP_INFO, headMap, GroupInfoBean::class.java, mapOf(Pair("groupId", groupId)))
                    //点击回车隐藏软键盘
                    val service: InputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    service.hideSoftInputFromWindow(people_edit_search.windowToken, 0)
                }
                return false
            }

        })
    }

    override fun View(any: Any) {
        if (any is UserByPhoneBean) {
            var userByPhoneBean: UserByPhoneBean = any
            val result = userByPhoneBean.result
            if (result != null) {
                lin_search_result.visibility = VISIBLE
                no_search_result.visibility = GONE
                val uri = Uri.parse(result.headPic)
                img_search_result.setImageURI(uri)
                name_search_result.text = result.nickName
            } else {
                lin_search_result.visibility = GONE
                no_search_result.visibility = VISIBLE
            }
            lin_search_result.setOnClickListener {
                var intentMap: HashMap<String, Any> = hashMapOf(Pair("userId", result.userId))
                JumpActivityUtils.skipValueActivity(this, AddFriendActivity::class.java, intentMap)
            }
        } else if (any is GroupInfoBean) {
            var groupInfoBean: GroupInfoBean = any
            val infoResult = groupInfoBean.result
            if (infoResult != null) {
                lin_search_result.visibility = VISIBLE
                no_search_result.visibility = GONE
                val uri = Uri.parse(infoResult.groupImage)
                img_search_result.setImageURI(uri)
                name_search_result.text = infoResult.groupName
                lin_search_result.setOnClickListener {

                }
            }
        }
    }
}

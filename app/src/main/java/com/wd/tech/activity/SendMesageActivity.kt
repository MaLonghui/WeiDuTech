package com.wd.tech.activity

import android.content.Context
import android.content.Intent
import android.os.Handler
import android.support.v7.widget.LinearLayoutManager
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import cn.jpush.im.android.api.JMessageClient
import cn.jpush.im.android.api.content.TextContent
import cn.jpush.im.android.api.enums.ContentType
import cn.jpush.im.android.api.event.MessageEvent
import cn.jpush.im.android.api.model.Conversation
import cn.jpush.im.android.api.model.Message
import cn.jpush.im.api.BasicCallback
import com.wd.tech.R
import com.wd.tech.api.Api
import com.wd.tech.base.BaseActivity
import com.wd.tech.bean.FriendInfomation
import com.wd.tech.mvp.Constanct
import com.wd.tech.mvp.Presenter
import com.wd.tech.utils.JumpActivityUtils
import kotlinx.android.synthetic.main.activity_send_mesage.*
import cn.jpush.im.android.api.options.MessageSendingOptions
import com.wd.tech.adapter.MessagesListAdapter
import kotlinx.android.synthetic.main.activity_search.*


class SendMesageActivity : BaseActivity<Constanct.View, Constanct.Presenter>(), Constanct.View {
    var s1 = ""
    var adapter: MessagesListAdapter? = null
    var phone: String? = null
    override fun getLayoutId(): Int {
        return R.layout.activity_send_mesage
    }

    override fun initPresenter(): Constanct.Presenter {
        return Presenter()
    }

    override fun onResume() {
        super.onResume()
        var sp = getSharedPreferences("config", Context.MODE_PRIVATE)
        var userId = sp.getString("userId", "")
        var sessionId = sp.getString("sessionId", "")
        var sHeadMap = mapOf(Pair("userId", userId), Pair("sessionId", sessionId))
        val id = intent.getIntExtra("id", 1)
        mPresenter!!.getPresenter(Api.FRIEND_INFORMATION, sHeadMap, FriendInfomation::class.java, mapOf(Pair("friend", id)))

    }

    override fun initData() {
        var sp = getSharedPreferences("config", Context.MODE_PRIVATE)
        var userId = sp.getString("userId", "")
        var sessionId = sp.getString("sessionId", "")
        var sHeadMap = mapOf(Pair("userId", userId), Pair("sessionId", sessionId))
        val nickName = intent.getStringExtra("nickName")
        val headPic = intent.getStringExtra("headPic")
        val signature = intent.getStringExtra("signature")
        val id = intent.getIntExtra("id", 1)

        et_input.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                s1 = s.toString()
                //Toast.makeText(this@SendMesageActivity,s1,Toast.LENGTH_LONG).show()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

        })
        et_input.setOnFocusChangeListener { v, hasFocus ->
            if (!hasFocus) {
                val service: InputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                service.hideSoftInputFromWindow(info_search_edit.windowToken, 0)
            }
        }
        send_message_user_name.text = nickName

        var map = hashMapOf(Pair("nickName", nickName)
                , Pair("headPic", headPic),
                Pair("signature", signature)
        )
        send_message_img.setOnClickListener {
            JumpActivityUtils.skipValueActivity(this, UserSettingActivity::class.java, map)
        }
        mPresenter!!.getPresenter(Api.FRIEND_INFORMATION, sHeadMap, FriendInfomation::class.java, mapOf(Pair("friend", id)))
        send_message_recycler.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        JMessageClient.registerEventReceiver(this)
        send_message_back.setOnClickListener {
            onBackPressed()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        JMessageClient.unRegisterEventReceiver(this)
    }

    fun onEvent(event: MessageEvent) {
        val message = event.message
        when (message.contentType) {
            ContentType.text -> {
                //处理文字消息
                val textContent = message.getContent() as TextContent
                textContent.text
                val singleConversation = JMessageClient.getSingleConversation(phone, "")
                val allMessage = singleConversation.allMessage
                adapter!!.replaceData(allMessage)
            }
        }
    }


    override fun View(any: Any) {
        val sp = getSharedPreferences("config", Context.MODE_PRIVATE)
        if (any is FriendInfomation) {

            var friendInfomation: FriendInfomation = any
            val result = friendInfomation.result
            phone = result.phone
            val singleConversation = JMessageClient.getSingleConversation(phone, "")
            val allMessage = singleConversation.allMessage
            val headPic = sp.getString("headPic", "")

            JMessageClient.enterSingleConversation(phone)
            send_message_recycler.scrollToPosition(singleConversation.allMessage.size - 1)
            adapter = MessagesListAdapter(R.layout.message_adapter_layout, allMessage)
            send_message_recycler.adapter = adapter
            adapter!!.setUrl(result.headPic, headPic)

            send_message_btn.setOnClickListener {
                val conversation = Conversation.createSingleConversation(result.phone, "d4cf77f0d3b85e9edc540dee")
                if (s1.equals("")) {
                    Toast.makeText(this@SendMesageActivity, "请输入要发送的消息", Toast.LENGTH_LONG).show()
                } else {
                    val message = conversation.createSendMessage(TextContent(s1))
                    message.setOnSendCompleteCallback(object : BasicCallback() {
                        override fun gotResult(p0: Int, p1: String?) {
                            if (p0 == 0) {
                                Toast.makeText(this@SendMesageActivity, "消息发送成功", Toast.LENGTH_LONG).show()
                                et_input.text = null

                            } else {
                                Toast.makeText(this@SendMesageActivity, "消息发送失败", Toast.LENGTH_LONG).show()
                            }
                        }
                    })
                    val service: InputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    service.hideSoftInputFromWindow(info_search_edit.windowToken, 0)
                    val options = MessageSendingOptions()
                    options.isRetainOffline = false
                    JMessageClient.sendMessage(message)//使用默认控制参数发送消息
                    val singleConversation = JMessageClient.getSingleConversation(phone, "")
                    val allMessage = singleConversation.allMessage
                    adapter!!.replaceData(allMessage)
                }
            }
        }
    }
}

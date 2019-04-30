package com.wd.tech.Fragment

import android.os.Bundle
import android.os.Handler
import android.support.v7.widget.LinearLayoutManager
import cn.jpush.im.android.api.JMessageClient
import cn.jpush.im.android.api.model.Conversation
import com.wd.tech.R
import com.wd.tech.adapter.MessageAdapter
import com.wd.tech.base.BaseFragment
import com.wd.tech.mvp.Constanct
import com.wd.tech.mvp.Presenter
import kotlinx.android.synthetic.main.fragment_message_list.*
import android.support.v4.os.HandlerCompat.postDelayed
import android.support.v7.widget.DividerItemDecoration
import cn.jpush.im.android.api.content.TextContent
import cn.jpush.im.android.api.enums.ContentType
import cn.jpush.im.android.api.event.MessageEvent
import cn.jpush.im.android.api.model.UserInfo
import com.wd.tech.bean.MessageBean


class MessageListFragment : BaseFragment<Constanct.View, Constanct.Presenter>(), Constanct.View {
    var adapter: MessageAdapter? = null
    var data: MutableList<MessageBean> = ArrayList()
    var list: MutableList<Conversation> = ArrayList()
    var bean: MessageBean? = null
    var i = 0
    var conversation: Conversation? = null
    var handler = Handler()
    override fun getLayoutId(): Int {
        return R.layout.fragment_message_list
    }

    override fun initPresenter(): Constanct.Presenter {
        return Presenter()
    }

    override fun onResume() {
        super.onResume()
       /* updataData()*/
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
       /* JMessageClient.registerEventReceiver(this)
        list = JMessageClient.getConversationList()
        println("aaaaa=${list}")
        initView()*/

    }

    override fun initData() {

    }

   /* private fun initView() {
        handler.postDelayed({
            updataData()
        }, 2000)
        initMsgData()
        onClickItem()
    }*/

    override fun onDestroy() {
        super.onDestroy()
        JMessageClient.unRegisterEventReceiver(this)
    }

    private fun onClickItem() {

    }

    /*private fun initMsgData() {
        recycler_message.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        recycler_message.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        *//*adapter = MessageAdapter(this!!.context!!)
        recycler_message.adapter = adapter*//*
       // adapter!!.setDataList(data)

    }*/

   /* private fun updataData() {
        data.clear();
        adapter!!.clear()
        initDataBean()
    }*/
    /*fun onEvent(event: MessageEvent){
        val msg = event.message
        activity!!.runOnUiThread(object : Runnable{
            override fun run() {
                if (JMessageClient.getMyInfo().getUserName() == "1006" || JMessageClient.getMyInfo().getUserName().equals("1006")) {
                    //JMessageClient.createSingleTextMessage(((UserInfo)msg.getTargetInfo()).getUserName(), SharedPrefHelper.getInstance().getAppKey(), "[自动回复]你好，我是机器人")
                    val message1 = JMessageClient.createSingleCustomMessage((msg.targetInfo as UserInfo).userName,null)
                    JMessageClient.sendMessage(message1)
                }
                updataData()
            }


        })
    }*/

   /* private fun initDataBean() {
        list = JMessageClient.getConversationList()
        if (list.size > 0) {
            do {
                i++
                *//*if (list[i].latestMessage.content.contentType== ContentType.prompt) {
                    bean!!.content = (list[i].latestMessage.content).
                }*//*
                var x = 112
              //  bean = MessageBean(true,true,0,"1",list[i].id,list[i].title,"aaaa","11","112",true, this!!.conversation!!,1,x.toLong())
                *//*bean!!.msgID = list[i].id
                bean!!.userName = (list[i].targetInfo as UserInfo).userName
                bean!!.conversation = list[i]
                bean!!.time = "${list[i].unReadMsgCnt}"
                bean!!.img = "${list[i].avatarFile.toURI()}"*//*
                data.add(bean!!)
            } while (i < list.size)
        }
        adapter!!.notifyDataSetChanged()
    }*/

    override fun View(any: Any) {
    }

}

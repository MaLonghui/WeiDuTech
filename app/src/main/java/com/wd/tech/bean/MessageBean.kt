package com.wd.tech.bean

import cn.jpush.im.android.api.model.Conversation



data class MessageBean (
    //登录状态
    var login: Boolean,
    //在线状态
    var online: Boolean,
    var type: Int,
    var img: String,
    var msgID: String,
    var title: String,
    var content: String,
    var time: String,
    var userName: String,
    var isFriends: Boolean,
    var conversation: Conversation,
    var MsgType: Int,
    var serialVersionUID: Long = 1L
)
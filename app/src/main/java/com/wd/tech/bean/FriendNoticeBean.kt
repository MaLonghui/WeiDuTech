package com.wd.tech.bean

data class FriendNoticeBean(
    var message: String,
    var result: List<FriendNoticeResult>,
    var status: String
)

data class FriendNoticeResult(
    var fromHeadPic: String,
    var fromNickName: String,
    var fromUid: Int,
    var noticeId: Int,
    var noticeTime: Long,
    var receiveUid: Int,
    var remark: String,
    var status: Int
)
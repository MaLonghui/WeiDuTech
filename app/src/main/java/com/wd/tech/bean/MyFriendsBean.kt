package com.wd.tech.bean

data class MyFriendsBean(
    var message: String,
    var result: List<FriendResult>,
    var status: String
)

data class FriendResult(
    var friendUid: Int,
    var headPic: String,
    var nickName: String,
    var remarkName: String,
    var vipFlag: Int
)
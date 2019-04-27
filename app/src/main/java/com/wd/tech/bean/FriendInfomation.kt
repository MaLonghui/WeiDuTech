package com.wd.tech.bean

data class FriendInfomation(
    var message: String,
    var result:  FriendInfomationResult,
    var status: String
)

data class  FriendInfomationResult(
    var birthday: Long,
    var email: String,
    var headPic: String,
    var integral: Int,
    var myGroupList: List<MyGroup>,
    var nickName: String,
    var phone: String,
    var sex: Int,
    var signature: String,
    var userId: Int,
    var whetherVip: Int
)

data class MyGroup(
    var blackFlag: Int,
    var groupId: Int,
    var groupImage: String,
    var groupName: String,
    var hxGroupId: String,
    var role: Int
)
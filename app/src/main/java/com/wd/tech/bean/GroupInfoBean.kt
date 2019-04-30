package com.wd.tech.bean

data class GroupInfoBean(
    var message: String,
    var result: GroupInfoResult,
    var status: String
)

data class GroupInfoResult(
    var currentCount: Int,
    var description: String,
    var groupId: Int,
    var groupImage: String,
    var groupName: String,
    var hxGroupId: String,
    var maxCount: Int,
    var ownerUid: Int
)
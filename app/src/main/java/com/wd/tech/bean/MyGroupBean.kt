package com.wd.tech.bean

data class MyGroupBean(
    var message: String,
    var result: List<GroupResult>,
    var status: String
)

data class GroupResult(
    var currentNumber: Int,
    var customize: Int,
    var groupId: Int,
    var groupName: String
)
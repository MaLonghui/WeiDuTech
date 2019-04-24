package com.wd.tech.bean

data class UserByPhoneBean(
    var message: String,
    var result: userPhoneResult,
    var status: String
)

data class userPhoneResult(
    var email: String,
    var headPic: String,
    var integral: Int,
    var nickName: String,
    var phone: String,
    var sex: Int,
    var signature: String,
    var userId: Int,
    var whetherFaceId: Int,
    var whetherVip: Int
)
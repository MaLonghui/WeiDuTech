package com.wd.tech.bean

data class PaySuccessBean(
        var appId: String,
        var message: String,
        var nonceStr: String,
        var packageValue: String,
        var partnerId: String,
        var prepayId: String,
        var sign: String,
        var status: String,
        var timeStamp: String,
        var result: String
)
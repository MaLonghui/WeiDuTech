package com.wd.tech.bean

data class UserIntegralBean(
    var message: String,
    var result: IntegralResult,
    var status: String
)

data class IntegralResult(
    var amount: Int,
    var id: Int,
    var updateTime: Long,
    var userId: Int
)
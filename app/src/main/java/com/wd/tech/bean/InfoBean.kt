package com.wd.tech.bean

data class InfoBean(
    var message: String,
    var result: List<InfoResult>,
    var status: String
)

data class InfoResult(
    var collection: Int,
    var id: Int,
    var infoAdvertisingVo: InfoAdvertisingVo,
    var releaseTime: Long,
    var share: Int,
    var source: String,
    var summary: String,
    var thumbnail: String,
    var title: String,
    var whetherAdvertising: Int,
    var whetherCollection: Int,
    var whetherPay: Int
)

data class InfoAdvertisingVo(
    var content: String,
    var id: Int,
    var pic: String
)
package com.wd.tech.bean

data class BannerBean(
    var message: String,
    var result: List<BannerResult>,
    var status: String
)

data class BannerResult(
    var imageUrl: String,
    var jumpUrl: String,
    var rank: Int,
    var title: String
)
package com.wd.tech.bean

data class BannerBean(
    var message: String,
    var result: List<Result>,
    var status: String
)

data class Result(
    var imageUrl: String,
    var jumpUrl: String,
    var rank: Int,
    var title: String
)
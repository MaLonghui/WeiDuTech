package com.wd.tech.bean

data class InfoDetailsBean(
    var message: String,
    var result: InfoDetailsResult,
    var status: String
)

data class InfoDetailsResult(
    var comment: Int,
    var content: String,
    var id: Int,
    var informationList: List<Information>,
    var integralCost: Int,
    var plate: List<Plate>,
    var praise: Int,
    var readPower: Int,
    var relatedArticles: String,
    var releaseTime: Long,
    var share: Int,
    var source: String,
    var summary: String,
    var thumbnail: String,
    var title: String,
    var whetherCollection: Int,
    var whetherGreat: Int,
    var yuanCost: Int
)

data class Information(
    var id: Int,
    var thumbnail: String,
    var title: String
)

data class Plate(
    var id: Int,
    var name: String
)
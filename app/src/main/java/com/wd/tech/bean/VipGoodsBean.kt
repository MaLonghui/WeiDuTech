package com.wd.tech.bean

data class VipGoodsBean(
    var message: String,
    var result: List<VipGoodsResult>,
    var status: String
)

data class VipGoodsResult(
    var commodityId: Int,
    var commodityName: String,
    var imageUrl: String,
    var price: Double
)
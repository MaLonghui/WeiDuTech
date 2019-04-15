package com.wd.tech.bean

data class InfoCommentListBean(
        var message: String,
        var result: List<InfoCommentResult>,
        var status: String
)

data class InfoCommentResult(
    var commentTime: Long,
    var content: String,
    var headPic: String,
    var id: Int,
    var infoId: Int,
    var nickName: String,
    var userId: Int
)
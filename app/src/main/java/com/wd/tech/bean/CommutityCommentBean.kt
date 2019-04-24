package com.wd.tech.bean

data class CommutityCommentBean(
    var message: String,
    var result: List<CommutityResult>,
    var status: String
)

data class CommutityResult(
    var commentTime: Long,
    var content: String,
    var headPic: String,
    var nickName: String,
    var userId: Int
)
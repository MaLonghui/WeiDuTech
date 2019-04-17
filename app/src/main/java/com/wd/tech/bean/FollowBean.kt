package com.wd.tech.bean

/**
 * date:2019/4/16
 * author:冯泽林{2019/4/16}
 * function:
 */
data class FollowBean(
    val message: String,
    val result: List<Follow>,
    val status: String
)

data class Follow(
    val focusTime: Long,
    val focusUid: Int,
    val headPic: String,
    val nickName: String,
    val signature: String,
    val userId: Int,
    val whetherMutualFollow: Int,
    val whetherVip: Int
)
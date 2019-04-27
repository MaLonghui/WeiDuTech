package com.wd.tech.bean

/**
 * date:2019/4/16
 * author:冯泽林{2019/4/16}
 * function:
 */
data class IndividualBean(
    val message: String,
    val result: Result,
    val status: String
)

data class Result(
    val birthday: Long,
    val email: String,
    val headPic: String,
    val integral: Int,
    val nickName: String,
    val phone: String,
    val sex: Int,
    val signature: String,
    val userId: Int,
    val userName: String,
    val whetherFaceId: Int,
    val whetherVip: Int
)
package com.wd.tech.bean

/**
 * date:2019/4/12
 * author:冯泽林{2019/4/12}
 * function:
 */
data class LoginBean(
    val message: String,
    val result: LoginResult,
    val status: String
)

data class LoginResult(
    val headPic: String,
    val nickName: String,
    val phone: String,
    val pwd: String,
    val sessionId: String,
    val userId: Int,
    val userName: String,
    val signature: String,
    val whetherFaceId: Int,
    val whetherVip: Int
)
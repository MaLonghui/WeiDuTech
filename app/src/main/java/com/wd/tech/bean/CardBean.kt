package com.wd.tech.bean

/**
 * date:2019/4/17
 * author:冯泽林{2019/4/17}
 * function:
 */
data class CardBean(
    val message: String,
    val result: List<Card>,
    val status: String
)

data class Card(
    val comment: Int,
    val content: String,
    val `file`: String,
    val headPic: String,
    val id: Int,
    val nickName: String,
    val praise: Int,
    val publishTime: Long,
    val signature: String,
    val userId: Int
)
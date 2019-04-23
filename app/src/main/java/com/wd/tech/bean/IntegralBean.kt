package com.wd.tech.bean

/**
 * date:2019/4/19
 * author:冯泽林{2019/4/19}
 * function:
 */
data class IntegralBean(
    val message: String,
    val result: Integral,
    val status: String
)

data class Integral(
    val amount: Int,
    val id: Int,
    val updateTime: Long,
    val userId: Int
)
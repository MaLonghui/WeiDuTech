package com.wd.tech.bean

/**
 * date:2019/4/21
 * author:冯泽林{2019/4/21}
 * function:
 */
data class DefiniteBean(
    val message: String,
    val result: List<Definite>,
    val status: String
)

data class Definite(
    val amount: Int,
    val createTime: Long,
    val direction: Int,
    val type: Int
)
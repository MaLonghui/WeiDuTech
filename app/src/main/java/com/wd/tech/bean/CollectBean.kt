package com.wd.tech.bean

/**
 * date:2019/4/16
 * author:冯泽林{2019/4/16}
 * function:
 */
data class CollectBean(
    val message: String,
    val result: List<Collect>,
    val status: String
)

data class Collect(
    val createTime: Long,
    val id: Int,
    val infoId: Int,
    val thumbnail: String,
    val title: String
)
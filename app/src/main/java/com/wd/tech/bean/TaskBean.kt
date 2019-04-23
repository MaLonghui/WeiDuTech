package com.wd.tech.bean

/**
 * date:2019/4/19
 * author:冯泽林{2019/4/19}
 * function:
 */
data class TaskBean(
    val message: String,
    val result: List<TaskResult>,
    val status: String
)

data class TaskResult(
    val status: Int,
    val taskId: Int,
    val taskIntegral: Int,
    val taskName: String,
    val taskType: Int
)
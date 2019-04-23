package com.wd.tech.bean

/**
 * date:2019/4/14
 * author:冯泽林{2019/4/14}
 * function:
 */
data class Community(
    val message: String,
    val result: List<CommunityBean>,
    val status: String
)

data class CommunityBean(
        val comment: Int,
        val communityCommentVoList: List<CommunityCommentVoList>,
        val content: String,
        val `file`: String,
        val headPic: String,
        val id: Int,
        val nickName: String,
        var praise: Int,
        val publishTime: Long,
        val signature: String,
        val userId: Int,
        val whetherFollow: Int,
        val whetherGreat: Int,
        var great:Int,
        val whetherVip: Int
)
data class CommunityCommentVoList(
        val userId: String,
        val nickName:String,
        val content :String
)
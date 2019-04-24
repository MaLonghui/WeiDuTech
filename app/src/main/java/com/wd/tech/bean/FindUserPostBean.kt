package com.wd.tech.bean

data class FindUserPostBean(
    var message: String,
    var result: List<userResult>,
    var status: String
)

data class userResult(
    var communityUserPostVoList: List<CommunityUserPostVo>,
    var communityUserVo: CommunityUserVo
)

data class CommunityUserPostVo(
    var comment: Int,
    var content: String,
    var `file`: String,
    var id: Int,
    var praise: Int,
    var whetherGreat: Int
)

data class CommunityUserVo(
    var headPic: String,
    var nickName: String,
    var signature:String,
    var userId: Int,
    var whetherFollow: Int,
    var whetherMyFriend: Int
)
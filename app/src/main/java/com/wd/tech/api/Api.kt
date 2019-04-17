package com.wd.tech.api

class Api {
    companion object {
        var BASE_URL = "https://mobile.bwstudent.com/techApi/"
        //登录
        var LOGIN_URL = "user/v1/login"
        //注册
        var REG_URL = "user/v1/register"

        //1.banner展示列表
        var TECH_BANNER = "information/v1/bannerShow"
        //2.资讯推荐展示列表(包含单独板块列表展示)
        var TECH_INFOR = "information/v1/infoRecommendList"
        //3. 资讯详情展示
        var INFO_DETAILS = "information/v1/findInformationDetails"
        //9. 查询资讯评论列表
        var INFO_DETAILS_COMMENT = "information/v1/findAllInfoCommentList"
        //社区列表
        var COMMUNITY = "community/v1/findCommunityList"
        //4.所有板块查询
        var INFO_PLATE = "information/v1/findAllInfoPlate"
        //10.根据标题模糊查询
        var INFO_TITLE = "information/v1/findInformationByTitle"
        //6.资讯点赞
        var INFO_GREAT = "information/verify/v1/addGreatRecord"
        //7. 取消点赞
        var INFO_CANCEL_GREAT="information/verify/v1/cancelGreat"
        //14.添加收藏
        var INFO_COLLECT = "user/verify/v1/addCollection"
        //15.取消收藏（支持批量操作）
        var INFO_CANCEl_COLLECT ="user/verify/v1/cancelCollection"

    }
}
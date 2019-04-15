
package com.wd.tech.api
class Api {
    companion object {
        public var BASE_URL = "https://mobile.bwstudent.com/techApi/"
        //1.banner展示列表
        public var TECH_BANNER = "information/v1/bannerShow"
        //2.资讯推荐展示列表(包含单独板块列表展示)
        public var TECH_INFOR = "information/v1/infoRecommendList"
        //3. 资讯详情展示
        var INFO_DETAILS = "information/v1/findInformationDetails"
        //9. 查询资讯评论列表
        var INFO_DETAILS_COMMENT = "information/v1/findAllInfoCommentList"

    }
}
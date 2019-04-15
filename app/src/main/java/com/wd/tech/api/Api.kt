package com.wd.tech.api

class Api {
    companion object {
        //登录
        var LOGIN_URL = "user/v1/login"
        //注册
        var REG_URL = "user/v1/register"
        public var BASE_URL = "https://mobile.bwstudent.com/techApi/"
        //资讯banner
        public var TECH_BANNER = "information/v1/bannerShow"
        //资讯展示
        public var TECH_INFOR = "information/v1/infoRecommendList"
//        社区列表
        var COMMUNITY="community/v1/findCommunityList"
    }
}
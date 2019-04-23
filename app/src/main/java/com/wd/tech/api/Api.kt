package com.wd.tech.api

import com.wd.tech.bean.Card
import com.wd.tech.customize.SignCalendar

class Api {
    companion object {
        var BASE_URL = "https://mobile.bwstudent.com/techApi/"
        //登录
        var LOGIN_URL = "user/v1/login"
        //注册
        var REG_URL = "user/v1/register"
        //根据用户ID查询用户信息
        var INDIVIDUALINFORMATION="user/verify/v1/getUserInfoByUserId"
        //修改签名
        var SIGNATURE="user/verify/v1/modifySignature"
        //修改用户昵称
        var ALTERUSER="user/verify/v1/modifyNickName"
        //上传头像
        var HEAD="user/verify/v1/modifyHeadPic"
        //修改密码
        var PWD="user/verify/v1/modifyUserPwd"
        //用户签到
        var SIGNIN="user/verify/v1/userSign"
        //用户签到状态
        var SIGNINTURN="user/verify/v1/findUserSignStatus"
        //连续签到
        var DAYSIGN="user/verify/v1/findContinuousSignDays"
        //签到日期
        var TIMEDAYSIGN="user/verify/v1/findUserSignRecording"
        //用户收藏
        var COLLECT="user/verify/v1/findAllInfoCollection"
        //关注
        var FOLLOW="user/verify/v1/findFollowUserList"
        //帖子
        var CARD="community/verify/v1/findMyPostById"
        //删除帖子
        var DELETECARD="community/verify/v1/deletePost"
        //用户积分查询
        var INTEGRAL="user/verify/v1/findUserIntegral"
        //用户积分查询明细
        var DEFINITE="user/verify/v1/findUserIntegralRecord"
        //任务查询列表
        var TASK="user/verify/v1/findUserTaskList"
        //做任务
        var ZUOTASK="user/verify/v1/doTheTask"
        //1.banner展示列表
        var TECH_BANNER = "information/v1/bannerShow"
        //2.资讯推荐展示列表(包含单独板块列表展示)
        var TECH_INFOR = "information/v1/infoRecommendList"
        // 社区列表
        var COMMUNITY = "community/v1/findCommunityList"
        //点赞
        var GIVEALIKE="community/verify/v1/addCommunityGreat"
        //取消点赞
        var GIVEDELETE="community/verify/v1/cancelCommunityGreat"
        //3. 资讯详情展示
        var INFO_DETAILS = "information/v1/findInformationDetails"
        //9. 查询资讯评论列表
        var INFO_DETAILS_COMMENT = "information/v1/findAllInfoCommentList"
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
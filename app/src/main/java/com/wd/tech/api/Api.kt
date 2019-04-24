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
        //微信登录
        var WXLOGIN_API="user/v1/weChatLogin"
        //根据用户ID查询用户信息
        var INDIVIDUALINFORMATION = "user/verify/v1/getUserInfoByUserId"
        //修改签名
        var SIGNATURE = "user/verify/v1/modifySignature"
        //修改用户昵称
        var ALTERUSER = "user/verify/v1/modifyNickName"
        //上传头像
        var HEAD = "user/verify/v1/modifyHeadPic"
        //修改密码
        var PWD = "user/verify/v1/modifyUserPwd"
        //用户签到
        var SIGNIN = "user/verify/v1/userSign"
        //用户签到状态
        var SIGNINTURN = "user/verify/v1/findUserSignStatus"
        //连续签到
        var DAYSIGN = "user/verify/v1/findContinuousSignDays"
        //签到日期
        var TIMEDAYSIGN = "user/verify/v1/findUserSignRecording"
        //用户收藏
        var COLLECT = "user/verify/v1/findAllInfoCollection"
        //关注
        var FOLLOW = "user/verify/v1/findFollowUserList"
        //帖子
        var CARD = "community/verify/v1/findMyPostById"
        //删除帖子
        var DELETECARD = "community/verify/v1/deletePost"
        //用户积分查询
        var INTEGRAL = "user/verify/v1/findUserIntegral"
        //用户积分查询明细
        var DEFINITE = "user/verify/v1/findUserIntegralRecord"
        //任务查询列表
        var TASK = "user/verify/v1/findUserTaskList"
        //做任务
        var ZUOTASK = "user/verify/v1/doTheTask"
        //1.banner展示列表
        var TECH_BANNER = "information/v1/bannerShow"
        //2.资讯推荐展示列表(包含单独板块列表展示)
        var TECH_INFOR = "information/v1/infoRecommendList"
        //社区点赞
        // 社区列表
        var COMMUNITY = "community/v1/findCommunityList"
        //点赞
        //社区点赞
        var GIVEALIKE="community/verify/v1/addCommunityGreat"
        //社区取消点赞
        var GIVEDELETE = "community/verify/v1/cancelCommunityGreat"
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
        var INFO_CANCEL_GREAT = "information/verify/v1/cancelGreat"
        //14.添加收藏
        var INFO_COLLECT = "user/verify/v1/addCollection"
        //15.取消收藏（支持批量操作）
        var INFO_CANCEl_COLLECT = "user/verify/v1/cancelCollection"
        //8.社区评论
        var INFO_COMMENT_ADD = "community/verify/v1/addCommunityComment"
        //7.社区用户评论列表（bean方式返参)
        var COMMUNITY_LIST = "community/v1/findCommunityUserCommentList"
        //2.发布帖子
        var COMMUNITY_RELEASE = "community/verify/v1/releasePost"
        //11.查询用户积分
        var USER_INTEGRAL = "user/verify/v1/findUserIntegral"
        //13.资讯积分兑换
        var INFO_PAY_INTEGRRAL = "information/verify/v1/infoPayByIntegral"
        //1.查询所有会员商品
        var VIP_GOODS = "tool/v1/findVipCommodityList"
        //2.用户购买VIP
        var BUY_VIP = "tool/verify/v1/buyVip"
        //支付
        var VIP_PAY = "tool/verify/v1/pay"
        //10. 查询用户发布的帖子
        var FIND_USER_POST = "community/verify/v1/findUserPostById"
        //6.查询用户所有分组
        var FRIEND_GROUP_LIST = "chat/verify/v1/findFriendGroupList"
        //17.查询我的好友列表
        var SEARCH_FREND = "chat/verify/v1/searchFriend"
        //10.查询分组下的好友列表信息
        var MY_FRIEND_LIST = "chat/verify/v1/findFriendListByGroupId"
        //31.根据手机号查询用户信息
        var FIND_USER_PHONE = "user/verify/v1/findUserByPhone"
    }
}
package com.wd.tech.utils;

import android.content.Context;

import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.wd.tech.wxapi.WXEntryActivity;

public class WeiXinUtil {
    private static IWXAPI wxapi;
    Context context;
    WXEntryActivity wxEntryActivity;
    // APP_ID 替换为你的应用从官方网站申请到的合法appID
    public static  String APP_ID = "wx4c96b6b8da494224";

    // IWXAPI 是第三方app和微信通信的openApi接口
    private WeiXinUtil() {

    }
    public  static  boolean success(Context context){
        //判断是否安装过微信
        if (WeiXinUtil.reg(context).isWXAppInstalled()) {
            return  true;
        }else {
            return false;
        }
    }
    public static IWXAPI reg(Context context){
        if (context!=null) {
            //AppConst.WEIXIN.APP_ID是指你应用在微信开放平台上的AppID，记得替换。
             wxapi = WXAPIFactory.createWXAPI(context, APP_ID, true);
            //注册到微信
            wxapi.registerApp(APP_ID);

            return wxapi;
        }else {
            return  null;
        }
    }

    public static IWXAPI detach() {
        wxapi.detach();
        return null;
    }

}

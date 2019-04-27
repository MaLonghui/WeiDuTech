package com.wd.tech.wxapi

import android.content.Context
import android.content.Intent
import android.os.Handler
import android.widget.Toast
import com.tencent.mm.opensdk.constants.ConstantsAPI
import com.tencent.mm.opensdk.modelbase.BaseReq
import com.tencent.mm.opensdk.modelbase.BaseResp
import com.tencent.mm.opensdk.modelmsg.SendAuth
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler
import com.wd.tech.R
import com.wd.tech.activity.ShowActivity
import com.wd.tech.api.Api
import com.wd.tech.base.BaseActivity
import com.wd.tech.bean.LoginBean
import com.wd.tech.bean.UserPublicBean
import com.wd.tech.mvp.Constanct
import com.wd.tech.mvp.Presenter
import com.wd.tech.utils.MD5Utils
import com.wd.tech.utils.WeiXinUtil

class WXEntryActivity : BaseActivity<Constanct.View, Constanct.Presenter>(), Constanct.View, IWXAPIEventHandler {
    override fun onReq(p0: BaseReq?) {
    }

    var code: String? = null
    override fun getLayoutId(): Int {
        return R.layout.activity_wxentry
    }

    override fun initPresenter(): Constanct.Presenter {
        return Presenter()
    }

    override fun initData() {
        WeiXinUtil.reg(this@WXEntryActivity)!!.handleIntent(getIntent(), this@WXEntryActivity)
        val millis = System.currentTimeMillis()
        val millisStr = millis.toString()
        var sign = millisStr + "wxShare" + "tech"
        val mD5Utils = MD5Utils()
        val signStr = mD5Utils.string2MD5(sign)
        mPresenter!!.postPresenter(Api.WX_SHARE, mapOf(Pair("time", millisStr), Pair("sign", signStr)), UserPublicBean::class.java, mapOf())

    }

    override fun View(any: Any) {
        if (any is LoginBean) {
            if (any.status.equals("0000")) {
                val sp = getSharedPreferences("config", Context.MODE_PRIVATE)
                sp.edit().putString("userId", any.result.userId.toString()).putString("sessionId", any.result.sessionId).commit()
                startActivity(Intent(this@WXEntryActivity, ShowActivity::class.java))
                finish()
            }
        }else if(any is UserPublicBean){
            var userPublicBean:UserPublicBean = any
            Toast.makeText(this,userPublicBean.message,Toast.LENGTH_LONG).show()
        }
    }

    override fun onResp(baseResp: BaseResp) {
        when (baseResp.errCode) {
            BaseResp.ErrCode.ERR_OK -> {
                code = (baseResp as SendAuth.Resp).code
                mPresenter!!.postPresenter(Api.WXLOGIN_API, mapOf(pair = Pair("ak", "0110010010000")), LoginBean::class.java, mapOf(pair = Pair("code", code!!)))

            }
            else -> {


            }
        }

        finish()

    }
}

package com.wd.tech.wxapi

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast

import com.tencent.mm.opensdk.constants.ConstantsAPI
import com.tencent.mm.opensdk.modelbase.BaseReq
import com.tencent.mm.opensdk.modelbase.BaseResp
import com.tencent.mm.opensdk.modelpay.PayReq
import com.tencent.mm.opensdk.openapi.IWXAPI
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler
import com.tencent.mm.opensdk.openapi.WXAPIFactory
import com.wd.tech.R
import com.wd.tech.app.MyApp
import com.wd.tech.utils.WeiXinUtil


class WXPayEntryActivity : AppCompatActivity(), IWXAPIEventHandler {
    private var api: IWXAPI? = null
    var myApp: MyApp = MyApp()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wxpay_entry)
        if (api == null) {
            api = WXAPIFactory.createWXAPI(myApp.getmContext(), null);
            req = PayReq()
            api!!.registerApp(WeiXinUtil.APP_ID)
        }

        WeiXinUtil.reg(this@WXPayEntryActivity).handleIntent(intent, this)
        val intent = intent
        req!!.appId = intent.getStringExtra("appId")
        req!!.nonceStr = intent.getStringExtra("nonceStr")
        req!!.partnerId = intent.getStringExtra("partnerId")
        req!!.prepayId = intent.getStringExtra("prepayId")
        req!!.sign = intent.getStringExtra("sign")
        req!!.timeStamp = intent.getStringExtra("timeStamp")
        req!!.packageValue = intent.getStringExtra("packageValue")
        api!!.sendReq(req)

    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        setIntent(intent)
        api!!.handleIntent(intent, this)
    }

    override fun onReq(req: BaseReq) {}

    override fun onResp(resp: BaseResp) {
        if (resp.type == ConstantsAPI.COMMAND_PAY_BY_WX) {
            if (resp.errCode == 0) {
                Toast.makeText(this, "付款成功", Toast.LENGTH_SHORT).show()
                //Intent intent = new Intent(this, ReccordActivity.class);
                // startActivity(intent);

            } else if (resp.errCode == -2) {
                Toast.makeText(this, "您已取消付款", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "参数错误", Toast.LENGTH_SHORT).show()
            }
        }
        finish()
    }

    companion object {

        private var req: PayReq? = null
    }
}

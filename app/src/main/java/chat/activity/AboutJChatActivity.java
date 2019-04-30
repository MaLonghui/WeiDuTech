package chat.activity;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wd.tech.R;

import cn.jpush.im.android.api.JMessageClient;

/**
 * Created by ${chenyn} on 2017/2/22.
 */

public class AboutJChatActivity extends BaseActivity {

    private TextView mJChat_version;
    private TextView mSDK_version;
    private RelativeLayout com_wd_techs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_jchat);

        initView();
        initData();
    }

    private void initView() {
        initTitle(true, true, "关于JChat", "", false, "");
        mJChat_version = (TextView) findViewById(R.id.jchat_version);
        mSDK_version = (TextView) findViewById(R.id.sdk_version);
        com_wd_techs = (RelativeLayout) findViewById(R.id.com_wd_techs);
    }

    //跳转极光官网
    public void initData() {
        com_wd_techs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("https://www.cn");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });
        PackageManager manager = getPackageManager();
        try {
            PackageInfo packageInfo = manager.getPackageInfo(getPackageName(), 0);
            mJChat_version.setText(packageInfo.versionName);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        //极光IM版本以及sdk版本
        mSDK_version.setText(JMessageClient.getSdkVersionString());
    }
}

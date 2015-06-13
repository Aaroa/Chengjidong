package com.jiedaoche.tigeroil.ui.activitys;

import android.os.Bundle;
import android.view.View;

import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;

import com.jiedaoche.tigeroil.ui.BaseActivity;
import com.jiedaoche.tigeroil.ui.TOApplication;
import com.jiedaoche.tigeroil.utils.ISkipActivityUtil;
import com.jiedaoche.tigeroil.utils.ISpfUtil;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.event.OnClick;

@ContentView(R.layout.activity_setting)
public class SettingActivity extends BaseActivity {

	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		ViewUtils.inject(this);
		TOApplication.getInstance().addActivity(this);
		setHeadTitle("设置");
	}

	@Override
	public void onClickLeftBtn() {
		this.finish();
	};

	@OnClick({ R.id.setting_about, R.id.setting_idea, R.id.setting_share,
			R.id.exit_btn })
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.setting_idea:
			ISkipActivityUtil.startIntent(this, AdiceActivtiy.class);
			break;
		case R.id.setting_share:
			showShare();
			break;
		case R.id.setting_about:
			ISkipActivityUtil.startIntent(this, AboutActivity.class);
			break;
		case R.id.exit_btn:
			ISpfUtil.setValue(this, "userName", "");
			ISpfUtil.setValue(this, "userPwd", "");
			ISkipActivityUtil.startIntent(this, LoginActivity.class);
			TOApplication.getInstance().exit();
			break;

		default:
			break;
		}
	}

	private void showShare() {
		ShareSDK.initSDK(this);
		OnekeyShare oks = new OnekeyShare();
		// 分享时Notification的图标和文字
		// oks.setno
		// oks.setNotification(R.drawable.ic_launcher, "老虎油");
		oks.setTitle("老虎油");
		oks.setText("我是分享文本");
		oks.setImageUrl("http://f1.sharesdk.cn/imgs/2014/05/21/oESpJ78_533x800.jpg");
		// 启动分享GUI
		oks.show(this);
	}
}

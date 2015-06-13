package com.jiedaoche.tigeroil.ui.activitys;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.jiedaoche.tigeroil.ui.BaseActivity;
import com.jiedaoche.tigeroil.ui.TOApplication;
import com.jiedaoche.tigeroil.utils.ISkipActivityUtil;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

/**
 * @ClassName: RegistActivity
 * @Description: 注册界面
 * @author Aaron
 * @date 2015-5-12 上午10:28:45
 * 
 */
@ContentView(R.layout.activity_regist)
public class RegistActivity extends BaseActivity {

	@ViewInject(R.id.regist_phone_et)
	private EditText mPhoneEdit;

	@ViewInject(R.id.regist_verify_et)
	private EditText mVerifyEdit;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		ViewUtils.inject(this);
		TOApplication.getInstance().addActivity(this);
		setHeadTitle("注册(1/2)");
	}

	@Override
	public void onClickLeftBtn() {
		super.onClickLeftBtn();
		this.finish();
	}

	@OnClick({ R.id.regist_btn, R.id.regist_verify_btn })
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.regist_btn:
			ISkipActivityUtil.startIntent(this, RegistInfoActivity.class);
			break;
		case R.id.regist_verify_btn:
			break;

		default:
			break;
		}
	}
}

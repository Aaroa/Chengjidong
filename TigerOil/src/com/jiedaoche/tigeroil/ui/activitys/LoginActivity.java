package com.jiedaoche.tigeroil.ui.activitys;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.jiedaoche.tigeroil.ui.BaseActivity;
import com.jiedaoche.tigeroil.ui.TOApplication;
import com.jiedaoche.tigeroil.utils.ISkipActivityUtil;
import com.jiedaoche.tigeroil.utils.ISpfUtil;
import com.jiedaoche.tigeroil.utils.IStringUtils;
import com.jiedaoche.tigeroil.widget.LoginEditView;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

/**
 * @ClassName: LoginActivity
 * @Description: 登录入口界面
 * @author Aaron
 * @date 2015-5-12 上午10:26:29
 * 
 */
@ContentView(R.layout.activity_login)
public class LoginActivity extends BaseActivity {

	@ViewInject(R.id.login_user_name_edit)
	private LoginEditView mUserNameEdit;

	@ViewInject(R.id.login_user_pass_edit)
	private LoginEditView mUserPassEdit;

	@ViewInject(R.id.login_btn)
	private Button mLoginBtn;

	protected boolean mIsEngineInitSuccess;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		if (!IStringUtils.isEmpty(ISpfUtil.getValue(this, "userName", "")
				.toString())
				&& !IStringUtils.isEmpty(ISpfUtil.getValue(this, "userPwd", "")
						.toString())) {
			ISkipActivityUtil.startIntent(this, MainActivity.class);
			this.finish();
		}
		ViewUtils.inject(this);
		TOApplication.getInstance().addActivity(this);
		setHeadViewVisibility(View.GONE);

		initView();
	}

	private void initView() {
		mUserNameEdit.setIcon(R.drawable.login_icon_name);
		mUserNameEdit.setEditHint(R.string.login_user_name_hint);

		mUserPassEdit.setIcon(R.drawable.login_icon_pass);
		mUserPassEdit.setEditHint(R.string.login_user_pass_hint);
	}

	@OnClick({ R.id.login_btn, R.id.login_regist_text })
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.login_btn:
			if (IStringUtils.isEmpty(mUserNameEdit.getText())) {
				showToast("请输入用户名");
				return;
			}
			if (IStringUtils.isEmpty(mUserPassEdit.getText())) {
				showToast("密码不能为空");
				return;
			}
			if (!"test".equals(mUserNameEdit.getText())
					&& !"123456".equals(mUserPassEdit.getText())) {
				showToast("用户名或者密码错误");
				return;
			}
			ISpfUtil.setValue(this, "userName", mUserNameEdit.getText());
			ISpfUtil.setValue(this, "userPwd", mUserPassEdit.getText());
			ISkipActivityUtil.startIntent(this, MainActivity.class);
			this.finish();
			break;
		case R.id.login_regist_text:
			ISkipActivityUtil.startIntent(this, RegistActivity.class);
			break;

		default:
			break;
		}
	}
}
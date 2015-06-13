package com.jiedaoche.tigeroil.ui.activitys;

import android.os.Bundle;
import android.view.View;

import com.jiedaoche.tigeroil.ui.BaseActivity;
import com.jiedaoche.tigeroil.ui.TOApplication;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.event.OnClick;

@ContentView(R.layout.activity_adout)
public class AboutActivity extends BaseActivity {

	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		ViewUtils.inject(this);
		TOApplication.getInstance().addActivity(this);
		setHeadTitle("关于");
	}

	@Override
	public void onClickLeftBtn() {
		// TODO Auto-generated method stub
		super.onClickLeftBtn();
		this.finish();
	}

	@OnClick(R.id.update_version_layout)
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.update_version_layout:
			showToast("目前已是最新版本");
			break;

		default:
			break;
		}
	}
}

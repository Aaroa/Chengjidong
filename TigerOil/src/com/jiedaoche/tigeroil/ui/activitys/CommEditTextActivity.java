package com.jiedaoche.tigeroil.ui.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.jiedaoche.tigeroil.ui.BaseActivity;
import com.jiedaoche.tigeroil.ui.TOApplication;
import com.jiedaoche.tigeroil.utils.IStringUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

@ContentView(R.layout.common_editview_layout)
public class CommEditTextActivity extends BaseActivity {

	private Bundle bundle;

	private int RESPONE_CODE;

	@ViewInject(R.id.commont_editview)
	private EditText mEditText;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		ViewUtils.inject(this);
		TOApplication.getInstance().addActivity(this);
		Intent intent = getIntent();
		bundle = intent.getExtras();
		setHeadTitle(bundle.getString("title"));
		RESPONE_CODE = bundle.getInt("responeCode");
		String hintText = bundle.getString("hint");
		if (!IStringUtils.isEmpty(hintText)) {
			mEditText.setHint(hintText);
		} else {
			mEditText.setText("请输入");
		}

		String text = bundle.getString("msg");
		if (!IStringUtils.isEmpty(text)) {
			mEditText.setText(text);
		}
	}

	@Override
	public void onClickLeftBtn() {
		super.onClickLeftBtn();
		this.finish();
	}

	@OnClick(R.id.commont_editview_btn)
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.commont_editview_btn:
			Intent intent = new Intent();
			intent.putExtra("result", mEditText.getText().toString());
			setResult(RESPONE_CODE, intent);
			this.finish();
			break;

		default:
			break;
		}
	}
}

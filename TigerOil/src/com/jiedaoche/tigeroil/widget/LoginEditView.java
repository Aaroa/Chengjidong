package com.jiedaoche.tigeroil.widget;

import com.jiedaoche.tigeroil.ui.activitys.R;
import com.jiedaoche.tigeroil.utils.IStringUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import android.view.View.OnClickListener;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

/**
 * 
 * @ClassName: LoginEditView
 * @Description: 自定义输入框【类似登录输入框】
 * @author Aaron
 * @date 2015-5-12 上午10:42:43
 * 
 */
@SuppressLint("InflateParams")
public class LoginEditView extends LinearLayout implements OnClickListener,
		TextWatcher {

	@ViewInject(R.id.comm_editview_icon)
	private ImageView mIconIV;

	@ViewInject(R.id.comm_editview_delete)
	private ImageView mDeleteIV;

	@ViewInject(R.id.comm_eidtview)
	private EditText mEditText;

	public LoginEditView(Context context) {
		super(context);
		init();
	}

	@SuppressLint("InflateParams")
	public LoginEditView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	private void init() {
		View view = LayoutInflater.from(getContext()).inflate(
				R.layout.comm_login_editview_layout, null);
		ViewUtils.inject(this, view);
		addView(view);
		mEditText.addTextChangedListener(this);
		mDeleteIV.setOnClickListener(this);
	}

	public void setIcon(int resid) {
		mIconIV.setImageResource(resid);
	}

	public void setEditHint(int resid) {
		mEditText.setHint(resid);
	}

	public void setEditHint(String str) {
		mEditText.setHint(str);
	}

	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {
		case R.id.comm_editview_delete:
			mEditText.getText().clear();
			mDeleteIV.setVisibility(View.GONE);
			break;

		default:
			break;
		}
	}

	public String getText() {
		return mEditText.getText().toString();
	}

	@Override
	public void afterTextChanged(Editable arg0) {

	}

	@Override
	public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
			int arg3) {

	}

	@Override
	public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
		if (!IStringUtils.isEmpty(arg0.toString())) {
			mDeleteIV.setVisibility(View.VISIBLE);
		}
	}
}

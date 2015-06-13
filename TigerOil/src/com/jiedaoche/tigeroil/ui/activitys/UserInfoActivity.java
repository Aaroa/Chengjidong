package com.jiedaoche.tigeroil.ui.activitys;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.jiedaoche.tigeroil.common.dialog.PopupWindowUtil;
import com.jiedaoche.tigeroil.ui.BaseActivity;
import com.jiedaoche.tigeroil.ui.TOApplication;
import com.jiedaoche.tigeroil.utils.ISkipActivityUtil;
import com.jiedaoche.tigeroil.utils.IStringUtils;
import com.library.IUtils.Dialog.IDialogFactory;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

/**
 * 
 * @ClassName: UserInfoActivity
 * @Description: 用户信息
 * @author Aaron
 * @date 2015年5月22日 上午11:41:20
 * 
 */
@ContentView(R.layout.activity_user_info)
public class UserInfoActivity extends BaseActivity {

	@ViewInject(R.id.user_car_code_add_layout)
	private LinearLayout mAddCarCodeLayout;

	@ViewInject(R.id.user_info_name_tv)
	private TextView name;

	@ViewInject(R.id.user_info_nick_name_tv)
	private TextView nickName;

	@ViewInject(R.id.user_info_sex_tv)
	private TextView sex;

	@ViewInject(R.id.user_info_phone_tv)
	private TextView phone;

	private List<String> mList = new ArrayList<String>();

	private int variable = -1;

	private Dialog dialog;

	private PopupWindow mPopupWindow;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		ViewUtils.inject(this);
		TOApplication.getInstance().addActivity(this);
		setHeadTitle("个人信息");

		mList.add("粤B8888");
		mList.add("粤A8888");
		mList.add("粤B9999");

		initCarCodeView();
	}

	@Override
	public void onClickLeftBtn() {
		super.onClickLeftBtn();
		this.finish();
	}

	@OnClick({ R.id.user_info_head_layout, R.id.user_info_name,
			R.id.user_info_nick_name, R.id.user_info_phone, R.id.user_info_sex,
			R.id.user_info_add_number })
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.user_info_head_layout:

			break;
		case R.id.user_info_add_number:// 添加车牌号
			Bundle bundle = new Bundle();
			bundle.putInt("responeCode", ActivitySkipCode.ADDCARCODE.value);
			bundle.putString("title", "添加车牌号");
			bundle.putString("hint", "输入要添加的车牌号");
			ISkipActivityUtil.startIntentForResult(this, this,
					CommEditTextActivity.class, bundle,
					ActivitySkipCode.REQUEST.value);
			break;
		case R.id.user_info_name:
			Bundle bundlee = new Bundle();
			bundlee.putInt("responeCode", ActivitySkipCode.UPDATENAME.value);
			bundlee.putString("title", "修改姓名");
			bundlee.putString("hint", "");
			bundlee.putString("msg", name.getText().toString());
			ISkipActivityUtil.startIntentForResult(UserInfoActivity.this,
					UserInfoActivity.this, CommEditTextActivity.class, bundlee,
					ActivitySkipCode.REQUEST.value);
			break;
		case R.id.user_info_nick_name:
			Bundle bundlen = new Bundle();
			bundlen.putInt("responeCode", ActivitySkipCode.UPDATENIKENAME.value);
			bundlen.putString("title", "修改别名");
			bundlen.putString("hint", "");
			bundlen.putString("msg", nickName.getText().toString());
			ISkipActivityUtil.startIntentForResult(UserInfoActivity.this,
					UserInfoActivity.this, CommEditTextActivity.class, bundlen,
					ActivitySkipCode.REQUEST.value);
			break;
		case R.id.user_info_phone:
			Bundle bundlep = new Bundle();
			bundlep.putInt("responeCode",
					ActivitySkipCode.UPDATEPHONENUMBER.value);
			bundlep.putString("title", "修改手机号码");
			bundlep.putString("hint", "");
			bundlep.putString("msg", phone.getText().toString());
			ISkipActivityUtil.startIntentForResult(UserInfoActivity.this,
					UserInfoActivity.this, CommEditTextActivity.class, bundlep,
					ActivitySkipCode.REQUEST.value);
			break;
		case R.id.user_info_sex:
			createSexDialog();
			break;

		default:
			break;
		}
	}

	private void createSexDialog() {
		@SuppressWarnings("static-access")
		View view = getLayoutInflater().from(this).inflate(
				R.layout.sex_popup_dialog_layout, null);
		view.findViewById(R.id.sex_pop_man_tv).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						sex.setText("男");
						PopupWindowUtil.dismissDialog(mPopupWindow);
					}
				});
		view.findViewById(R.id.sex_pop_girl_tv).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						sex.setText("女");
						PopupWindowUtil.dismissDialog(mPopupWindow);
					}
				});
		;
		view.findViewById(R.id.sex_pop_cancel_btn).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						PopupWindowUtil.dismissDialog(mPopupWindow);
					}
				});
		mPopupWindow = PopupWindowUtil.createPopupWindow(this, view,
				LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		mPopupWindow.showAtLocation(findViewById(R.id.user_info_view),
				Gravity.BOTTOM, 0, 0);
	}

	private void initCarCodeView() {
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		mAddCarCodeLayout.removeAllViews();
		for (int i = 0; i < mList.size(); i++) {
			final View view = getLayoutInflater().inflate(
					R.layout.car_code_layout, null);
			TextView serialNumber = (TextView) view
					.findViewById(R.id.car_code_serial_number);
			TextView carCode = (TextView) view
					.findViewById(R.id.car_code_number);
			serialNumber.setText((i + 1) + "");
			carCode.setText(mList.get(i));
			view.setTag(i);
			view.setOnClickListener(new OnClickListener() {

				@SuppressLint("ShowToast")
				@Override
				public void onClick(View v) {
					variable = IStringUtils.toInt(view.getTag());
					Bundle bundle = new Bundle();
					bundle.putInt("responeCode",
							ActivitySkipCode.UPDATECARNUMBER.value);
					bundle.putString("title", "修改车牌号");
					bundle.putString("hint", "");
					bundle.putString("msg",
							mList.get(IStringUtils.toInt(view.getTag())));
					ISkipActivityUtil.startIntentForResult(
							UserInfoActivity.this, UserInfoActivity.this,
							CommEditTextActivity.class, bundle,
							ActivitySkipCode.REQUEST.value);
					// showToast(mList.get(IStringUtils.toInt(view.getTag())));
				}
			});
			view.setOnLongClickListener(new OnLongClickListener() {

				@Override
				public boolean onLongClick(View v) {
					dialog = IDialogFactory.showMsgDialog(
							UserInfoActivity.this,
							"提示",
							"你确定要删除"
									+ mList.get(IStringUtils.toInt(view
											.getTag())) + "车牌号?",
							new OnClickListener() {

								@Override
								public void onClick(View v) {
									IDialogFactory.dimissDialog(dialog);
									mList.remove(IStringUtils.toInt(view
											.getTag()));
									initCarCodeView();
								}
							});
					return true;
				}
			});
			if (view != null) {
				mAddCarCodeLayout.setLayoutParams(params);
				mAddCarCodeLayout.addView(view);
			}
		}
	}

	private final int ADDCARNUMBERCALLBACKCODE = 103;
	private final int UPDATECARNUMBERCALLBACKCODE = 104;
	private final int UPDATEPHONENUMBERCALLBACKCODE = 105;
	private final int UPDATENIKENAMECALLBACKCODE = 106;
	private final int UPDATENAMECALLBACKCODE = 107;

	@Override
	protected void onActivityResult(int arg0, int arg1, Intent arg2) {
		super.onActivityResult(arg0, arg1, arg2);
		if (ActivitySkipCode.REQUEST.value == arg0) {
			if (arg2 == null) {
				return;
			}
			String result = arg2.getStringExtra("result");
			switch (arg1) {
			case ADDCARNUMBERCALLBACKCODE:
				if (!IStringUtils.isEmpty(result)) {
					mList.add(0, result);
					initCarCodeView();
				}
				break;
			case UPDATECARNUMBERCALLBACKCODE:
				if (variable == -1) {
					return;
				}
				mList.set(variable, result);
				initCarCodeView();
				variable = -1;
				break;
			case UPDATEPHONENUMBERCALLBACKCODE:
				phone.setText(result);
				break;
			case UPDATENIKENAMECALLBACKCODE:
				nickName.setText(result);
				break;
			case UPDATENAMECALLBACKCODE:
				name.setText(result);
				break;

			default:
				break;
			}
		}
	}

	enum ActivitySkipCode {
		REQUEST(102), ADDCARCODE(103), UPDATECARNUMBER(104), UPDATEPHONENUMBER(
				105), UPDATENIKENAME(106), UPDATENAME(107);
		private int value;

		public int getValue() {
			return value;
		}

		private ActivitySkipCode(int value) {
			this.value = value;
		}
	}
}

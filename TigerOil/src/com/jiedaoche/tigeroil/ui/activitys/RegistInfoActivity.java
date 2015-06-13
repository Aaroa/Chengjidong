package com.jiedaoche.tigeroil.ui.activitys;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.LinearLayout.LayoutParams;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.jiedaoche.tigeroil.common.dialog.PopupWindowUtil;
import com.jiedaoche.tigeroil.ui.BaseActivity;
import com.jiedaoche.tigeroil.ui.TOApplication;
import com.jiedaoche.tigeroil.utils.ISkipActivityUtil;
import com.jiedaoche.tigeroil.utils.IStringUtils;
import com.jiedaoche.tigeroil.utils.ISystemTool;
import com.library.IUtils.Dialog.IDialogFactory;
import com.library.IUtils.Interface.EditDialogCallBack;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

/**
 * 
 * @ClassName: RegistInfoActivity
 * @Description: 注册完善个人信息
 * @author Aaron
 * @date 2015-5-12 下午5:15:52
 * 
 */
@SuppressLint("ViewHolder")
@ContentView(R.layout.activity_regist_info)
public class RegistInfoActivity extends BaseActivity {

	private List<String> mContent = new ArrayList<String>();

	private CarCodeAdapter adapter;

	@ViewInject(R.id.regist_car_code_gridview)
	private GridView mGridView;

	@ViewInject(R.id.regist_info_sex)
	private TextView sex;

	private PopupWindow mPopupWindow;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		ViewUtils.inject(this);
		TOApplication.getInstance().addActivity(this);
		setHeadTitle("注册(2/2)");
		initGridView();
	}

	private void initGridView() {
		adapter = new CarCodeAdapter();
		mGridView.setAdapter(adapter);
	}

	@Override
	public void onClickLeftBtn() {
		super.onClickLeftBtn();
		this.finish();
	}

	@OnClick({ R.id.regist_car_code, R.id.regist_comfir_btn,
			R.id.regist_info_sex })
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.regist_car_code:
			IDialogFactory.createEditTextDialog(this, "输入车牌号",
					new EditDialogCallBack() {

						@Override
						public void result(String str) {
							ISystemTool.hideKeyBoard(RegistInfoActivity.this);
							if (IStringUtils.isEmpty(str)) {
								return;
							}
							mContent.add(str);
							adapter.notifyDataSetChanged();
						}
					});
			break;
		case R.id.regist_comfir_btn:
			ISkipActivityUtil.startIntent(this, MainActivity.class);
			this.finish();
			break;
		case R.id.regist_info_sex:
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
		mPopupWindow.showAtLocation(findViewById(R.id.regist_info_view),
				Gravity.BOTTOM, 0, 0);
	}

	private class CarCodeAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			if (mContent.size() > 0) {
				mGridView.setVisibility(View.VISIBLE);
			}
			return mContent.size();
		}

		@Override
		public Object getItem(int arg0) {
			return mContent.get(arg0);
		}

		@Override
		public long getItemId(int arg0) {
			return arg0;
		}

		@SuppressLint("InflateParams")
		@Override
		public View getView(int arg0, View arg1, ViewGroup arg2) {
			arg1 = LayoutInflater.from(RegistInfoActivity.this).inflate(
					R.layout.regist_car_code_item_layout, null);
			TextView textView = (TextView) arg1
					.findViewById(R.id.car_code_text);
			textView.setText(mContent.get(arg0));
			return arg1;
		}
	}
}

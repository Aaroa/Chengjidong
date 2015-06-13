package com.jiedaoche.tigeroil.modle;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.jiedaoche.tigeroil.common.dialog.PopupWindowUtil;
import com.jiedaoche.tigeroil.common.dialog.SeleteDateTimePickDialog;
import com.jiedaoche.tigeroil.common.dialog.SeleteDateTimePickDialog.SeleteCallBack;
import com.jiedaoche.tigeroil.ui.activitys.R;
import com.jiedaoche.tigeroil.ui.activitys.adapter.SeleteTimeListViewAdapter;
import com.jiedaoche.tigeroil.utils.ISystemTool;
import com.library.IUtils.Dialog.IDialogFactory;

/**
 * 
 * @ClassName: OrderControl
 * @Description: 预约业务逻辑处理
 * @author Aaron
 * @date 2015年5月22日 上午11:54:26
 * 
 */
@SuppressLint("SimpleDateFormat")
public class OrderControl {
	/**
	 * 上下文
	 */
	private Context mContext;

	private long mDateLong;

	private String mTimeLong;

	private Dialog dialog;

	private String oilName;

	private String oilAddress;

	private View mView;

	private PopupWindow popupWindow;

	public OrderControl(Context context, View view) {
		this.mContext = context;
		this.mView = view;
	}

	/**
	 * 
	 * @Title: showOrderSeleteDialog
	 * @Description: 预约订单生成
	 * @param
	 * @return void
	 * @throws
	 */
	public void showOrderSeleteDialog() {
		SeleteDateTimePickDialog.createDatePickDialog(mContext, "选择预约时间",
				new SeleteCallBack() {

					@Override
					public void result(long date) {
						mDateLong = date;
						seleteTimeDialog();
					}
				});
	}

	/**
	 * 
	 * @Title: seleteTimeDialog
	 * @Description: 预约选择时间段
	 * @param
	 * @return void
	 * @throws
	 */
	private void seleteTimeDialog() {
		View view = LayoutInflater.from(mContext).inflate(
				R.layout.popup_defalt_listview_layout, null);
		ListView mListView = (ListView) view.findViewById(R.id.popup_listview);
		final String content[] = mContext.getResources().getStringArray(
				R.array.time);
		SeleteTimeListViewAdapter adapter = new SeleteTimeListViewAdapter(
				mContext, content);
		mListView.setAdapter(adapter);
		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				mTimeLong = content[arg2];
				IDialogFactory.dimissDialog(dialog);
				createOrderViewDialog();
			}
		});
		dialog = IDialogFactory.createShowDialogCancel(mContext, view,
				"选择预约时间段");
		dialog.show();
	}

	/**
	 * 
	 * @Title: createOrderViewDialog
	 * @Description: 订单生成
	 * @param
	 * @return void
	 * @throws
	 */
	public void createOrderViewDialog() {
		View view = LayoutInflater.from(mContext).inflate(R.layout.oder_layout,
				null);
		TextView gName = (TextView) view
				.findViewById(R.id.order_gasstation_name);
		TextView date = (TextView) view.findViewById(R.id.order_date);
		TextView address = (TextView) view.findViewById(R.id.order_address);
		TextView gPhone = (TextView) view
				.findViewById(R.id.order_gasstation_phone);
		TextView name = (TextView) view.findViewById(R.id.order_user_name);
		TextView phone = (TextView) view.findViewById(R.id.order_phone);
		TextView carCode = (TextView) view.findViewById(R.id.order_car_code);
		gName.setText(oilName);
		date.setText(ISystemTool.getData("yyyy年MM月dd日", mDateLong) + " "
				+ mTimeLong);
		address.setText(oilAddress);
		gPhone.setText("020-9868663");
		name.setText("陈先生");
		phone.setText("15632542387");
		carCode.setText("粤A8888");
		dialog = IDialogFactory.showMsgDialog(mContext, "预约订单", view, "支付",
				"稍后支付", new OnClickListener() {

					@Override
					public void onClick(View v) {
						dialog.dismiss();
					}
				}, new OnClickListener() {

					@Override
					public void onClick(View v) {
						dialog.dismiss();
						showPayDialog();
					}
				});
		dialog.show();
	}

	/**
	 * 
	 * @Title: showPayDialog
	 * @Description: 支付对话框
	 * @param
	 * @return void
	 * @throws
	 */
	private void showPayDialog() {
		View view = LayoutInflater.from(mContext).inflate(
				R.layout.pay_popup_dialog_layout, null);
		view.findViewById(R.id.weChat_pay_tv).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						Toast.makeText(mContext, "微信支付", Toast.LENGTH_SHORT)
								.show();
						PopupWindowUtil.dismissDialog(popupWindow);
					}
				});
		view.findViewById(R.id.alipay_tv).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						Toast.makeText(mContext, "支付宝支付", Toast.LENGTH_SHORT)
								.show();
						PopupWindowUtil.dismissDialog(popupWindow);
					}
				});
		popupWindow = PopupWindowUtil.createPopupWindow(mContext, view,
				LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		popupWindow.showAtLocation(mView, Gravity.BOTTOM, 0, 0);

	}

	public String getOilName() {
		return oilName;
	}

	public void setOilName(String oilName) {
		this.oilName = oilName;
	}

	public String getOilAddress() {
		return oilAddress;
	}

	public void setOilAddress(String oilAddress) {
		this.oilAddress = oilAddress;
	}

	public long getmDateLong() {
		return mDateLong;
	}

	public void setmDateLong(long mDateLong) {
		this.mDateLong = mDateLong;
	}

	public String getmTimeLong() {
		return mTimeLong;
	}

	public void setmTimeLong(String mTimeLong) {
		this.mTimeLong = mTimeLong;
	}

}

package com.jiedaoche.tigeroil.modle;

import java.util.Calendar;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.LinearLayout;
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
import com.jiedaoche.tigeroil.view.OnWheelScrollListener;
import com.jiedaoche.tigeroil.view.WheelView;
import com.jiedaoche.tigeroil.view.adapter.ArrayWheelAdapter;
import com.jiedaoche.tigeroil.view.adapter.NumericWheelAdapter;
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

	private Dialog dialog, timeDialog;

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
//				createOrderViewDialog();
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
	public void createOrderViewDialog(boolean isCurrent) {
		View view = LayoutInflater.from(mContext).inflate(R.layout.oder_layout,
				null);
		TextView dateET = (TextView) view
				.findViewById(R.id.order_date_editText);
		TextView gName = (TextView) view
				.findViewById(R.id.order_gasstation_name);
		TextView date = (TextView) view.findViewById(R.id.order_date);
		TextView address = (TextView) view.findViewById(R.id.order_address);
		TextView gPhone = (TextView) view
				.findViewById(R.id.order_gasstation_phone);
		TextView name = (TextView) view.findViewById(R.id.order_user_name);
		TextView phone = (TextView) view.findViewById(R.id.order_phone);
		TextView carCode = (TextView) view.findViewById(R.id.order_car_code);
		if (isCurrent) {
			dateET.setCompoundDrawables(null, null, null, null);
			Calendar c=Calendar.getInstance();
			dateET.setText(ISystemTool.getData("yyyy年MM月dd日  HH:mm", c.getTimeInMillis()));
		}else {
			dateET.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					showSeleteDateDialog();
				}
			});
		}
		gName.setText(oilName);
		date.setText(ISystemTool.getData("yyyy年MM月dd日", mDateLong) + " "
				+ mTimeLong);
		address.setText(oilAddress);
		gPhone.setText("020-9868663");
		name.setText("陈先生");
		phone.setText("15632542387");
		carCode.setText("粤A8888");
		dialog = IDialogFactory.showMsgDialog(mContext, "预约订单", view, "预约",
				"取消", new OnClickListener() {

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

	public void showSeleteDateDialog() {
		View view = LayoutInflater.from(mContext).inflate(
				R.layout.wheel_date_picker, null);

		WheelView year = (WheelView) view.findViewById(R.id.year);
		WheelView month = (WheelView) view.findViewById(R.id.month);
		WheelView day = (WheelView) view.findViewById(R.id.day);
		WheelView startTime = (WheelView) view.findViewById(R.id.start_time);
		WheelView endTime = (WheelView) view.findViewById(R.id.end_time);

		Calendar c = Calendar.getInstance();
		int norYear = c.get(Calendar.YEAR);
		int norMonth = 1;
		int norDay = 1;

		NumericWheelAdapter numericWheelAdapter1 = new NumericWheelAdapter(
				mContext, 1950, norYear);
		numericWheelAdapter1.setLabel("年");
		year.setViewAdapter(numericWheelAdapter1);
		year.setCyclic(true);// 是否可循环滑动
		year.setWheelForeground(android.R.color.white);
		year.addScrollingListener(new OnWheelScrollListener() {
			
			@Override
			public void onScrollingStarted(WheelView wheel) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onScrollingFinished(WheelView wheel) {
				// TODO Auto-generated method stub
				
			}
		});

		NumericWheelAdapter numericWheelAdapter2 = new NumericWheelAdapter(
				mContext, 1, 12, "%02d");
		numericWheelAdapter2.setLabel("月");
		month.setViewAdapter(numericWheelAdapter2);
		month.setCyclic(true);
		month.addScrollingListener(new OnWheelScrollListener() {
			
			@Override
			public void onScrollingStarted(WheelView wheel) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onScrollingFinished(WheelView wheel) {
				// TODO Auto-generated method stub
				
			}
		});

		NumericWheelAdapter numericWheelAdapter = new NumericWheelAdapter(
				mContext, 1, getDay(norYear, norMonth), "%02d");
		numericWheelAdapter.setLabel("日");
		day.setViewAdapter(numericWheelAdapter);
		day.setCyclic(true);
		day.addScrollingListener(new OnWheelScrollListener() {
			
			@Override
			public void onScrollingStarted(WheelView wheel) {
//				Log.d("Aaron", "cc==="+wheel.get)
			}
			
			@Override
			public void onScrollingFinished(WheelView wheel) {
				// TODO Auto-generated method stub
				
			}
		});

		String[] items1 = mContext.getResources().getStringArray(
				R.array.start_time);
		ArrayWheelAdapter<String> numericWheelAdapter3 = new ArrayWheelAdapter<String>(
				mContext, items1);
		startTime.setViewAdapter(numericWheelAdapter3);
		startTime.setCyclic(true);
		startTime.addScrollingListener(new OnWheelScrollListener() {
			
			@Override
			public void onScrollingStarted(WheelView wheel) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onScrollingFinished(WheelView wheel) {
				// TODO Auto-generated method stub
				
			}
		});

		String[] items = mContext.getResources().getStringArray(
				R.array.start_time);
		ArrayWheelAdapter<String> numericWheelAdapter4 = new ArrayWheelAdapter<String>(
				mContext, items);
		endTime.setViewAdapter(numericWheelAdapter4);
		endTime.setCyclic(true);
		endTime.addScrollingListener(new OnWheelScrollListener() {
			
			@Override
			public void onScrollingStarted(WheelView wheel) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onScrollingFinished(WheelView wheel) {
				// TODO Auto-generated method stub
				
			}
		});

		year.setVisibleItems(7);
		month.setVisibleItems(7);
		day.setVisibleItems(7);
		startTime.setVisibleItems(7);
		endTime.setVisibleItems(7);

		year.setCurrentItem(c.get(Calendar.YEAR)-1950);
		month.setCurrentItem(c.get(Calendar.MONTH));
		day.setCurrentItem(c.get(Calendar.DAY_OF_MONTH));
		startTime.setCurrentItem(9);
		endTime.setCurrentItem(12);

		timeDialog = IDialogFactory.createShowViewDialog(mContext, view,
				"选择预约时间", new OnClickListener() {

					@Override
					public void onClick(View v) {
						IDialogFactory.dimissDialog(timeDialog);
					}
				});
		timeDialog.show();
	}

	OnWheelScrollListener scrollListener = new OnWheelScrollListener() {
		@Override
		public void onScrollingStarted(WheelView wheel) {

		}

		@Override
		public void onScrollingFinished(WheelView wheel) {

		}
	};

	/**
	 * 
	 * @param year
	 * @param month
	 * @return
	 */
	private int getDay(int year, int month) {
		int day = 30;
		boolean flag = false;
		switch (year % 4) {
		case 0:
			flag = true;
			break;
		default:
			flag = false;
			break;
		}
		switch (month) {
		case 1:
		case 3:
		case 5:
		case 7:
		case 8:
		case 10:
		case 12:
			day = 31;
			break;
		case 2:
			day = flag ? 29 : 28;
			break;
		default:
			day = 30;
			break;
		}
		return day;
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
		view.findViewById(R.id.sex_pop_cancel_btn).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
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

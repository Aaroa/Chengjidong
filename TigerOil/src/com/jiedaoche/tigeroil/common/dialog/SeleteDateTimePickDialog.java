package com.jiedaoche.tigeroil.common.dialog;

import java.util.Calendar;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;
import android.widget.DatePicker.OnDateChangedListener;

import com.jiedaoche.tigeroil.ui.activitys.R;
import com.library.IUtils.Dialog.IDialogFactory;

/**
 * @ClassName: SeleteDateTimePickDialog
 * @Description: 日期选择对话框
 * @author Aaron
 * @date 2015-5-21 下午10:20:34
 * 
 */
public class SeleteDateTimePickDialog {

	private static long mDateLong = Calendar.getInstance().getTimeInMillis();

	private static Dialog dialog;

	private static DatePicker mDatePicker;

	private static Calendar calendar = Calendar.getInstance();

	@SuppressLint({ "InflateParams", "SimpleDateFormat" })
	public static void createDatePickDialog(Context context, String title,
			final SeleteCallBack callBack) {
		View dateTimeLayout = LayoutInflater.from(context).inflate(
				R.layout.common_date, null);
		mDatePicker = (DatePicker) dateTimeLayout.findViewById(R.id.datepicker);
		initDataPicker(mDatePicker);
		dialog = IDialogFactory.createShowViewDialog(context, dateTimeLayout,
				title, new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						calendar.set(mDatePicker.getYear(), mDatePicker.getMonth(), mDatePicker.getDayOfMonth());
						mDateLong = calendar.getTime().getTime();
						callBack.result(mDateLong);
						IDialogFactory.dimissDialog(dialog);
					}
				});
		dialog.show();
	}

	private static void initDataPicker(final DatePicker datePicker) {
		datePicker.init(calendar.get(Calendar.YEAR),
				calendar.get(Calendar.MONTH),
				calendar.get(Calendar.DAY_OF_MONTH),
				new OnDateChangedListener() {

					@Override
					public void onDateChanged(DatePicker view, int year,
							int monthOfYear, int dayOfMonth) {
					}
				});
	}

	public interface SeleteCallBack {
		public void result(long date);
	}
}

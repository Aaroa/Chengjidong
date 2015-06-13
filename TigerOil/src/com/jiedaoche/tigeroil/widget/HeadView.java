package com.jiedaoche.tigeroil.widget;

import com.jiedaoche.tigeroil.ui.activitys.R;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * 
 * @ClassName: HeadView
 * @Description: 标题栏
 * @author Aaron
 * @date 2015-5-10 上午1:51:48
 * 
 */
@SuppressLint("InflateParams")
public class HeadView extends LinearLayout {

	private TextView mBackText;

	private TextView mCenterText;

	public HeadView(Context context) {
		super(context);
		init();
	}

	public HeadView(Context context, AttributeSet attri) {
		super(context, attri);
		init();
	}

	private void init() {
		View view = LayoutInflater.from(getContext()).inflate(
				R.layout.common_headview_layout, null);
		addView(view);
		mBackText = (TextView) view
				.findViewById(R.id.common_head_view_back_text);
		mCenterText = (TextView) view
				.findViewById(R.id.common_head_view_center_text);
	}

	public void setLeftText(int resid) {
		mBackText.setText(resid);
	}

	public void setLeftText(String str) {
		mBackText.setText(str);
	}

	public void setTitleText(int resid) {
		mCenterText.setText(resid);
	}

	public void setTitleText(String str) {
		mCenterText.setText(str);
	}

	public void onClickLeftBtn(OnClickListener listener) {
		mBackText.setOnClickListener(listener);
	}

	public interface HeadViewListener {
		public void setOnClick();

		public void setHeadTitle(int resid);

		public void setHeadTitle(String str);

		public void setHeadViewVisibility(int visibility);
	}

}

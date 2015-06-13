package com.jiedaoche.tigeroil.ui;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.jiedaoche.tigeroil.ui.activitys.R;
import com.jiedaoche.tigeroil.widget.HeadView;
import com.jiedaoche.tigeroil.widget.HeadView.HeadViewListener;

/**
 * 
 * @ClassName: BaseActivity
 * @Description: Activity基类
 * @author Aaron
 * @date 2015-5-12 上午9:58:57
 * 
 */
@SuppressLint("HandlerLeak")
public abstract class BaseActivity extends FragmentActivity implements
		HeadViewListener {
	/**
	 * 头部View
	 */
	private HeadView mHeadView;

	private FrameLayout mFrameLayout;

	private LayoutInflater mLayoutInflater;

	public Handler mHandler;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		super.setContentView(R.layout.activity_base);
		mLayoutInflater = LayoutInflater.from(this);
		findViewById();
		setOnClick();
		initHandler();
	}

	private void findViewById() {
		mHeadView = (HeadView) findViewById(R.id.common_head_view);
		mFrameLayout = (FrameLayout) findViewById(R.id.common_content_layout);
	}

	private void initHandler() {
		mHandler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				BaseActivity.this.handleMessage(msg);
			}
		};
	}

	public void handleMessage(Message msg) {
	};

	@Override
	public void setContentView(int layoutResID) {
		View view = mLayoutInflater.inflate(layoutResID, null);
		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
				ViewGroup.LayoutParams.MATCH_PARENT,
				ViewGroup.LayoutParams.MATCH_PARENT);
		mFrameLayout.removeAllViews();
		mFrameLayout.addView(view, lp);
	}

	@Override
	public void setContentView(View view, ViewGroup.LayoutParams params) {
		mFrameLayout.removeAllViews();
		mFrameLayout.addView(view, params);
	}

	@Override
	public void setContentView(View view) {
		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
				ViewGroup.LayoutParams.MATCH_PARENT,
				ViewGroup.LayoutParams.MATCH_PARENT);
		mFrameLayout.removeAllViews();
		mFrameLayout.addView(view, lp);
	}

	@Override
	public void setOnClick() {
		mHeadView.onClickLeftBtn(new OnClickListener() {

			@Override
			public void onClick(View v) {
				onClickLeftBtn();
			}
		});
	}

	/**
	 * 
	 * @Title: showToast
	 * @Description: 自定义Toast
	 * @param @param resid
	 * @return void
	 * @throws
	 */
	public void showToast(int resid) {
		Toast toast = Toast.makeText(this, resid, Toast.LENGTH_SHORT);
		toast.setGravity(Gravity.CENTER, 0, 0);
		toast.show();
	}

	/**
	 * 
	 * @Title: showToast
	 * @Description: 定义Toast
	 * @param @param msg
	 * @return void
	 * @throws
	 */
	public void showToast(String msg) {
		Toast toast = Toast.makeText(this, msg, Toast.LENGTH_SHORT);
		toast.setGravity(Gravity.CENTER, 0, 0);
		toast.show();
	}

	public void onClickLeftBtn() {
	};

	@Override
	public void setHeadTitle(String str) {
		mHeadView.setTitleText(str);
	}

	@Override
	public void setHeadTitle(int resid) {
		mHeadView.setTitleText(resid);
	}

	@Override
	public void setHeadViewVisibility(int visibility) {
		mHeadView.setVisibility(visibility);
	}
}

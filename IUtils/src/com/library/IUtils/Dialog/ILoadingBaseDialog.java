package com.library.IUtils.Dialog;

import com.lidrary.Iutil.R;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.LinearLayout.LayoutParams;

/**
 * 加载对话框基类
 * 
 * @author Aaroa
 * @date 2015-01-13
 * @version 1.1
 */
public class ILoadingBaseDialog extends Dialog {
	/**
	 * 提示内容控件
	 */
	private TextView tipText;
	/**
	 * 点击返回是否取消对话框[true不取消，false取消]
	 */
	private boolean backPressCancel;

	public ILoadingBaseDialog(Context context) {
		super(context);
		init();
	}

	private void init() {
		this.getContext().setTheme(R.style.dialog);
		super.setContentView(R.layout.loading_dialog_layout);

		tipText = (TextView) findViewById(R.id.loading_tv);

		Window window = getWindow();
		WindowManager.LayoutParams attributesParams = window.getAttributes();
		attributesParams.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
		attributesParams.dimAmount = 0.4f;
		int width = (int) (window.getWindowManager().getDefaultDisplay()
				.getWidth() * 0.6f);
		window.setLayout(width, LayoutParams.WRAP_CONTENT);
	}

	/**
	 * 设置内容
	 * 
	 * @param msg
	 */
	public void setTipText(String msg) {
		tipText.setText(msg);
	}

	/**
	 * 设置内容
	 * 
	 * @param resid
	 */
	public void setTipText(int resid) {
		if (resid <= 0) {
			return;
		}
		tipText.setText(resid);
	}

	public void setBackPressCancel(boolean backPressCancel) {
		this.backPressCancel = backPressCancel;
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK
				|| keyCode == KeyEvent.KEYCODE_SEARCH) {
			if (backPressCancel) {
				return true;
			}
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		return true;
	}

}

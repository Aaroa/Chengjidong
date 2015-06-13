package com.jiedaoche.tigeroil.common.dialog;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.PopupWindow;

/**
 * 
 * @ClassName: PopupWindowUtil
 * @Description: PopupWIndow工具类
 * @author Aaron
 * @date 2015年5月22日 上午11:42:32
 * 
 */
public class PopupWindowUtil {

	private final static String TAG = PopupWindowUtil.class.getSimpleName();

	private PopupWindow mPopupWindow = null;

	/**
	 * 创建PopupWindow;
	 * 
	 * @param context
	 * @param view
	 *            布局
	 * @param width
	 *            宽
	 * @param height
	 *            高
	 * @param parntView
	 *            在那个控件下显示
	 * @param x
	 *            偏移量
	 * @param y
	 *            偏移量
	 * @return
	 */
	@SuppressLint("NewApi")
	public static PopupWindow createPopupWindow(Context context, View view,
			int width, int height) {
		PopupWindow popupWindow = new PopupWindow(view, width, height);
		popupWindow.setBackgroundDrawable(new ColorDrawable(0x88000000));
		popupWindow.setOutsideTouchable(true);
		popupWindow.setFocusable(true);
		popupWindow.setSplitTouchEnabled(true);
		popupWindow.setAnimationStyle(android.R.style.Animation_Dialog);
		return popupWindow;
	}

	/**
	 * 在指定控件上方显示，默认x座标与指定控件的中点x座标相同
	 * 
	 * @param anchor
	 * @param xoff
	 * @param yoff
	 */
	public static void showAsPullUp(PopupWindow popupWindow, View anchor,
			int xoff, int yoff) {
		if (popupWindow == null) {
			return;
		}
		// 保存anchor在屏幕中的位置
		int[] location = new int[2];
		// 读取位置anchor座标
		anchor.getLocationOnScreen(location);
		Log.d(TAG, "anchorCenter[0]=" + location[0] + "anchorCenter[1]="
				+ location[1] + "popupWindow=" + popupWindow.getHeight());
		popupWindow.showAtLocation(anchor, Gravity.NO_GRAVITY, location[0]
				+ xoff, location[1] - popupWindow.getHeight() + yoff);
	}

	public static void dismissDialog(PopupWindow window) {
		if (window == null) {
			return;
		}
		if (window.isShowing()) {
			window.dismiss();
		}
	}

	/**
	 * 在布局的某个位置显示
	 * 
	 * @param context
	 * @param view
	 *            布局
	 * @param width
	 *            宽
	 * @param height
	 *            高
	 * @param gravity显示位置
	 * @param x
	 *            x轴偏移量
	 * @param y
	 *            y轴偏移量
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public static PopupWindow showAtLocationWindow(Context context, View view,
			int width, int height, int gravity, int x, int y) {
		PopupWindow popupWindow = new PopupWindow(view, width, height);
		popupWindow.setBackgroundDrawable(new BitmapDrawable());
		popupWindow.showAtLocation(view, gravity, x, y);
		return popupWindow;
	}

	/**
	 * 在布局的某个位置显示
	 * 
	 * @param view
	 * @param gravity
	 * @param x
	 * @param y
	 */
	public void showAtLocationWindow(View view, int gravity, int x, int y) {
		mPopupWindow.showAtLocation(view, gravity, x, y);
	}

	public void dismissPopupwind() {
		if (mPopupWindow != null) {
			mPopupWindow.dismiss();
		}
	}
}

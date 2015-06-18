package com.library.IUtils.Dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;

import com.library.IUtils.Interface.EditDialogCallBack;

/**
 * 对话框工厂类
 * 
 * @author Aaroa
 * 
 */
public class IDialogFactory {

	/******************* -------START-------- 碇定和取消Dialog -------START-------- *************************/
	/**
	 * 消息提示对话框
	 * 
	 * @param context
	 *            上下文
	 * @param title
	 *            标题
	 * @param titleTextColor
	 *            标题字体颜色
	 * @param titleTextSize
	 *            标题字体大小
	 * @param titleGravity
	 *            标题字体位置
	 * @param msg
	 *            内容
	 * @param msgTextColor
	 *            内容字体颜色
	 * @param msgTextSize
	 *            内容字体大小
	 * @param msgGravity
	 *            内容字体位置
	 * @param confirmBtnStr
	 *            确定按钮
	 * @param confirmTextColor
	 *            确定按钮字体颜色
	 * @param confirmTextSize
	 *            确定按钮字体大小
	 * @param cancelBtnStr
	 *            取消按钮
	 * @param cancelTextColor
	 *            取消按钮字体颜色
	 * @param cancelTextSize
	 *            取消按钮字体大小
	 * @param backCancel
	 *            点击返回是否取消对话框[true不取消，false取消]
	 * @param confirmlistener
	 *            确定监听回调
	 * @param cancelListener
	 *            取消监听回调
	 * @param warning
	 *            是否只有确定按钮[true有，false没有]
	 * @return
	 */
	public static Dialog showMsgDialog(Context context, String title,
			int titleTextColor, int titleTextSize, int titleGravity,
			String msg, int msgTextColor, int msgTextSize, int msgGravity,
			String confirmBtnStr, int confirmTextColor, int confirmTextSize,
			String cancelBtnStr, int cancelTextColor, int cancelTextSize,
			boolean backCancel, View.OnClickListener confirmListener,
			View.OnClickListener cancelListener, boolean warning) {
		if (context == null) {
			return null;
		}
		if (context instanceof Activity) {
			Activity activity = (Activity) context;
			if (activity.isFinishing()) {
				return null;
			}
		}
		IMessageDialog dialog = new IMessageDialog(context);

		// 标题
		dialog.setTitleText(title);
		dialog.setTitleTextColor(titleTextColor);
		dialog.setTitleTextSize(titleTextSize);
		dialog.setTitleTextGravity(titleGravity);

		// 内容
		dialog.setMsgText(msg);
		dialog.setMsgTextColor(msgTextColor);
		dialog.setMsgTextSize(msgTextSize);
		dialog.setMsgTextGravity(msgGravity);

		// 取消按钮
		dialog.setCancelBtnText(cancelBtnStr);
		dialog.setCancelBtnTextSize(cancelTextSize);
		dialog.setCancelBtnTextColor(cancelTextColor);

		// 确定按钮
		dialog.setConfirmBtnText(confirmBtnStr);
		dialog.setConfirmBtnTextSize(confirmTextSize);
		dialog.setConfirmBtnTextColor(confirmTextColor);

		// 返回监听
		dialog.setBackPressCancel(backCancel);

		// 监听监听回调
		dialog.setCancelBtnOnClickListener(cancelListener);
		dialog.setConfirmBtnOnClickListener(confirmListener);

		// 如果标题为空默认标题栏隐藏
		if ("".equals(title) || null == title) {
			dialog.setTitleTextVisibility(View.GONE);
		}

		// 只有确定对话框
		if (warning) {
			dialog.setCancelBtnTextVisibility(View.GONE);
		}
		dialog.show();

		return dialog;
	}

	/**
	 * 消息提示对话框
	 * 
	 * @param context
	 *            上下文
	 * @param titleRes
	 *            标题
	 * @param titleTextColor
	 *            标题字体颜色
	 * @param titleTextSize
	 *            标题字体大小
	 * @param titleGravity
	 *            标题字体位置
	 * @param msgRes
	 *            内容
	 * @param msgTextColor
	 *            内容字体颜色
	 * @param msgTextSize
	 *            内容字体大小
	 * @param msgGravity
	 *            内容字体位置
	 * @param confirmBtnStrRes
	 *            确定按钮
	 * @param confirmTextColor
	 *            确定按钮字体颜色
	 * @param confirmTextSize
	 *            确定按钮字体大小
	 * @param cancelBtnStrRes
	 *            取消按钮
	 * @param cancelTextColor
	 *            取消按钮字体颜色
	 * @param cancelTextSize
	 *            取消按钮字体大小
	 * @param backCancel
	 *            点击返回是否取消对话框[true不取消，false取消]
	 * @param confirmlistener
	 *            确定监听回调
	 * @param cancelListener
	 *            取消监听回调
	 * @return
	 */
	public static Dialog showMsgDialog(Context context, int titleRes,
			int titleTextColor, int titleTextSize, int titleGravity,
			int msgRes, int msgTextColor, int msgTextSize, int msgGravity,
			int confirmBtnStrRes, int confirmTextColor, int confirmTextSize,
			int cancelBtnStrRes, int cancelTextColor, int cancelTextSize,
			boolean backCancel, View.OnClickListener confirmlistener,
			View.OnClickListener cancelListener, boolean warning) {

		if (context == null) {
			return null;
		}
		if (context instanceof Activity) {
			Activity activity = (Activity) context;
			if (activity.isFinishing()) {
				return null;
			}
		}

		IMessageDialog dialog = new IMessageDialog(context);

		// 标题
		dialog.setTitleText(titleRes);
		dialog.setTitleTextColor(titleTextColor);
		dialog.setTitleTextSize(titleTextSize);
		dialog.setTitleTextGravity(titleGravity);

		// 内容
		dialog.setMsgText(msgRes);
		dialog.setMsgTextColor(msgTextColor);
		dialog.setMsgTextSize(msgTextSize);
		dialog.setMsgTextGravity(msgGravity);

		// 取消
		dialog.setCancelBtnText(cancelBtnStrRes);
		dialog.setCancelBtnTextSize(cancelTextSize);
		dialog.setCancelBtnTextColor(cancelTextColor);

		// 确定
		dialog.setConfirmBtnText(confirmBtnStrRes);
		dialog.setConfirmBtnTextSize(confirmTextSize);
		dialog.setConfirmBtnTextColor(confirmTextColor);

		// 返回监听
		dialog.setBackPressCancel(backCancel);

		// 按钮监听回调
		dialog.setCancelBtnOnClickListener(cancelListener);
		dialog.setConfirmBtnOnClickListener(confirmlistener);

		// 如果标题为空默认标题栏隐藏
		if (titleRes <= 0) {
			dialog.setTitleTextVisibility(View.GONE);
		}

		// 只有确定对话框
		if (warning) {
			dialog.setCancelBtnTextVisibility(View.GONE);
		}

		dialog.show();

		return dialog;
	}

	/**
	 * 消息提示对话框
	 * 
	 * @param context
	 *            上下文
	 * @param title
	 *            标题
	 * @param msg
	 *            内容
	 * @param confirmListener
	 *            确定监听回调
	 * @return
	 */
	public static Dialog showMsgDialog(Context context, String title,
			String msg, View.OnClickListener confirmListener) {
		Dialog dialog = showMsgDialog(context, title, 0, 0, 0, msg, 0, 0, 0,
				null, 0, 0, null, 0, 0, false, confirmListener, null, false);
		return dialog;
	}

	/**
	 * 消息提示对话框
	 * 
	 * @param context
	 *            上下文
	 * @param titleRes
	 *            标题
	 * @param msgRes
	 *            内容
	 * @param confirmListener
	 *            确定监听回调
	 * @return
	 */
	public static Dialog showMsgDialog(Context context, int titleRes,
			int msgRes, View.OnClickListener confirmListener) {
		Dialog dialog = showMsgDialog(context, titleRes, 0, 0, 0, msgRes, 0, 0,
				0, 0, 0, 0, 0, 0, 0, false, confirmListener, null, false);
		return dialog;
	}

	/**
	 * 消息提示对话框
	 * 
	 * @param context
	 *            上下文
	 * @param title
	 *            标题
	 * @param msg
	 *            内容
	 * @param confirmListener
	 *            确定监听回调
	 * @param cancelListener
	 *            取消监听回调
	 * @return
	 */
	public static Dialog showMsgDialog(Context context, String title,
			String msg, View.OnClickListener confirmListener,
			View.OnClickListener cancelListener) {
		Dialog dialog = showMsgDialog(context, title, 0, 0, 0, msg, 0, 0, 0,
				null, 0, 0, null, 0, 0, false, confirmListener, cancelListener,
				false);
		return dialog;
	}

	/**
	 * 
	 * @param context
	 *            上下文
	 * @param titleRes
	 *            标题
	 * @param msgRes
	 *            内容
	 * @param confirmListener
	 *            确定监听回调
	 * @param cancelListener
	 *            取消监听回调
	 * @return
	 */
	public static Dialog showMsgDialog(Context context, int titleRes,
			int msgRes, View.OnClickListener confirmListener,
			View.OnClickListener cancelListener) {
		Dialog dialog = showMsgDialog(context, titleRes, 0, 0, 0, msgRes, 0, 0,
				0, 0, 0, 0, 0, 0, 0, false, confirmListener, cancelListener,
				false);
		return dialog;
	}

	/**
	 * 
	 * @param context
	 *            上下文
	 * @param title
	 *            标题
	 * @param msg
	 *            内容
	 * @param confirmBtnStr
	 *            确定
	 * @param cancelBtnStr
	 *            取消
	 * @param confirmListener
	 *            确定监听回调
	 * @param cancelListener
	 *            取消监听回调
	 * @return
	 */
	public static Dialog showMsgDialog(Context context, String title,
			String msg, String confirmBtnStr, String cancelBtnStr,
			View.OnClickListener confirmListener,
			View.OnClickListener cancelListener) {
		Dialog dialog = showMsgDialog(context, title, 0, 0, 0, msg, 0, 0, 0,
				confirmBtnStr, 0, 0, cancelBtnStr, 0, 0, false,
				confirmListener, cancelListener, false);
		return dialog;
	}

	/**
	 * 
	 * @param context
	 *            上下文
	 * @param titleRes
	 *            标题
	 * @param msgRes
	 *            内容
	 * @param confirmBtnStrRes
	 *            确定
	 * @param cancelBtnStrRes
	 *            取消
	 * @param confirmListener
	 *            确定监听回调
	 * @param cancelListener
	 *            取消监听回调
	 * @return
	 */
	public static Dialog showMsgDialog(Context context, int titleRes,
			int msgRes, int confirmBtnStrRes, int cancelBtnStrRes,
			View.OnClickListener confirmListener,
			View.OnClickListener cancelListener) {
		Dialog dialog = showMsgDialog(context, titleRes, 0, 0, 0, msgRes, 0, 0,
				0, confirmBtnStrRes, 0, 0, cancelBtnStrRes, 0, 0, false,
				confirmListener, cancelListener, false);
		return dialog;
	}

	/**
	 * 
	 * @Title: showMsgDialog
	 * @Description: 自定布局Dialog
	 * @param @param context
	 * @param @param titleRes
	 * @param @param view
	 * @param @param confirStr
	 * @param @param cancelStr
	 * @param @param cancleLister
	 * @param @param confirmLister
	 * @param @return
	 * @return Dialog
	 * @throws
	 */
	public static Dialog showMsgDialog(Context context, String titleRes,
			final View view, String confirStr, String cancelStr,
			View.OnClickListener cancleLister,
			View.OnClickListener confirmLister) {
		IMessageBaseDialog dialog = new IMessageBaseDialog(context) {

			@Override
			public View createContentView() {
				return view;
			}
		};
		dialog.setTitleText(titleRes);
		dialog.setCancelBtnText(cancelStr);
		dialog.setConfirmBtnText(confirStr);
		dialog.setConfirmBtnOnClickListener(confirmLister);
		dialog.setCancelBtnOnClickListener(cancleLister);
		return dialog;
	}

	/******************* -------END-------- 碇定和取消Dialog -------END-------- *************************/

	/******************* -------START-------- 只有碇定Dialog -------START-------- *************************/
	/**
	 * 
	 * @param context
	 *            上下文
	 * @param title
	 *            标题
	 * @param msg
	 *            内容
	 * @param confirmListener
	 *            确定监听回调
	 * @return
	 */
	public static Dialog warningDialog(Context context, String title,
			String msg, View.OnClickListener confirmListener) {
		Dialog dialog = showMsgDialog(context, title, 0, 0, 0, msg, 0, 0, 0,
				null, 0, 0, null, 0, 0, false, confirmListener, null, true);
		return dialog;
	}

	/**
	 * 
	 * @param context
	 *            上下文
	 * @param titleRes
	 *            标题
	 * @param msgRes
	 *            内容
	 * @param confirmListener
	 *            确定监听回调
	 * @return
	 */
	public static Dialog warningDialog(Context context, int titleRes,
			int msgRes, View.OnClickListener confirmListener) {
		Dialog dialog = showMsgDialog(context, titleRes, 0, 0, 0, msgRes, 0, 0,
				0, 0, 0, 0, 0, 0, 0, false, confirmListener, null, true);
		return dialog;
	}

	/**
	 * 
	 * @param context
	 *            上下文
	 * @param title
	 *            标题
	 * @param msg
	 *            内容
	 * @param confirmListener
	 *            确定监听回调
	 * @param backCancel
	 *            点击返回是否取消对话框[true不取消，false取消]
	 * @return
	 */
	public static Dialog warningDialog(Context context, String title,
			String msg, View.OnClickListener confirmListener, boolean backCancel) {
		Dialog dialog = showMsgDialog(context, title, 0, 0, 0, msg, 0, 0, 0,
				null, 0, 0, null, 0, 0, backCancel, confirmListener, null, true);
		return dialog;
	}

	/**
	 * 
	 * @param context
	 *            上下文
	 * @param titleRes
	 *            标题
	 * @param msgRes
	 *            内容
	 * @param confirmListener
	 *            确定监听回调
	 * @param backCancel
	 *            点击返回是否取消对话框[true不取消，false取消]
	 * @return
	 */
	public static Dialog warningDialog(Context context, int titleRes,
			int msgRes, View.OnClickListener confirmListener, boolean backCancel) {
		Dialog dialog = showMsgDialog(context, titleRes, 0, 0, 0, msgRes, 0, 0,
				0, 0, 0, 0, 0, 0, 0, backCancel, confirmListener, null, true);
		return dialog;
	}

	/******************* -------END-------- 只有碇定Dialog -------END-------- *************************/

	/******************* -------START-------- 加载Dialog -------START-------- *************************/
	/**
	 * 加载对话框
	 * 
	 * @param context
	 * @param msg
	 * @param backPressCancel
	 * @return
	 */
	public static Dialog createLoadingDialog(Context context, String msg,
			boolean backPressCancel) {
		ILoadingBaseDialog dialog = new ILoadingBaseDialog(context);
		dialog.setBackPressCancel(backPressCancel);
		dialog.setTipText(msg);
		return dialog;
	}

	/**
	 * 加载对话框
	 * 
	 * @param context
	 * @param msgRes
	 * @param backPressCancel
	 * @return
	 */
	public static Dialog createLoadingDialog(Context context, int msgRes,
			boolean backPressCancel) {
		ILoadingBaseDialog dialog = new ILoadingBaseDialog(context);
		dialog.setBackPressCancel(backPressCancel);
		dialog.setTipText(msgRes);
		return dialog;
	}

	/**
	 * 加载对话框
	 * 
	 * @param context
	 * @param msg
	 * @return
	 */
	public static Dialog createLoadingDialog(Context context, String msg) {
		return createLoadingDialog(context, msg, false);
	}

	/**
	 * 加载对话框
	 * 
	 * @param context
	 * @param msgRes
	 * @return
	 */
	public static Dialog createLoadingDialog(Context context, int msgRes) {
		return createLoadingDialog(context, msgRes, false);
	}

	/**
	 * 
	 * @param context
	 * @return
	 */
	public static Dialog createLoadingDialog(Context context) {
		return createLoadingDialog(context, "");
	}

	/******************* -------END-------- 加载Dialog -------EDI-------- *************************/

	/**
	 * 显示
	 * 
	 * @param dialog
	 */
	public static void showDialog(Dialog dialog) {
		if (dialog == null) {
			return;
		}
		if (!dialog.isShowing()) {
			dialog.show();
		}
	}

	/**
	 * 取消
	 * 
	 * @param dialog
	 */
	public static void dimissDialog(Dialog dialog) {
		if (dialog == null) {
			return;
		}
		if (dialog.isShowing()) {
			dialog.dismiss();
		}
	}

	/**
	 * 创建输入框对话框
	 * 
	 * @param context
	 * @param title
	 * @param callBack
	 */
	public static void createEditTextDialog(Context context, String title,
			final EditDialogCallBack callBack) {
		final IEditTextDialog dialog = new IEditTextDialog(context);
		dialog.setTitleText(title);
		dialog.setConfirmBtnOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				callBack.result(dialog.getEditText());
				dimissDialog(dialog);
			}
		});
		dialog.show();
	}

	public static Dialog createShowViewDialog(Context context, final View view,
			String title, View.OnClickListener listener) {
		IMessageBaseDialog dialog = new IMessageBaseDialog(context) {

			@Override
			public View createContentView() {
				return view;
			}
		};
		// dialog.setContentView(view);
		dialog.setTitleText(title);
		dialog.setConfirmBtnOnClickListener(listener);
		return dialog;
	}

	public static Dialog createShowDialogCancel(Context context,
			final View view, String title) {
		final IMessageBaseDialog dialog = new IMessageBaseDialog(context) {

			@Override
			public View createContentView() {
				return view;
			}
		};
		// dialog.setContentView(view);
		dialog.setTitleText(title);
		dialog.setConfirBtnTextVisibility(View.GONE);
		dialog.setConfirmBtnOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				dialog.dismiss();

			}
		});
		return dialog;
	}

}

package com.jiedaoche.tigeroil.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

/**
 * Activity跳转工具类
 * 
 * @author hzwua
 * 
 */
@SuppressLint("NewApi")
public class ISkipActivityUtil {

	public static final String SKIP_FLAG = "skip_flag";

	/**
	 * 不带参数跳转
	 * 
	 * @param context
	 * @param cls
	 */
	public static void startIntent(Context context, Class<?> cls) {
		context.startActivity(new Intent(context, cls));
	}

	/**
	 * 带标记跳转
	 * 
	 * @param context
	 * @param cls
	 * @param tag
	 */
	public static void startIntent(Context context, Class<?> cls, String tag) {
		Intent intent = new Intent();
		intent.putExtra(SKIP_FLAG, tag);
		context.startActivity(intent);
	}

	/**
	 * 带Bundle跳转
	 * 
	 * @param context
	 * @param cls
	 * @param extras
	 */
	public static void startIntent(Context context, Class<?> cls, Bundle extras) {
		Intent intent = new Intent();
		intent.putExtras(extras);
		intent.setClass(context, cls);
		context.startActivity(intent);
	}

	/**
	 * 回调跳转
	 * 
	 * @param activity
	 * @param context
	 * @param cls
	 * @param requestCode
	 */
	public static void startIntentForResult(Activity activity, Context context,
			Class<?> cls, int requestCode) {
		Intent intent = new Intent();
		intent.setClass(context, cls);
		activity.startActivityForResult(intent, requestCode);
	}

	/**
	 * 回调跳转
	 * 
	 * @param activity
	 * @param context
	 * @param cls
	 * @param extras
	 * @param requestCode
	 */
	public static void startIntentForResult(Activity activity, Context context,
			Class<?> cls, Bundle extras, int requestCode) {
		Intent intent = new Intent();
		intent.putExtras(extras);
		intent.setClass(context, cls);
		activity.startActivityForResult(intent, requestCode);
	}

}

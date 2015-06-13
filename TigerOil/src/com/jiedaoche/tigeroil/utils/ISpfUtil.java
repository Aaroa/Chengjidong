package com.jiedaoche.tigeroil.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

/**
 * SharedPreference 工具类
 * 
 * @author hezhiWu on 14-10-10
 * 
 */
public class ISpfUtil {
	private static final String NAME = "hert";

	/**
	 * 设置SharePreference文件中的字段的值
	 * 
	 * @param ctx
	 *            上下文
	 * @param spName
	 *            share文件名字
	 * @param key
	 *            字段
	 * @param value
	 *            值
	 */
	@SuppressWarnings("deprecation")
	@SuppressLint("WorldWriteableFiles")
	public static boolean setValue(Context ctx, String key, Object value) {
		boolean status = false;
		SharedPreferences spf = null;
		spf = ctx.getSharedPreferences(NAME, Context.MODE_WORLD_WRITEABLE);
		String type = value.getClass().getSimpleName();// 获取数据类型
		SharedPreferences.Editor editor = spf.edit();
		if (spf != null) {
			if ("String".equals(type)) {
				editor.putString(key, (String) value);
			} else if ("Integer".equals(type)) {
				editor.putInt(key, (Integer) value);
			} else if ("Boolean".equals(type)) {
				editor.putBoolean(key, (Boolean) value);
			} else if ("Long".equals(type)) {
				editor.putLong(key, (Long) value);
			} else if ("Float".equals(type)) {
				editor.putFloat(key, (Float) value);
			}
			status = editor.commit();
		}
		return status;
	}

	/**
	 * 获得SharePreference的值
	 * 
	 * @param ctx
	 *            上下文
	 * @param spName
	 *            share文件名字
	 * @param key
	 *            字段
	 * @param defValue
	 *            默认值
	 * @return 获得对应key的值
	 */
	@SuppressWarnings("deprecation")
	@SuppressLint("WorldWriteableFiles")
	public static Object getValue(Context ctx, String key, Object defValue) {
		SharedPreferences spf = null;
		spf = ctx.getSharedPreferences(NAME, Context.MODE_WORLD_WRITEABLE);
		String type = defValue.getClass().getSimpleName();// 获取数据类型
		if (spf != null) {
			if (type.equals("String")) {
				return spf.getString(key, (String) defValue);
			} else if (type.equals("Integer")) {
				return spf.getInt(key, (Integer) defValue);
			} else if (type.equals("Boolean")) {
				return spf.getBoolean(key, (Boolean) defValue);
			} else if (type.equals("Long")) {
				return spf.getLong(key, (Long) defValue);
			} else if (type.equals("Float")) {
				return spf.getFloat(key, (Float) defValue);
			}
		}
		return null;
	}

	/**
	 * 删除某一个字段
	 * 
	 * @param context
	 *            上下文
	 * @param fileName
	 *            文件名
	 * @param key
	 *            字段
	 */
	public static void remove(Context context, String fileName, String key) {
		SharedPreferences preference = context.getSharedPreferences(fileName,
				Context.MODE_PRIVATE);
		Editor editor = preference.edit();
		editor.remove(key);
		editor.commit();
	}

	/**
	 * 删除某个文件
	 * 
	 * @param cxt
	 *            上下文
	 * @param fileName
	 *            文件名
	 */
	public static void clean(Context cxt, String fileName) {
		SharedPreferences preference = cxt.getSharedPreferences(fileName,
				Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = preference.edit();
		editor.clear();
		editor.commit();
	}
}

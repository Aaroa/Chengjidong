package com.jiedaoche.tigeroil.ui;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

import com.baidu.mapapi.SDKInitializer;

/**
 * 
 * @ClassName: TOApplication
 * @Description: 全局类，程序启动时调用
 * @author Aaron
 * @date 2015-5-10 上午1:47:16
 * 
 */
public class TOApplication extends Application {

	private static TOApplication instance;

	private List<Activity> activities = new ArrayList<Activity>();

	@Override
	public void onCreate() {
		super.onCreate();
		// 初始化百度地图
		SDKInitializer.initialize(getApplicationContext());
	}

	public static TOApplication getInstance() {
		if (instance == null) {
			instance = new TOApplication();
		}
		return instance;
	}

	public TOApplication getApplicationContext() {
		return instance;
	}

	public void addActivity(Activity activity) {
		activities.add(activity);
	}

	public void exit() {
		for (Activity activity : activities) {
			activity.finish();
		}
	}
}

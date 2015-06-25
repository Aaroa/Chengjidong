package com.jiedaoche.tigeroil.ui.activitys.adapter;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

import com.jiedaoche.tigeroil.ui.activitys.CommonWebViewActivity;
import com.jiedaoche.tigeroil.utils.ISkipActivityUtil;

public class BaseViewPageAdapter extends PagerAdapter {

	/**
	 * 装ImageView数组
	 */
	private ImageView[] mImageViews;

	private Activity activity;

	public BaseViewPageAdapter(Activity activities, ImageView[] mImageViews) {
		this.mImageViews = mImageViews;
		this.activity = activities;
	}

	/**
	 * 如果想无限循环，此处要返回 Integer.MAX_VALUE
	 */
	@Override
	public int getCount() {
		return Integer.MAX_VALUE;
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		return arg0 == arg1;
	}

	@Override
	public void destroyItem(View container, int position, Object object) {
		// ((ViewPager) container).removeView(mImageViews[position %
		// mImageViews.length]);
	}

	/**
	 * 载入图片进去，用当前的position 除以 图片数组长度取余数是关键
	 */
	@Override
	public Object instantiateItem(View container, final int position) {
		try {
			((ViewPager) container).addView(mImageViews[position
					% mImageViews.length], 0);
		} catch (Exception e) {
			e.printStackTrace();
		}
		final int p = position % mImageViews.length;
		mImageViews[position % mImageViews.length]
				.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						switch (p) {
						case 0:
							Bundle bundle = new Bundle();
							bundle.putString("title", "中国新力加油站");
							bundle.putString("url", "http://07384230083.locoso.com/");
							ISkipActivityUtil.startIntent(activity,
									CommonWebViewActivity.class, bundle);
							break;
						case 1:
							Bundle bundle1 = new Bundle();
							bundle1.putString("title", "中国石化加油站");
							bundle1.putString("url", "http://www.ce.cn/cysc/yq/dt/201306/19/t20130619_21528836.shtml");
							ISkipActivityUtil.startIntent(activity,
									CommonWebViewActivity.class, bundle1);
							break;
						case 2:
							Bundle bundle2 = new Bundle();
							bundle2.putString("title", "深圳科技园加油站");
							bundle2.putString("url", "http://www.shilirhy.com/");
							ISkipActivityUtil.startIntent(activity,
									CommonWebViewActivity.class, bundle2);
							break;

						default:
							break;
						}
					}
				});
		return mImageViews[position % mImageViews.length];
	}
}

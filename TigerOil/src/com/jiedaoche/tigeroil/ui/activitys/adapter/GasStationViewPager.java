package com.jiedaoche.tigeroil.ui.activitys.adapter;

import java.util.List;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

/**
 * 
 * @ClassName: GasStationViewPager
 * @Description: 加油站界面适配器
 * @author Aaron
 * @date 2015-5-16 下午4:42:48
 * 
 */
public class GasStationViewPager extends PagerAdapter {
	private List<View> mListViews;

	public GasStationViewPager(List<View> mListViews) {
		this.mListViews = mListViews;// 构造方法，参数是我们的页卡，这样比较方便。
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {

		return arg0 == arg1;
	}

	@Override
	public int getCount() {

		return mListViews.size();
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		((ViewPager) container).removeView(mListViews.get(position));

	}

	@Override
	public int getItemPosition(Object object) {

		return super.getItemPosition(object);
	}
	

	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		((ViewPager) container).addView(mListViews.get(position));
		return mListViews.get(position);
	}

}

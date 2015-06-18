package com.jiedaoche.tigeroil.ui.activitys.adapter;

import com.jiedaoche.tigeroil.ui.activitys.fragment.GasStationCommonFragment;
import com.jiedaoche.tigeroil.ui.activitys.fragment.GasStationNearFragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * @ClassName: GasStationFragmentAdapter
 * @Description: 加油站适配器
 * @author Aaron
 * @date 2015-6-14 上午9:40:00
 * 
 */
public class GasStationFragmentAdapter extends FragmentPagerAdapter {

	public GasStationFragmentAdapter(FragmentManager fm) {
		super(fm);
	}

	@Override
	public Fragment getItem(int arg0) {
		Fragment fragment = null;
		switch (arg0) {
		case 0:
			fragment = new GasStationCommonFragment();
			break;
		case 1:
			fragment = new GasStationNearFragment();
			break;

		default:
			break;
		}
		return fragment;
	}

	@Override
	public int getCount() {
		return 2;
	}

}

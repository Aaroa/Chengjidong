package com.jiedaoche.tigeroil.ui.activitys.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jiedaoche.tigeroil.ui.activitys.R;

public class GasStationNearFragment extends Fragment {

	public static final String SWITCH_ACTION = "com.jiedongcheng.switch";

	private FragmentTransaction transaction;
	private FragmentManager fragmentManager;

	private GasStationListFragment listFragment;
	private GasStationMapFragment mapFragment;

	private showBrocast brocast;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		brocast = new showBrocast();
		IntentFilter filter = new IntentFilter(SWITCH_ACTION);
		getActivity().registerReceiver(brocast, filter);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.gas_station_layout, null);
		showListView();
		return view;
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		getActivity().unregisterReceiver(brocast);
	}

	private void showListView() {
		fragmentManager = getActivity().getSupportFragmentManager();
		transaction = fragmentManager.beginTransaction();
		listFragment = new GasStationListFragment();
		transaction.replace(R.id.gas_station_frameLayout, listFragment);
		transaction.commitAllowingStateLoss();
	}

	private void showMapView() {
		fragmentManager = getActivity().getSupportFragmentManager();
		transaction = fragmentManager.beginTransaction();
		mapFragment = new GasStationMapFragment();
		transaction.replace(R.id.gas_station_frameLayout, mapFragment);
		transaction.commitAllowingStateLoss();
	}

	private class showBrocast extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			String tag = intent.getStringExtra("flag");
			if (tag == null || "".equals(tag)) {
				return;
			}
			if ("map".equals(tag)) {
				showMapView();
			} else if ("list".equals(tag)) {
				showListView();
			}
		}
	}
}

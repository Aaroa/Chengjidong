package com.jiedaoche.tigeroil.ui.activitys.fragment;

import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.jiedaoche.tigeroil.ui.activitys.R;
import com.jiedaoche.tigeroil.ui.activitys.adapter.GasStationNearbyAdapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class GasStationListFragment extends Fragment {

	private PullToRefreshListView mListView;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.gas_station_nearby_layout, null);
		mListView = (PullToRefreshListView) view
				.findViewById(R.id.gas_station_nearby_listview);
		mListView.setAdapter(new GasStationNearbyAdapter(getActivity()));
		return view;

	}
}

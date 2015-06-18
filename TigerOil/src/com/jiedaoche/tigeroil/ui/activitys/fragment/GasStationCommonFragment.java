package com.jiedaoche.tigeroil.ui.activitys.fragment;

import com.jiedaoche.tigeroil.ui.activitys.R;
import com.jiedaoche.tigeroil.ui.activitys.adapter.GasStationCommonAdapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class GasStationCommonFragment extends Fragment {

	private ListView mListView;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.gas_station_common_layout, null);
		mListView = (ListView) view
				.findViewById(R.id.gas_station_common_listview);
		mListView.setAdapter(new GasStationCommonAdapter(getActivity()));
		return view;
	}
}

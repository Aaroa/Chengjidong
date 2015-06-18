package com.jiedaoche.tigeroil.ui.activitys.fragment;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ZoomControls;

import com.baidu.location.LocationClient;
import com.baidu.location.ad;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.jiedaoche.tigeroil.ui.activitys.R;
import com.jiedaoche.tigeroil.ui.activitys.adapter.GasStationViewPager;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

public class GasStationMapFragment extends Fragment {

	@ViewInject(R.id.station_bmapView)
	private MapView mMapView;

	@ViewInject(R.id.station_viewPager)
	private ViewPager mViewPager;

	private BaiduMap mBaiduMap;
	private LocationClient mLocationClient;

	private List<View> mList = new ArrayList<View>();

	private double[] lng = new double[] { 23.133318, 23.127302, 22.538014,
			22.603942 };
	private double[] lat = new double[] { 113.36865, 113.376629, 113.955008,
			114.054903 };

	private int[] focusIcon = { R.drawable.icon_focus_marka,
			R.drawable.icon_focus_markb, R.drawable.icon_focus_markc,
			R.drawable.icon_focus_markd, R.drawable.icon_focus_marke,
			R.drawable.icon_focus_markf, R.drawable.icon_focus_markg,
			R.drawable.icon_focus_markh, R.drawable.icon_focus_marki,
			R.drawable.icon_focus_markj };

	private int[] markIcon = { R.drawable.icon_marka, R.drawable.icon_markb,
			R.drawable.icon_markc, R.drawable.icon_markd,
			R.drawable.icon_marke, R.drawable.icon_markf,
			R.drawable.icon_markg, R.drawable.icon_markh,
			R.drawable.icon_marki, R.drawable.icon_markj };

	private GasStationViewPager adapter;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.gas_station_map_layout, null);
		ViewUtils.inject(this, view);
		initMap();
		initView();
		return view;
	}

	@Override
	public void onResume() {
		super.onResume();
		mMapView.onResume();
	}

	@Override
	public void onPause() {
		super.onPause();
		mMapView.onPause();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		mMapView.onDestroy();
		mMapView = null;
	}

	private void initMap() {
		mBaiduMap = mMapView.getMap();
		// 隐藏自带绽放控件、百度Logo
		int count = mMapView.getChildCount();
		for (int i = 0; i < count; i++) {
			View view = mMapView.getChildAt(i);
			if (view instanceof ZoomControls) {
				view.setVisibility(View.GONE);
			}
			if (view instanceof ImageView) {
				view.setVisibility(View.GONE);
			}
		}
		// 标记
		for (int i = 0; i < lat.length; i++) {
			LatLng latLng = new LatLng(lng[i], lat[i]);
			BitmapDescriptor descriptor = BitmapDescriptorFactory
					.fromResource(markIcon[i]);
			OverlayOptions options = new MarkerOptions().position(latLng).icon(
					descriptor);
			mBaiduMap.addOverlay(options);
		}

		LatLng ll = new LatLng(lng[3], lat[3]);
		// 设置中心点
		MapStatus status = new MapStatus.Builder().target(ll).zoom(14).build();
		MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory
				.newMapStatus(status);
		// 改变地图状态
		mBaiduMap.setMapStatus(mMapStatusUpdate);
	}

	private void initView() {
		for (int i = 0; i < lat.length; i++) {
			mList.add(createView());
		}
		adapter = new GasStationViewPager(mList);
		mViewPager.setAdapter(adapter);
	}

	private View createView() {
		View view = LayoutInflater.from(getActivity()).inflate(
				R.layout.gas_station_item_layout, null);
		return view;
	}
}

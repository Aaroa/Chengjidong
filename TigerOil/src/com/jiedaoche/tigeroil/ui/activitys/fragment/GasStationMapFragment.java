package com.jiedaoche.tigeroil.ui.activitys.fragment;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ZoomControls;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.navisdk.BNaviPoint;
import com.baidu.navisdk.BaiduNaviManager;
import com.baidu.navisdk.BaiduNaviManager.OnStartNavigationListener;
import com.baidu.navisdk.comapi.routeplan.RoutePlanParams.NE_RoutePlan_Mode;
import com.jiedaoche.tigeroil.ui.activitys.BNavigatorActivity;
import com.jiedaoche.tigeroil.ui.activitys.GasStationDetailsActivity;
import com.jiedaoche.tigeroil.ui.activitys.R;
import com.jiedaoche.tigeroil.ui.activitys.adapter.GasStationViewPagerAdapter;
import com.jiedaoche.tigeroil.utils.ISkipActivityUtil;
import com.jiedaoche.tigeroil.view.GasStationViewPager;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

/**
 * 
 * @ClassName: GasStationMapFragment
 * @Description: 加油站地图模式
 * @author Aaron
 * @date 2015-6-19 上午11:09:09
 * 
 */
public class GasStationMapFragment extends Fragment implements OnClickListener {

	@ViewInject(R.id.station_bmapView)
	private MapView mMapView;

	@ViewInject(R.id.station_viewPager)
	private GasStationViewPager mViewPager;

	private BaiduMap mBaiduMap;
	private LocationClient mLocationClient;

	private MyLocationListenner mMyLocationData;

	private double currentLat;
	private double currentLng;

	private List<View> mList = new ArrayList<View>();

	private double[] lng = new double[] { 23.133318, 23.127302, 22.538014,
			22.603942 };
	private double[] lat = new double[] { 113.36865, 113.376629, 113.955008,
			114.054903 };

	private String[] stationName = new String[] { "中国石油新力加油站", "中国石化加油站",
			"深圳科技园加油站", "普滨加油站" };

	private String[] address = new String[] { "广州市天河区天府路冠盛酒店旁，天河公园正门附近",
			"广州市天河区黄埔大道241号", "深圳市南山区白石路3699号", "深圳市龙华新区民乐立交附近" };

	private String[] state = new String[] { "空闲", "一般", "爆满", "空闲" };

	private String[] distance = new String[] { "2.1", "8.0", "6.0", "4.3" };

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

	private GasStationViewPagerAdapter adapter;

	boolean isFirstLoc = false;// 是否首次定位
	private int currentLevel = 14;// 设置地图当前绽放级别

	private String currentAddress;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.gas_station_map_layout, null);
		ViewUtils.inject(this, view);
		initMap();
		initLocation();
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
			BitmapDescriptor descriptor = null;
			if (i == 0) {
				descriptor = BitmapDescriptorFactory.fromResource(focusIcon[i]);
			} else
				descriptor = BitmapDescriptorFactory.fromResource(markIcon[i]);
			BitmapDescriptorFactory.fromResource(focusIcon[i]);
			OverlayOptions options = new MarkerOptions().position(latLng).icon(
					descriptor);
			mBaiduMap.addOverlay(options);
		}

		LatLng ll = new LatLng(lng[0], lat[0]);
		// 设置中心点
		MapStatus status = new MapStatus.Builder().target(ll).zoom(14).build();
		MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory
				.newMapStatus(status);
		// 改变地图状态
		mBaiduMap.setMapStatus(mMapStatusUpdate);
	}

	/**
	 * @Title: initLocation
	 * @Description: 初始化定位
	 * @param
	 * @return void
	 */
	private void initLocation() {
		// 开启定位层
		mBaiduMap.setMyLocationEnabled(true);

		mLocationClient = new LocationClient(getActivity());
		mMyLocationData = new MyLocationListenner();
		mLocationClient.registerLocationListener(mMyLocationData);

		LocationClientOption option = new LocationClientOption();
		option.setOpenGps(true);// 打开gps
		option.setCoorType("bd09ll"); // 设置坐标类型
		option.setScanSpan(1000);

		mLocationClient.setLocOption(option);
		mLocationClient.start();

	}

	private void initView() {
		for (int i = 0; i < lat.length; i++) {
			mList.add(createView((i + 1) + "、" + stationName[i], address[i],
					distance[i], state[i], lng[i], lat[i]));
		}
		adapter = new GasStationViewPagerAdapter(mList);
		mViewPager.setAdapter(adapter);
		mViewPager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int arg0) {
				LatLng ll = new LatLng(lng[arg0], lat[arg0]);
				mBaiduMap.clear();
				for (int i = 0; i < lat.length; i++) {
					if (arg0 == i) {
						BitmapDescriptor descriptor = BitmapDescriptorFactory
								.fromResource(focusIcon[arg0]);
						OverlayOptions options = new MarkerOptions().position(
								ll).icon(descriptor);
						mBaiduMap.addOverlay(options);

						// 设置中心点
						MapStatus status = new MapStatus.Builder().target(ll)
								.zoom(14).build();
						MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory
								.newMapStatus(status);
						// 改变地图状态
						mBaiduMap.setMapStatus(mMapStatusUpdate);
					} else {
						LatLng latLng = new LatLng(lng[i], lat[i]);
						BitmapDescriptor descriptor = BitmapDescriptorFactory
								.fromResource(markIcon[i]);
						OverlayOptions options = new MarkerOptions().position(
								latLng).icon(descriptor);
						mBaiduMap.addOverlay(options);
					}
				}
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
			}
		});
	}

	private View createView(final String name, final String address,
			final String distance, final String state, final double lng,
			final double lat) {
		View view = LayoutInflater.from(getActivity()).inflate(
				R.layout.gas_station_item_layout, null);
		view.findViewById(R.id.gas_details).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						Bundle bundle = new Bundle();
						bundle.putString("name", name);
						bundle.putString("distance", distance);
						bundle.putString("address", address);
						bundle.putDouble("lng", lng);
						bundle.putDouble("lat", lat);
						bundle.putString("state", state);
						ISkipActivityUtil.startIntent(getActivity(),
								GasStationDetailsActivity.class, bundle);
					}
				});
		view.findViewById(R.id.map_gas_nav).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						// 114.030084 ,22.623357
						// 起点
						BNaviPoint startPotion = new BNaviPoint(currentLng,
								currentLat, currentAddress,
								BNaviPoint.CoordinateType.GCJ02);

						// 113.376629 ,23.127302
						// 终点
						BNaviPoint entPotion = new BNaviPoint(lat, lng,
								address, BNaviPoint.CoordinateType.GCJ02);

						launchNavigatorViaPoints(startPotion, entPotion);
					}
				});
		TextView nameTV = (TextView) view.findViewById(R.id.gas_name);
		TextView addressTV = (TextView) view.findViewById(R.id.gas_address);
		TextView stateTV = (TextView) view.findViewById(R.id.gas_state);

		nameTV.setText(name + "     " + distance+"km");
		addressTV.setText(address);
		stateTV.setText(state);
		return view;
	}

	/**
	 * 
	 * @Title: launchNavigatorViaPoints
	 * @Description: 开户导航
	 * @param @param mStartPoint
	 * @param @param mEndPoint
	 * @return void
	 * @throws
	 */
	private void launchNavigatorViaPoints(BNaviPoint mStartPoint,
			BNaviPoint mEndPoint) {
		List<BNaviPoint> points = new ArrayList<BNaviPoint>();
		points.add(mStartPoint);
		points.add(mEndPoint);
		BaiduNaviManager.getInstance().launchNavigator(getActivity(), points, // 路线点列表
				NE_RoutePlan_Mode.ROUTE_PLAN_MOD_MIN_TIME, // 算路方式
				true, // 真实导航
				BaiduNaviManager.STRATEGY_FORCE_ONLINE_PRIORITY, // 在离线策略
				new OnStartNavigationListener() { // 跳转监听

					@Override
					public void onJumpToNavigator(Bundle configParams) {
						Intent intent = new Intent(getActivity(),
								BNavigatorActivity.class);
						intent.putExtras(configParams);
						startActivity(intent);
					}

					@Override
					public void onJumpToDownloader() {
					}
				});
	}

	@OnClick({ R.id.station_map_myLocation, R.id.station_map_min_ib,
			R.id.station_map_out_ib })
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.station_map_myLocation:
			isFirstLoc = true;
			mLocationClient.requestLocation();
			break;
		case R.id.station_map_min_ib:
			++currentLevel;
			if (currentLevel > mBaiduMap.getMaxZoomLevel()) {
				Toast.makeText(getActivity(), "已放大到最大", Toast.LENGTH_LONG)
						.show();
				currentLevel--;
				return;
			}
			mBaiduMap.setMapStatus(MapStatusUpdateFactory.zoomTo(currentLevel));
			break;
		case R.id.station_map_out_ib:
			--currentLevel;
			if (currentLevel < mBaiduMap.getMinZoomLevel()) {
				Toast.makeText(getActivity(), "已缩小到最小", Toast.LENGTH_LONG)
						.show();
				currentLevel++;
				return;
			}
			mBaiduMap.setMapStatus(MapStatusUpdateFactory.zoomTo(currentLevel));
			break;

		default:
			break;
		}
	}

	/**
	 * 
	 * @ClassName: MyLocationListenner
	 * @Description: 定位监听器
	 * @author Aaron
	 * @date 2015年5月21日 上午10:03:53
	 * 
	 */
	private class MyLocationListenner implements BDLocationListener {

		@Override
		public void onReceiveLocation(BDLocation location) {
			if (location == null || mMapView == null)
				return;
			currentLat = location.getLatitude();
			currentLng = location.getLongitude();

			currentAddress = location.getAddrStr();

			MyLocationData locData = new MyLocationData.Builder()
					.accuracy(location.getRadius())
					// 此处设置开发者获取到的方向信息，顺时针0-360
					.direction(100).latitude(location.getLatitude())
					.longitude(location.getLongitude()).build();
			mBaiduMap.setMyLocationData(locData);
			if (isFirstLoc) {
				isFirstLoc = false;
				LatLng ll = new LatLng(location.getLatitude(),
						location.getLongitude());
				MapStatusUpdate u = MapStatusUpdateFactory.newLatLng(ll);
				mBaiduMap.animateMapStatus(u);
			}
		}
	}
}

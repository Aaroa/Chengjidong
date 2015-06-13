package com.jiedaoche.tigeroil.ui.activitys;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import android.widget.ZoomControls;

import com.baidu.lbsapi.auth.LBSAuthManagerListener;
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
import com.baidu.navisdk.BNaviEngineManager.NaviEngineInitListener;
import com.baidu.navisdk.BNaviPoint;
import com.baidu.navisdk.BaiduNaviManager;
import com.baidu.navisdk.BaiduNaviManager.OnStartNavigationListener;
import com.baidu.navisdk.comapi.routeplan.RoutePlanParams.NE_RoutePlan_Mode;
import com.jiedaoche.tigeroil.ui.BaseActivity;
import com.jiedaoche.tigeroil.ui.TOApplication;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

/**
 * 
 * @ClassName: GasStationMapActivity
 * @Description: 加油站在地图展示
 * @author Aaron
 * @date 2015年5月21日 上午10:04:15
 * 
 */
@ContentView(R.layout.activity_gas_station_map)
public class GasStationMapActivity extends BaseActivity {

	@ViewInject(R.id.bmapView)
	private MapView mMapView;

	@ViewInject(R.id.oil_price_list)
	private LinearLayout mOilPriceListLayout;

	@ViewInject(R.id.map_oil_name)
	private TextView mOilName;

	@ViewInject(R.id.map_oil_address)
	private TextView mOilAddress;

	@ViewInject(R.id.map_myLocation)
	private ImageButton mMyLocationBtn;
	
	@ViewInject(R.id.map_oil_state)
	private TextView state;

	private BaiduMap mBaiduMap;
	private LocationClient mLocationClient;
	private MyLocationListenner mMyLocationData;

	private Bundle mBundle;

	private int currentLevel = 14;// 设置地图当前绽放级别

	boolean isFirstLoc = false;// 是否首次定位

	private double currentLat;
	private double currentLng;

	private String currentAddress;

	private String oilNumber[] = new String[] { "92#汽油", "95#汽油", "0#柴油" };
	private String oilPrice[] = new String[] { "6.32", "6.98", "6.12" };

	protected boolean mIsEngineInitSuccess;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		ViewUtils.inject(this);
		TOApplication.getInstance().addActivity(this);
		mBundle = getIntent().getExtras();
		setHeadTitle(mBundle.getString("title"));
		mOilName.setText(mBundle.getString("title"));
		mOilAddress.setText(mBundle.getString("address"));
		state.setText(mBundle.getString("state"));

		initMap();
		initLocation();
		initOilList();

		// 初始华导航引擎
		BaiduNaviManager.getInstance().initEngine(this, getSdcardDir(),
				mNaviEngineInitListener, new LBSAuthManagerListener() {
					@SuppressWarnings("unused")
					@Override
					public void onAuthResult(int status, String msg) {
						String str = null;
						if (0 == status) {
							str = "key校验成功!";
						} else {
							str = "key校验失败, " + msg;
						}
						// showToast(str);
					} 
				});

	}

	@Override
	public void onClickLeftBtn() {
		super.onClickLeftBtn();
		this.finish();
	}

	@OnClick({ R.id.map_myLocation, R.id.map_min_ib, R.id.map_out_ib,
			R.id.gas_nav })
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.map_myLocation:
			isFirstLoc = true;
			mLocationClient.requestLocation();
			break;
		case R.id.map_min_ib:// 放大
			++currentLevel;
			if (currentLevel > mBaiduMap.getMaxZoomLevel()) {
				showToast("已放大到最大");
				currentLevel--;
				return;
			}
			mBaiduMap.setMapStatus(MapStatusUpdateFactory.zoomTo(currentLevel));
			break;
		case R.id.map_out_ib:// 缩小
			--currentLevel;
			if (currentLevel < mBaiduMap.getMinZoomLevel()) {
				showToast("已缩小到最小");
				currentLevel++;
				return;
			}
			mBaiduMap.setMapStatus(MapStatusUpdateFactory.zoomTo(currentLevel));
			break;
		case R.id.gas_nav:
			// 起点
			BNaviPoint startPotion = new BNaviPoint(currentLng, currentLat,
					currentAddress, BNaviPoint.CoordinateType.GCJ02);
			// 终点
			BNaviPoint entPotion = new BNaviPoint(mBundle.getDouble("lat"),
					mBundle.getDouble("lng"), mBundle.getString("title"),
					BNaviPoint.CoordinateType.GCJ02);

			launchNavigatorViaPoints(startPotion, entPotion);
			break;

		default:
			break;
		}
	}

	/**
	 * 
	 * @Title: initMap
	 * @Description: 初始BaiduMap
	 * @param
	 * @return void
	 */
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
		// 设置图层
		LatLng latLng = new LatLng(mBundle.getDouble("lng"),
				mBundle.getDouble("lat"));
		BitmapDescriptor descriptor = BitmapDescriptorFactory
				.fromResource(R.drawable.nav_turn_via_1_s);
		OverlayOptions options = new MarkerOptions().position(latLng).icon(
				descriptor);
		mBaiduMap.addOverlay(options);
		// 设置中心点
		MapStatus status = new MapStatus.Builder().target(latLng).zoom(14)
				.build();
		MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory
				.newMapStatus(status);
		// 改变地图状态
		mBaiduMap.setMapStatus(mMapStatusUpdate);
	}

	/**
	 * 
	 * @Title: initOilList
	 * @Description: 初始化油号列表
	 * @param
	 * @return void
	 * @throws
	 */
	private void initOilList() {
		LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT);
		for (int i = 0; i < oilNumber.length; i++) {
			View view = getLayoutInflater().inflate(
					R.layout.oil_price_item_layout, null);
			TextView name = (TextView) view.findViewById(R.id.oil_number);
			TextView price = (TextView) view.findViewById(R.id.oil_price);
			name.setText(oilNumber[i]);
			price.setText(oilPrice[i]);
			mOilPriceListLayout.setLayoutParams(params);
			mOilPriceListLayout.addView(view);
		}
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

		mLocationClient = new LocationClient(this);
		mMyLocationData = new MyLocationListenner();
		mLocationClient.registerLocationListener(mMyLocationData);

		LocationClientOption option = new LocationClientOption();
		option.setOpenGps(true);// 打开gps
		option.setCoorType("bd09ll"); // 设置坐标类型
		option.setScanSpan(1000);

		mLocationClient.setLocOption(option);
		mLocationClient.start();

	}

	@Override
	protected void onResume() {
		super.onResume();
		mMapView.onResume();
	}

	@Override
	protected void onPause() {
		super.onPause();
		mMapView.onPause();
		this.finish();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		// 退出时销毁定位
		mLocationClient.stop();
		// 关闭定位图层
		mBaiduMap.setMyLocationEnabled(false);
		mMapView.onDestroy();
		mMapView = null;
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
		BaiduNaviManager.getInstance().launchNavigator(this, points, // 路线点列表
				NE_RoutePlan_Mode.ROUTE_PLAN_MOD_MIN_TIME, // 算路方式
				true, // 真实导航
				BaiduNaviManager.STRATEGY_FORCE_ONLINE_PRIORITY, // 在离线策略
				new OnStartNavigationListener() { // 跳转监听

					@Override
					public void onJumpToNavigator(Bundle configParams) {
						Intent intent = new Intent(GasStationMapActivity.this,
								BNavigatorActivity.class);
						intent.putExtras(configParams);
						startActivity(intent);
					}

					@Override
					public void onJumpToDownloader() {
					}
				});
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

	private String getSdcardDir() {
		if (Environment.getExternalStorageState().equalsIgnoreCase(
				Environment.MEDIA_MOUNTED)) {
			return Environment.getExternalStorageDirectory().toString();
		}
		return null;
	}

	/**
	 * 导航验证监听器
	 */
	private NaviEngineInitListener mNaviEngineInitListener = new NaviEngineInitListener() {
		public void engineInitSuccess() {

			mIsEngineInitSuccess = true;
			mHandler.post(new Runnable() {

				@Override
				public void run() {
					// showToast(mIsEngineInitSuccess + "");
				}
			});

		}

		public void engineInitStart() {
		}

		public void engineInitFail() {
			mIsEngineInitSuccess = false;
			mHandler.post(new Runnable() {

				@Override
				public void run() {
					// showToast(mIsEngineInitSuccess + "");
				}
			});
		}

	};
}

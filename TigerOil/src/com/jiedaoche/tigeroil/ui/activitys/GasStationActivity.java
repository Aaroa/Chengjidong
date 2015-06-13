package com.jiedaoche.tigeroil.ui.activitys;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.jiedaoche.tigeroil.common.dialog.PopupWindowUtil;
import com.jiedaoche.tigeroil.ui.BaseActivity;
import com.jiedaoche.tigeroil.ui.TOApplication;
import com.jiedaoche.tigeroil.ui.activitys.adapter.GasStationCommonAdapter;
import com.jiedaoche.tigeroil.ui.activitys.adapter.GasStationNearbyAdapter;
import com.jiedaoche.tigeroil.ui.activitys.adapter.GasStationViewPager;
import com.jiedaoche.tigeroil.ui.activitys.adapter.PopupListViewAdapter;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

/**
 * 
 * @ClassName: GasStationActivity
 * @Description: 加油站
 * @author Aaron
 * @date 2015-5-16 下午4:20:48
 * 
 */
@SuppressLint("InflateParams")
@ContentView(R.layout.activity_gas_station)
public class GasStationActivity extends BaseActivity implements
		OnPageChangeListener {

	@ViewInject(R.id.gas_station_viewPager)
	private ViewPager mViewPager;

	@ViewInject(R.id.common_text)
	private TextView mCommonText;

	@ViewInject(R.id.nearby_text)
	private TextView mNearbyText;

	private List<View> mList = new ArrayList<View>();

	private ListView mCommonListView;
	private PullToRefreshListView mNearbyListView;

	private GasStationViewPager mViewPageAdapter;

	private GasStationCommonAdapter mCommonAdapter;

	private GasStationNearbyAdapter mNearbyAdapter;

	private String mContent[];

	private PopupWindow mPopupWindow;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		ViewUtils.inject(this);
		TOApplication.getInstance().addActivity(this);
		setHeadTitle("加油站");
		initView();
		initCommonView();
		initNearbyView();
	}

	@Override
	public void onClickLeftBtn() {
		this.finish();
	}

	@OnClick({ R.id.common_text, R.id.nearby_text,
			R.id.gas_station_menu_distance, R.id.gas_station_menu_sales,
			R.id.gas_station_menu_grade })
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.common_text:
			mViewPager.setCurrentItem(0);
			mNearbyText.setTextColor(getResources().getColor(
					R.color.main_bg_press));
			mCommonText.setTextColor(getResources().getColor(
					R.color.oil_text_color));
			break;
		case R.id.nearby_text:
			mViewPager.setCurrentItem(1);
			mNearbyText.setTextColor(getResources().getColor(
					R.color.oil_text_color));
			mCommonText.setTextColor(getResources().getColor(
					R.color.main_bg_press));
			break;
		case R.id.gas_station_menu_distance:
			mPopupWindow = PopupWindowUtil.createPopupWindow(this,
					createDistanceView(), LayoutParams.MATCH_PARENT,
					LayoutParams.MATCH_PARENT);
			mPopupWindow.showAsDropDown(findViewById(R.id.gas_station_menu), 0,
					3);
			break;
		case R.id.gas_station_menu_sales:
			break;
		case R.id.gas_station_menu_grade:
			mPopupWindow = PopupWindowUtil.createPopupWindow(this,
					createGradeView(), LayoutParams.MATCH_PARENT,
					LayoutParams.MATCH_PARENT);
			mPopupWindow.showAsDropDown(findViewById(R.id.gas_station_menu), 0,
					3);
			break;

		default:
			break;
		}
	}

	private void initView() {
		View mCommonGasStation = getLayoutInflater().inflate(
				R.layout.gas_station_common_layout, null);
		mCommonListView = (ListView) mCommonGasStation
				.findViewById(R.id.gas_station_common_listview);

		View mNearbyGasStation = getLayoutInflater().inflate(
				R.layout.gas_station_nearby_layout, null);
		mNearbyListView = (PullToRefreshListView) mNearbyGasStation
				.findViewById(R.id.gas_station_nearby_listview);

		mList.add(mCommonGasStation);
		mList.add(mNearbyGasStation);

		mViewPageAdapter = new GasStationViewPager(mList);
		mViewPager.setAdapter(mViewPageAdapter);
		mViewPager.setOnPageChangeListener(this);
	}

	private void initCommonView() {
		mCommonAdapter = new GasStationCommonAdapter(this);
		mCommonListView.setAdapter(mCommonAdapter);
	}

	private void initNearbyView() {
		mNearbyAdapter = new GasStationNearbyAdapter(this);
		mNearbyListView.setAdapter(mNearbyAdapter);
	}

	@Override
	public void onPageScrollStateChanged(int arg0) {
	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
	}

	@Override
	public void onPageSelected(int arg0) {
		switch (arg0) {
		case 0:
			mNearbyText.setTextColor(getResources().getColor(
					R.color.main_bg_press));
			mCommonText.setTextColor(getResources().getColor(
					R.color.oil_text_color));
			break;
		case 1:
			mNearbyText.setTextColor(getResources().getColor(
					R.color.oil_text_color));
			mCommonText.setTextColor(getResources().getColor(
					R.color.main_bg_press));
			break;

		default:
			break;
		}
	}

	private View createGradeView() {
		View view = getLayoutInflater().inflate(
				R.layout.popup_defalt_listview_layout, null);
		ListView mListView = (ListView) view.findViewById(R.id.popup_listview);
		mContent = getResources().getStringArray(R.array.grade);
		PopupListViewAdapter adapter = new PopupListViewAdapter(this, mContent);
		mListView.setAdapter(adapter);
		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				PopupWindowUtil.dismissDialog(mPopupWindow);
			}
		});
		return view;
	}
	private View createDistanceView() {
		View view = getLayoutInflater().inflate(
				R.layout.popup_defalt_listview_layout, null);
		ListView mListView = (ListView) view.findViewById(R.id.popup_listview);
		mContent = getResources().getStringArray(R.array.distance);
		PopupListViewAdapter adapter = new PopupListViewAdapter(this, mContent);
		mListView.setAdapter(adapter);
		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				PopupWindowUtil.dismissDialog(mPopupWindow);
			}
		});
		return view;
	}
}

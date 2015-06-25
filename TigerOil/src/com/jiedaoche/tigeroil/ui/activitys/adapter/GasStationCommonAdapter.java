package com.jiedaoche.tigeroil.ui.activitys.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.jiedaoche.tigeroil.modle.OrderControl;
import com.jiedaoche.tigeroil.ui.activitys.GasStationDetailsActivity;
import com.jiedaoche.tigeroil.ui.activitys.R;
import com.jiedaoche.tigeroil.utils.ISkipActivityUtil;

/**
 * 
 * @ClassName: GasStationCommonAdapter
 * @Description: 附近加油站适配器
 * @author Aaron
 * @date 2015年5月22日 上午11:40:21
 * 
 */
@SuppressLint({ "ViewHolder", "InflateParams" })
public class GasStationCommonAdapter extends BaseAdapter {

	private String[] stationName = new String[] { "中国石油新力加油站", "中国石化加油站",
			"深圳科技园加油站", "普滨加油站" };
	private String[] address = new String[] { "广州市天河区天府路冠盛酒店旁，天河公园正门附近",
			"广州市天河区黄埔大道241号", "深圳市南山区白石路3699号", "深圳市龙华新区民乐立交附近" };
	private String[] state=new String[]{"空闲","一般","爆满","空闲"};
	private String[] distance = new String[] { "2.1", "8.0", "6.0", "4.3" };
	private double[] lng = new double[] { 23.133318, 23.127302, 22.538014,
			22.603942 };
	private double[] lat = new double[] { 113.36865, 113.376629, 113.955008,
			114.054903 };

	private Context mContext;

	public GasStationCommonAdapter(Context context) {
		this.mContext = context;
	}

	@Override
	public int getCount() {
		return address.length;
	}

	@Override
	public Object getItem(int position) {
		return address[position];
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@SuppressLint("InflateParams")
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = LayoutInflater.from(mContext).inflate(
					R.layout.gas_station_common_item_layout, null);
			holder.nameText = (TextView) convertView
					.findViewById(R.id.gas_station_item_name_text);
			holder.distanceText = (TextView) convertView
					.findViewById(R.id.gas_station_item_distance);
			holder.addressText = (TextView) convertView
					.findViewById(R.id.gas_station_item_address_text);
			holder.mBtn = (Button) convertView
					.findViewById(R.id.gas_station_item_appointment);
			holder.state=(TextView)convertView.findViewById(R.id.gas_station_state);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.nameText.setText(stationName[position]);
		holder.distanceText.setText(distance[position]);
		holder.addressText.setText(address[position]);
		holder.state.setText(state[position]);
		holder.mBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				OrderControl control = new OrderControl(mContext,
						((Activity) mContext)
								.findViewById(R.id.oil_staticon_layout));
				control.setOilName(stationName[position]);
				control.setOilAddress(address[position]);
				control.createOrderViewDialog(false);
			}
		});
		convertView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Bundle bundle = new Bundle();
				bundle.putString("name", stationName[position]);
				bundle.putString("distance", distance[position]);
				bundle.putString("address", address[position]);
				bundle.putDouble("lng", lng[position]);
				bundle.putDouble("lat", lat[position]);
				bundle.putString("state", state[position]);
				ISkipActivityUtil.startIntent(mContext,
						GasStationDetailsActivity.class, bundle);
			}
		});
		return convertView;
	}

	private class ViewHolder {
		private TextView nameText;
		private TextView addressText;
		private TextView distanceText;
		private TextView state;
		private Button mBtn;
	}
}

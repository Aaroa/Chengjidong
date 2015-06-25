package com.jiedaoche.tigeroil.ui.activitys.adapter;

import java.util.ArrayList;
import java.util.List;

import com.jiedaoche.tigeroil.bean.OrderEntity;
import com.jiedaoche.tigeroil.ui.activitys.R;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

@SuppressLint({ "ViewHolder", "InflateParams" })
public class OrderAdapter extends BaseAdapter {

	private Context context;

	private List<OrderEntity> mList = new ArrayList<OrderEntity>();

	public OrderAdapter(Context context, List<OrderEntity> list) {
		this.context = context;
		if (list == null) {
			this.mList = new ArrayList<OrderEntity>();
		} else
			this.mList = list;
	}

	@Override
	public int getCount() {
		return mList.size();
	}

	@Override
	public Object getItem(int position) {
		return mList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHodler hodler = null;
		if (convertView == null) {
			hodler = new ViewHodler();

			convertView = LayoutInflater.from(context).inflate(
					R.layout.order_item_layout, null);
			hodler.stationName = (TextView) convertView
					.findViewById(R.id.station_name);
			hodler.address = (TextView) convertView.findViewById(R.id.address);
			hodler.totalPrice = (TextView) convertView
					.findViewById(R.id.total_price);
			hodler.date = (TextView) convertView.findViewById(R.id.date);
			hodler.state = (TextView) convertView.findViewById(R.id.state);

			convertView.setTag(hodler);

		} else {
			hodler = (ViewHodler) convertView.getTag();
		}

		hodler.stationName.setText(mList.get(position).getStationName());
		hodler.address.setText("地址:" + mList.get(position).getAddress());
		hodler.totalPrice.setText("总价:" + mList.get(position).getTotalPrice());
		hodler.date.setText("时间:" + mList.get(position).getDate());
		int s = mList.get(position).getState();
		if (s == 0) {
			hodler.state.setText("交易状态:未完成");
		} else if (s == 1) {
			hodler.state.setText("交易状态:完成");
		}
		return convertView;
	}

	private class ViewHodler {
		private TextView stationName, address, totalPrice, date, state;
	}

}

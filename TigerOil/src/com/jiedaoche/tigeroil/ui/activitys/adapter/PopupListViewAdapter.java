package com.jiedaoche.tigeroil.ui.activitys.adapter;

import com.jiedaoche.tigeroil.ui.activitys.R;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * 
 * @ClassName: PopupListViewAdapter
 * @Description: TODO
 * @author Aaron
 * @date 2015-5-17 上午8:35:49
 * 
 */
public class PopupListViewAdapter extends BaseAdapter {

	private String[] mContent;
	private Context mContext;

	public PopupListViewAdapter(Context context, String[] content) {
		this.mContext = context;
		this.mContent = content;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mContent.length;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return mContent[position];
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@SuppressLint({ "ViewHolder", "InflateParams" })
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		convertView = LayoutInflater.from(mContext).inflate(
				R.layout.popup_listview_item_layout, null);
		TextView textView = (TextView) convertView
				.findViewById(R.id.popup_item_text);
		textView.setText(mContent[position]);
		return convertView;
	}

}

package com.jiedaoche.tigeroil.ui.activitys.adapter;

import java.util.ArrayList;
import java.util.List;

import com.jiedaoche.tigeroil.bean.CommentEntity;
import com.jiedaoche.tigeroil.ui.activitys.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class CommentAdapter extends BaseAdapter {

	private List<CommentEntity> mList;
	private Context mContext;

	public CommentAdapter(Context context, List<CommentEntity> list) {
		this.mContext = context;
		if (list == null) {
			this.mList = new ArrayList<CommentEntity>();
		} else {
			this.mList = list;
		}
	}
	
	public void notifyDataSetChanged(List<CommentEntity> list){
		this.mList=list;
		notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return mList.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = LayoutInflater.from(mContext).inflate(
					R.layout.commnet_item_layout, null);
			holder.account = (TextView) convertView
					.findViewById(R.id.comment_account);
			holder.data = (TextView) convertView
					.findViewById(R.id.comment_data);
			holder.content = (TextView) convertView
					.findViewById(R.id.comment_content);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.account.setText(mList.get(position).getName());
		holder.data.setText(mList.get(position).getData());
		holder.content.setText(mList.get(position).getConten());
		return convertView;
	}

	private class ViewHolder {
		private TextView account, data, content;
	}

}

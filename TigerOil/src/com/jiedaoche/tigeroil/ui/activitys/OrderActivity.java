package com.jiedaoche.tigeroil.ui.activitys;

import android.os.Bundle;
import android.widget.ListView;

import com.jiedaoche.tigeroil.ui.BaseActivity;
import com.jiedaoche.tigeroil.ui.TOApplication;
import com.jiedaoche.tigeroil.ui.activitys.adapter.OrderAdapter;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;

@ContentView(R.layout.activity_order)
public class OrderActivity extends BaseActivity {

	@ViewInject(R.id.order_listview)
	private ListView mListView;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		ViewUtils.inject(this);
		TOApplication.getInstance().addActivity(this);
		setHeadTitle("我的订单");
		initView();
	}

	@Override
	public void onClickLeftBtn() {
		super.onClickLeftBtn();
		this.finish();
	}

	private void initView() {
		mListView.setAdapter(new OrderAdapter(this));
	}
}

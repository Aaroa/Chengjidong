package com.jiedaoche.tigeroil.ui.activitys;

import android.os.Bundle;
import android.widget.ListView;

import com.jiedaoche.tigeroil.ui.BaseActivity;
import com.jiedaoche.tigeroil.ui.TOApplication;
import com.jiedaoche.tigeroil.ui.activitys.adapter.CouponAdapter;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;

@ContentView(R.layout.activity_coupon)
public class CouponActivity extends BaseActivity {

	@ViewInject(R.id.coupon_listview)
	private ListView mListView;

	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		ViewUtils.inject(this);
		TOApplication.getInstance().addActivity(this);
		setHeadTitle("我的优惠卷");
		initView();
	}

	@Override
	public void onClickLeftBtn() {
		// TODO Auto-generated method stub
		super.onClickLeftBtn();
		this.finish();
	}

	private void initView() {
		mListView.setAdapter(new CouponAdapter(this));
	}
}

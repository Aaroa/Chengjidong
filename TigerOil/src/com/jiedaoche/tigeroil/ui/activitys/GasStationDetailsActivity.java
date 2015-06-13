package com.jiedaoche.tigeroil.ui.activitys;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.TextView;

import com.jiedaoche.tigeroil.bean.CommentEntity;
import com.jiedaoche.tigeroil.modle.OrderControl;
import com.jiedaoche.tigeroil.ui.BaseActivity;
import com.jiedaoche.tigeroil.ui.TOApplication;
import com.jiedaoche.tigeroil.ui.activitys.adapter.CommentAdapter;
import com.jiedaoche.tigeroil.utils.ISkipActivityUtil;
import com.jiedaoche.tigeroil.utils.IStringUtils;
import com.jiedaoche.tigeroil.utils.ISystemTool;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * 
 * @ClassName: GasStationDetailsActivity
 * @Description: 常用加油站适配器
 * @author Aaron
 * @date 2015年5月22日 上午11:39:56
 * 
 */
@ContentView(R.layout.activity_gas_station_details)
public class GasStationDetailsActivity extends BaseActivity {

	@ViewInject(R.id.gas_station_comment_listView)
	private ListView mListView;

	private List<CommentEntity> mList;

	private View mListHeadView;

	private CommentAdapter adapter;

	private Bundle bundle;

	private String oilNumber[] = new String[] { "92#汽油", "95#汽油", "0#柴油" };
	private String oilPrice[] = new String[] { "6.32", "6.98", "6.12" };

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		ViewUtils.inject(this);
		TOApplication.getInstance().addActivity(this);
		bundle = getIntent().getExtras();
		setHeadTitle("详情");
		initData();
		initView();
		mListView.addHeaderView(mListHeadView);
		adapter = new CommentAdapter(this, mList);
		mListView.setAdapter(adapter);
	}

	@Override
	public void onClickLeftBtn() {
		super.onClickLeftBtn();
		this.finish();
	}

	private void initData() {
		mList = new ArrayList<CommentEntity>();
		CommentEntity entity = new CommentEntity();
		entity.setName("陈先生");
		entity.setConten("第一次去尝试，非常方便,哈哈，以后还会再来....");
		entity.setData("2015-03-21");
		mList.add(entity);

		entity = new CommentEntity();
		entity.setName("吴女士");
		entity.setConten("那边服务态度很好，还给打折，付款很方便...");
		entity.setData("2015-04-11");

		mList.add(entity);

		entity = new CommentEntity();
		entity.setName("麻先生");
		entity.setData("20150-5-22");
		entity.setConten("用了一次，还想用第二次，太方便了...");
		mList.add(entity);

		entity = new CommentEntity();
		entity.setName("龙先生");
		entity.setData("20150-5-03");
		entity.setConten("不错不错...");
		mList.add(entity);
	}

	private void initView() {
		mListHeadView = getLayoutInflater().inflate(R.layout.details_layout,
				null);
		TextView name = (TextView) mListHeadView
				.findViewById(R.id.details_name);
		TextView address = (TextView) mListHeadView
				.findViewById(R.id.details_address);
		TextView distance = (TextView) mListHeadView
				.findViewById(R.id.details_distance);
		TextView state=(TextView)mListHeadView.findViewById(R.id.details_state);

		LinearLayout priceLayout = (LinearLayout) mListHeadView
				.findViewById(R.id.details_oil_price_layout);

		name.setText(bundle.getString("name"));
		address.setText(bundle.getString("address"));
		distance.setText(bundle.getString("distance") + "km");
		state.setText(bundle.getString("state"));

		mListHeadView.findViewById(R.id.order).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						OrderControl control = new OrderControl(
								GasStationDetailsActivity.this,
								GasStationDetailsActivity.this
										.findViewById(R.id.oil_details_layout));
						control.setOilName(bundle.getString("name"));
						control.setOilAddress(bundle.getString("address"));
						control.showOrderSeleteDialog();
					}
				});
		mListHeadView.findViewById(R.id.current_order).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						OrderControl control = new OrderControl(
								GasStationDetailsActivity.this,
								GasStationDetailsActivity.this
										.findViewById(R.id.oil_details_layout));
						control.setOilName(bundle.getString("name"));
						control.setOilAddress(bundle.getString("address"));
						control.setmDateLong(Calendar.getInstance()
								.getTimeInMillis());
						control.setmTimeLong(ISystemTool.getData("HH:mm:ss",
								Calendar.getInstance().getTimeInMillis()));
						control.createOrderViewDialog();
					}
				});
		Button commentBtn = (Button) mListHeadView
				.findViewById(R.id.write_comment);
		commentBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Bundle bundle = new Bundle();
				bundle.putString("title", "评论");
				bundle.putInt("responeCode", 12);
				bundle.putString("hint", "请输入评论内容...");
				ISkipActivityUtil.startIntentForResult(
						GasStationDetailsActivity.this,
						GasStationDetailsActivity.this,
						CommEditTextActivity.class, bundle, 122);
			}
		});

		mListHeadView.findViewById(R.id.map_style).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						Bundle bundle = new Bundle();
						bundle.putString("title",
								GasStationDetailsActivity.this.bundle
										.getString("name"));
						bundle.putString("address",
								GasStationDetailsActivity.this.bundle
										.getString("address"));
						bundle.putDouble("lng",
								GasStationDetailsActivity.this.bundle
										.getDouble("lng"));
						bundle.putDouble("lat",
								GasStationDetailsActivity.this.bundle
										.getDouble("lat"));
						bundle.putString("state", GasStationDetailsActivity.this.bundle.getString("state"));
						ISkipActivityUtil.startIntent(
								GasStationDetailsActivity.this,
								GasStationMapActivity.class, bundle);
					}
				});
		mListHeadView.findViewById(R.id.details_address_layout)
				.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						Bundle bundle = new Bundle();
						bundle.putString("title",
								GasStationDetailsActivity.this.bundle
										.getString("name"));
						bundle.putString("address",
								GasStationDetailsActivity.this.bundle
										.getString("address"));
						bundle.putDouble("lng",
								GasStationDetailsActivity.this.bundle
										.getDouble("lng"));
						bundle.putDouble("lat",
								GasStationDetailsActivity.this.bundle
										.getDouble("lat"));
						bundle.putString("state", GasStationDetailsActivity.this.bundle.getString("state"));
						ISkipActivityUtil.startIntent(
								GasStationDetailsActivity.this,
								GasStationMapActivity.class, bundle);
					}
				});
		initOilList(priceLayout);
	}

	/**
	 * 
	 * @Title: initOilList
	 * @Description: 初始化油号列表
	 * @param
	 * @return void
	 * @throws
	 */
	private void initOilList(LinearLayout layout) {
		LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT);
		for (int i = 0; i < oilNumber.length; i++) {
			View view = getLayoutInflater().inflate(
					R.layout.oil_price_item_layout, null);
			TextView name = (TextView) view.findViewById(R.id.oil_number);
			TextView price = (TextView) view.findViewById(R.id.oil_price);
			name.setTextColor(getResources().getColor(R.color.balck));
			name.setText(oilNumber[i]);
			price.setText(oilPrice[i]);
			params.topMargin = 15;
			layout.setLayoutParams(params);
			layout.addView(view);
		}
	}

	@Override
	protected void onActivityResult(int arg0, int arg1, Intent arg2) {
		super.onActivityResult(arg0, arg1, arg2);
		if (arg0 == 122) {
			switch (arg1) {
			case 12:
				String result = arg2.getStringExtra("result");
				if (IStringUtils.isEmpty(result)) {
					return;
				}
				CommentEntity entity = new CommentEntity();
				entity.setName("李先生");
				entity.setData(ISystemTool.getDataTime("yyyy-MM-dd"));
				entity.setConten(result);
				mList.add(0, entity);
				adapter.notifyDataSetChanged(mList);
				// Toast.makeText(this, arg2.getStringExtra("result"),
				// 1).show();
				break;

			default:
				break;
			}
		}
	}
}

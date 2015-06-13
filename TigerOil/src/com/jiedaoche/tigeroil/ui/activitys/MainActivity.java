package com.jiedaoche.tigeroil.ui.activitys;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import android.os.Bundle;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jiedaoche.tigeroil.ui.BaseActivity;
import com.jiedaoche.tigeroil.ui.TOApplication;
import com.jiedaoche.tigeroil.ui.activitys.adapter.BaseViewPageAdapter;
import com.jiedaoche.tigeroil.utils.ISkipActivityUtil;
import com.jiedaoche.tigeroil.view.CircularImageView;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

/**
 * 
 * @ClassName: MainActivity
 * @Description: 主界面
 * @author Aaron
 * @date 2015-5-10 上午1:38:31
 * 
 */
@ContentView(R.layout.activity_main)
public class MainActivity extends BaseActivity implements OnPageChangeListener {

	@SuppressWarnings("unused")
	private final String TAG = this.getClass().getSimpleName();

	@ViewInject(R.id.main_gas_station)
	private TextView mGasStation;

	@ViewInject(R.id.main_store)
	private TextView mStone;

	@ViewInject(R.id.main_viewPager)
	private ViewPager mViewPager;

	@ViewInject(R.id.main_user_head_iv)
	private CircularImageView mHeadIV;

	@ViewInject(R.id.viewGroup)
	private ViewGroup mViewGroup;

	@ViewInject(R.id.main_menu_layout)
	private LinearLayout mMenuLayout;

	@ViewInject(R.id.main_view)
	private FrameLayout mMainView;

	/**
	 * 装点点的ImageView数组
	 */
	private ImageView[] tips;

	/**
	 * 图片资源id
	 */
	private int[] imgIdArray = new int[] { R.drawable.gas_station, R.drawable.page2,
			R.drawable.page3 };

	/**
	 * 装ImageView数组
	 */
	private ImageView[] mImageViews;

	/**
	 * 自定义定时器
	 */
	private ScheduledExecutorService mScheduledExecutorService;

	/**
	 * 默认position
	 */
	private final int DEFAULE_POSITION = -1;
	/**
	 * 定时器延迟时间
	 */
	private final int DEFAULT_KEYS_DIALER = 2;
	/**
	 * 定时周期
	 */
	private final int DEFAULT_KEYS_PERIOD = 4;

	/**
	 * 记录ViewPager当前的position
	 */
	private int position = 0;

	@Override
	public void handleMessage(Message msg) {
		mViewPager.setCurrentItem(position);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ViewUtils.inject(this);
		TOApplication.getInstance().addActivity(this);
		setHeadTitle(R.string.app_name);
		setHeadViewVisibility(View.GONE);

		initViewPage();
		setListener();

	}

	private void setListener() {
		mMainView.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_DOWN) {

				}
				return false;
			}
		});
	}

	private void initViewPage() {
		// 将点点加入到ViewGroup中
		tips = new ImageView[imgIdArray.length];
		for (int i = 0; i < tips.length; i++) {
			// 初始化ImageView
			ImageView imageView = new ImageView(this);
			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
					LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
			params.setMargins(10, 0, 10, 30);
			imageView.setLayoutParams(params);
			tips[i] = imageView;
			if (i == 0) {
				tips[i].setBackgroundResource(R.drawable.page_indicator_focused);
			} else {
				tips[i].setBackgroundResource(R.drawable.page_indicator_unfocused);
			}
			mViewGroup.addView(imageView);
		}

		// 将图片装载到数组中
		mImageViews = new ImageView[imgIdArray.length];
		for (int i = 0; i < mImageViews.length; i++) {
			ImageView imageView = new ImageView(this);
			mImageViews[i] = imageView;
			imageView.setBackgroundResource(imgIdArray[i]);
		}

		// 设置Adapter
		mViewPager.setAdapter(new BaseViewPageAdapter(this, mImageViews));
		// 设置监听，主要是设置点点的背景
		mViewPager.setOnPageChangeListener(this);
		// 设置ViewPager的默认项, 设置为长度的10的倍数，这样子开始就能往左滑动
		mViewPager.setCurrentItem((mImageViews.length) * 10);
		/**
		 * 设置ViewPager item间的距离,单位:PX
		 */
		mViewPager.setPageMargin(10);

	}

	@Override
	protected void onStart() {
		super.onStart();
		/**
		 * 启动定时器
		 */
		startTimer(DEFAULE_POSITION);
	}

	@Override
	protected void onPause() {
		/**
		 * 关闭定时器
		 */
		mScheduledExecutorService.shutdown();
		super.onPause();

	}

	@Override
	public void onClickLeftBtn() {
		super.onClickLeftBtn();
		this.finish();
	}

	@OnClick({ R.id.main_user_head_iv, R.id.main_menu_close_iv,
			R.id.main_store, R.id.main_gas_station, R.id.main_user,
			R.id.main_order, R.id.main_coupon, R.id.main_setting })
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.main_menu_close_iv:
			if (mHeadIV.getVisibility() == View.GONE) {
				mMenuLayout.setVisibility(View.GONE);
				mHeadIV.setVisibility(View.VISIBLE);
			}
			break;
		case R.id.main_user_head_iv:
			if (mMenuLayout.getVisibility() == View.GONE) {
				mHeadIV.setVisibility(View.GONE);
				mMenuLayout.setVisibility(View.VISIBLE);
			}
			break;
		case R.id.main_gas_station:
			if (mMenuLayout.getVisibility() == View.VISIBLE
					&& mHeadIV.getVisibility() == View.GONE) {
				mMenuLayout.setVisibility(View.GONE);
				mHeadIV.setVisibility(View.VISIBLE);
				return;
			}
			ISkipActivityUtil.startIntent(this, GasStationActivity.class);
			break;
		case R.id.main_store:
			if (mMenuLayout.getVisibility() == View.VISIBLE
					&& mHeadIV.getVisibility() == View.GONE) {
				mMenuLayout.setVisibility(View.GONE);
				mHeadIV.setVisibility(View.VISIBLE);
				return;
			}
			showToast("正在开发中...");
			break;
		case R.id.main_user:
			ISkipActivityUtil.startIntent(this, UserInfoActivity.class);
			break;
		case R.id.main_order:
			ISkipActivityUtil.startIntent(this, OrderActivity.class);
			break;
		case R.id.main_coupon:
			ISkipActivityUtil.startIntent(this, CouponActivity.class);
			break;
		case R.id.main_setting:
			ISkipActivityUtil.startIntent(this, SettingActivity.class);
			break;

		default:
			break;
		}
	}

	// arg0 ==1的时候表示正在滑动，arg0==2的时候表示滑动完毕了，arg0==0的时候表示什么都没做，就是停在那。
	@Override
	public void onPageScrollStateChanged(int arg0) {
		if (ViewPager.SCROLL_STATE_DRAGGING == arg0) {// 滑动过程中
			if (!mScheduledExecutorService.isShutdown()) {
				mScheduledExecutorService.shutdown();
			}
		} else {// 停止或者滑动完毕
			if (!mScheduledExecutorService.isShutdown()) {
				mScheduledExecutorService.shutdown();
			}
			if (mScheduledExecutorService != null) {
				mScheduledExecutorService = null;
			}
			startTimer(DEFAULE_POSITION);
		}
	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		// Log.d(TAG, "onPageScrolled=  " + arg0 + ", " + arg1 + ", " + arg2);
	}

	@Override
	public void onPageSelected(int arg0) {
		/**
		 * 设置指引下标
		 */
		setImageBackground(arg0 % mImageViews.length);
	}

	/**
	 * 设置选中的tip的背景
	 * 
	 * @param selectItems
	 */
	private void setImageBackground(int selectItems) {
		for (int i = 0; i < tips.length; i++) {
			if (i == selectItems) {
				tips[i].setBackgroundResource(R.drawable.page_indicator_focused);
			} else {
				tips[i].setBackgroundResource(R.drawable.page_indicator_unfocused);
			}
		}
	}

	/**
	 * 初始化定时器,并启动
	 */
	private void startTimer(final int index) {

		mScheduledExecutorService = Executors
				.newSingleThreadScheduledExecutor();
		/**
		 * 第一次参数:执行纯种 ; 第二个参数: 延迟时间; 第二个参数:执行的时间周期 ; 第四个参数:设置时间单位
		 */
		mScheduledExecutorService.scheduleAtFixedRate(new Runnable() {

			@Override
			public void run() {
				switch (index) {
				case DEFAULE_POSITION:
					position = mViewPager.getCurrentItem() + 1;
					break;
				default:
					position = index;
					break;
				}
				mHandler.obtainMessage().sendToTarget();
			}
		}, DEFAULT_KEYS_DIALER, DEFAULT_KEYS_PERIOD, TimeUnit.SECONDS);
	}
}

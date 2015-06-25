package com.jiedaoche.tigeroil.modle.db;

import java.util.List;

import com.jiedaoche.tigeroil.bean.OrderEntity;
import com.jiedaoche.tigeroil.ui.TOApplication;
import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.exception.DbException;

/**
 * 
 * @ClassName: DBFactoty
 * @Description: 数据库工厂类
 * @author Aaron
 * @date 2015-6-25 下午4:50:58
 * 
 */
public class DBFactoty {

	private static DbUtils mDbUtils = null;

	private static DbUtils getInstance() {
		if (mDbUtils == null) {
			mDbUtils = DbUtils.create(TOApplication.getInstance()
					.getApplicationContext());
		}
		return mDbUtils;
	}

	/**
	 * 
	 * @Title: queryOerder
	 * @Description: 查询所有订单
	 * @return List<OrderEntity>
	 */
	public static List<OrderEntity> queryOerder() {
		List<OrderEntity> mList = null;
		try {
			mList = getInstance().findAll(OrderEntity.class);
		} catch (DbException e) {
			e.printStackTrace();
		}
		return mList;
	}
}

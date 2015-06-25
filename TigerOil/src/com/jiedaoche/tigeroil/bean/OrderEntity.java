package com.jiedaoche.tigeroil.bean;

import com.lidroid.xutils.db.annotation.Column;

/**
 * 
 * @ClassName: orderEntity
 * @Description: 订单实体类
 * @author Aaron
 * @date 2015-6-25 下午3:58:51
 * 
 */
public class OrderEntity extends EntityBase {
	@Column(column = "oId")
	private int oId;

	@Column(column = "date")
	private String date;

	@Column(column = "StationName")
	private String StationName;

	@Column(column = "address")
	private String address;

	@Column(column = "phone")
	private String phone;

	@Column(column = "type")
	private String type;

	@Column(column = "price")
	private int price;

	@Column(column = "number")
	private int number;

	@Column(column = "totalPrice")
	private int totalPrice;

	@Column(column = "favorable")
	private String favorable;

	@Column(column = "ahsr")
	private int ahsr;

	@Column(column = "state")
	private int state;

	@Column(column = "oilNumber")
	private String oilNumber;

	public int getoId() {
		return oId;
	}

	public void setoId(int oId) {
		this.oId = oId;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getStationName() {
		return StationName;
	}

	public void setStationName(String stationName) {
		StationName = stationName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public int getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(int totalPrice) {
		this.totalPrice = totalPrice;
	}

	public String getFavorable() {
		return favorable;
	}

	public void setFavorable(String favorable) {
		this.favorable = favorable;
	}

	public int getAhsr() {
		return ahsr;
	}

	public void setAhsr(int ahsr) {
		this.ahsr = ahsr;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public String getOilNumber() {
		return oilNumber;
	}

	public void setOilNumber(String oilNumber) {
		this.oilNumber = oilNumber;
	}
	
	

}

package com.amazon.bean;

import java.util.ArrayList;
import java.util.List;


public class AmazonOrders {
	
	private String oid;				//订单Id
	private String ordertime;		//订单创建时间
	private double total;			//订单总金额
	private int state;			//订单状态：1、未付款；2、已付款，等待发货3、已发货，等待确认；4、订单完成
	private String address;			//订单地址
	private String name;			
	private String telephone;		//电话
	private String uid;
	private AmazonUser user;
	private List<AmazonOrderItem> orderItems = new ArrayList<>();
	public String getOid() {
		return oid;
	}
	public void setOid(String oid) {
		this.oid = oid;
	}
	public String getOrdertime() {
		return ordertime;
	}
	public void setOrdertime(String ordertime) {
		this.ordertime = ordertime;
	}
	public double getTotal() {
		return total;
	}
	public void setTotal(double total) {
		this.total = total;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public AmazonUser getUser() {
		return user;
	}
	public void setUser(AmazonUser user) {
		this.user = user;
	}
	public List<AmazonOrderItem> getOrderItems() {
		return orderItems;
	}
	public void setOrderItems(List<AmazonOrderItem> orderItems) {
		this.orderItems = orderItems;
	}
	@Override
	public String toString() {
		return "AmazonOrders [oid=" + oid + ", ordertime=" + ordertime + ", total=" + total + ", state=" + state
				+ ", address=" + address + ", name=" + name + ", telephone=" + telephone + ", uid=" + uid + ", user="
				+ user + ", orderItems=" + orderItems + "]";
	}
	

}

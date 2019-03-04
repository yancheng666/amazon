package com.amazon.bean;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

public class AmazonCart {
	private String C_id;
	private Map<String, AmazonCartItem> map = new LinkedHashMap<>();
	private Double amazonTotal = 0d;

	public Collection<AmazonCartItem> getAmazonCartItems() {
		return map.values();
	}
	// public void setMap(Map<String, AmazonCartItem> map) {
	// this.map = map;
	// }

	public Double getAmazonTotal() {
		return amazonTotal;
	}

	public String getC_id() {
		return C_id;
	}

	public void setC_id(String c_id) {
		C_id = c_id;
	}

	public Map<String, AmazonCartItem> getMap() {
		return map;
	}

	// 添加购物项
	public void add(AmazonCartItem amazonCartItem) {
		String pid = amazonCartItem.getAmazonProduct().getPid();
		AmazonCartItem amazonCartItem1 = map.get(pid);
		if (amazonCartItem1 == null) {
			map.put(pid, amazonCartItem);
		} else {
			amazonCartItem1.setAmazonCount(amazonCartItem1.getAmazonCount() + amazonCartItem.getAmazonCount());
		}
		amazonTotal += amazonCartItem.getAmazonSubtotal();
	}

	// 删除购物项
	public void remove(String pid) {
		AmazonCartItem amazonCartItem = map.remove(pid);
		amazonTotal -= amazonCartItem.getAmazonSubtotal();
	}

	// 清空购物车
	public void clear() {
		map.clear();
		amazonTotal = 0d;
	}

	@Override
	public String toString() {
		return "AmazonCart [map=" + map + ", amazonTotal=" + amazonTotal + "]";
	}

}

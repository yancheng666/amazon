package com.amazon.bean;

public class AmazonOrderItem {
	private String itemid;
	private int count;
	private double subtotal;
	private String oid ;
	private String pid ;
	//描述商品与订单之间的关系
	private AmazonProduct product;
	@Override
	public String toString() {
		return "AmazonOrderItem [itemid=" + itemid + ", count=" + count + ", subtotal=" + subtotal + ", oid=" + oid
				+ ", pid=" + pid + ", product=" + product + "]";
	}
	public String getItemid() {
		return itemid;
	}
	public void setItemid(String itemid) {
		this.itemid = itemid;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public double getSubtotal() {
		return subtotal;
	}
	public void setSubtotal(double subtotal) {
		this.subtotal = subtotal;
	}
	public String getOid() {
		return oid;
	}
	public void setOid(String oid) {
		this.oid = oid;
	}
	public String getPid() {
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}
	public AmazonProduct getProduct() {
		return product;
	}
	public void setProduct(AmazonProduct product) {
		this.product = product;
	}
	
}

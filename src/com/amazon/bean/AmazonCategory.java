package com.amazon.bean;

public class AmazonCategory {
	private String cid;
	private String cname;

	public AmazonCategory() {
		super();

	}

	public String getCid() {
		return cid;
	}

	public void setCid(String cid) {
		this.cid = cid;
	}

	public String getCname() {
		return cname;
	}

	public void setCname(String cname) {
		this.cname = cname;
	}

	@Override
	public String toString() {
		return "AmazonCategory [cid=" + cid + ", cname=" + cname + "]";
	}

}
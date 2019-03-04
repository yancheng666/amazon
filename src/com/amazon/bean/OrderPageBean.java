package com.amazon.bean;

import java.util.ArrayList;
import java.util.List;

public class OrderPageBean {
	private int currentPage;
	private int PageSize;
	private int totalPage;
	private int totalCount;
	private int State;
	private List<AmazonOrders> list=new ArrayList<>();
	public int getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}
	public int getPageSize() {
		return PageSize;
	}
	public void setPageSize(int pageSize) {
		PageSize = pageSize;
	}
	public int getTotalPage() {
		return totalPage;
	}
	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}
	public int getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}
	public List<AmazonOrders> getList() {
		return list;
	}
	public void setList(List<AmazonOrders> list) {
		this.list = list;
	}
	public int getState() {
		return State;
	}
	public void setState(int state) {
		State = state;
	}
	
	@Override
	public String toString() {
		return "OrderPageBean [currentPage=" + currentPage + ", PageSize=" + PageSize + ", totalPage=" + totalPage
				+ ", totalCount=" + totalCount + ", State=" + State + ", list=" + list + "]";
	}
	
}

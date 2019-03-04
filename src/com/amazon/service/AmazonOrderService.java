package com.amazon.service;

import java.util.List;

import com.amazon.bean.AmazonOrders;
import com.amazon.bean.OrderPageBean;
import com.amazon.bean.PageBean;

public interface AmazonOrderService {

	OrderPageBean findAll(String uid, int currentPage, String state);

	void save(AmazonOrders order);

	void pay(AmazonOrders order);

	OrderPageBean adminfindAll(String state, int currentPage);

	void update(String state, String oid);
}

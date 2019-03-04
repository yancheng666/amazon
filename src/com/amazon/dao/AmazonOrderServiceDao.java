package com.amazon.dao;

import java.sql.Connection;
import java.util.List;

import com.amazon.bean.AmazonOrders;
import com.amazon.bean.OrderPageBean;

public interface AmazonOrderServiceDao {

	int getCountByState(String state, String uid);

	List<AmazonOrders> findAll(String uid, int beginIndex, int pageSize, String state);

	void save(Connection conn, AmazonOrders order);

	void pay(AmazonOrders order);

	List<AmazonOrders> adminfindAll(int beginIndex, int pageSize, String state);

	void update(String state, String oid);

}

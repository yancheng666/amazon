package com.amazon.dao;

import java.util.List;

import com.amazon.bean.AmazonCategory;

public interface AmazonCategoryDao {
	//查看所有类目
	List<AmazonCategory> findAll();
	//添加类目
	void add(AmazonCategory category);
	//根据Cid查找该类，回显该类信息，
	AmazonCategory findByCid(String cid);
	//修改类目
	void update(AmazonCategory category);
	//删除类目
	void delete(String cid);

}

package com.amazon.service.impl;

import java.util.List;

import com.amazon.bean.AmazonCategory;
import com.amazon.dao.AmazonCategoryDao;
import com.amazon.dao.impl.AmazonCategoryDaoImpl;
import com.amazon.service.AmazonCategoryService;

public class AmazonCategoryServiceImpl implements AmazonCategoryService {
	AmazonCategoryDao dao=new AmazonCategoryDaoImpl();
	@Override
	public List<AmazonCategory> findAll() {
		// TODO Auto-generated method stub
		return dao.findAll();
	}

	@Override
	public void add(AmazonCategory category) {
		// TODO Auto-generated method stub
		dao.add(category);
	}

	@Override
	public AmazonCategory findByCid(String cid) {
		// TODO Auto-generated method stub
		return dao.findByCid(cid);
	}

	@Override
	public void update(AmazonCategory category) {
		// TODO Auto-generated method stub
		dao.update(category);
	}

	@Override
	public void delete(String cid) {
		// TODO Auto-generated method stub
		dao.delete(cid);
	}

}

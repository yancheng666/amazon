package com.amazon.dao.impl;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.amazon.bean.AmazonCategory;
import com.amazon.dao.AmazonCategoryDao;
import com.amazon.utils.C3P0Utils;
import com.amazon.utils.HBMUtils;

public class AmazonCategoryDaoImpl implements AmazonCategoryDao {

	@Override
	public List<AmazonCategory> findAll() {
		// TODO Auto-generated method stub
		QueryRunner query = new QueryRunner(C3P0Utils.getDataSource());
		List<AmazonCategory> list;
		try {
			list=query.query("select * from category", new BeanListHandler<>(AmazonCategory.class));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException();
		}
		return list;
	}
	
	public List<AmazonCategory> findAllByHbn() {
		      Session session = HBMUtils.getSession();
		      Query query = session.createQuery("from AmazonCategory");
		      List<AmazonCategory> list = query.list();
		      session.close();
		      return list;
	}

	@Override
	public void add(AmazonCategory category) {
		// TODO Auto-generated method stub
		QueryRunner query = new QueryRunner(C3P0Utils.getDataSource());
		try {
			query.update("insert into category values (?,?)", category.getCid(),category.getCname());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void addByHbn(AmazonCategory category) {
		Session session = HBMUtils.getSession();
		Transaction transaction = session.beginTransaction();
		session.save(category);
		transaction.commit();
		session.close();
	}
	//根据cid查询类目信息
	@Override
	public AmazonCategory findByCid(String cid) {
		// TODO Auto-generated method stub
		QueryRunner query = new QueryRunner(C3P0Utils.getDataSource());
		AmazonCategory category;
		try {
			category=query.query("select * from category where cid = ?",new BeanHandler<>(AmazonCategory.class), cid);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException();
		}
		return category;
	}
	
	public AmazonCategory findByCidHbn(String cid) {
		Session session = HBMUtils.getSession();
		Query qr = session.createQuery("from AmazonCategory where cid=?");
		qr.setString(0, cid);
		AmazonCategory Category = (AmazonCategory) qr.uniqueResult();
		session.close();
		return Category;
	}
	

	@Override
	public void update(AmazonCategory category) {
		// TODO Auto-generated method stub
		QueryRunner query=new QueryRunner(C3P0Utils.getDataSource());
		try {
			query.update("update category set cname=? where cid=?", category.getCname(),category.getCid());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void updateByHbn(AmazonCategory category) {
		Session session = HBMUtils.getSession();
		Transaction transaction = session.beginTransaction();
		session.update(category);
		transaction.commit();
		session.close();
	}
	
	//删除分类
	@Override
	public void delete(String cid) {
		// TODO Auto-generated method stub
		QueryRunner query=new QueryRunner(C3P0Utils.getDataSource());
		try {
			query.update("update product set cid=null where cid=?", cid);
			query.update("delete from category where cid=?", cid);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void deleteByHbn(String cid) {
		Session session = HBMUtils.getSession();
		Transaction transaction = session.beginTransaction();
		Query query = session.createQuery("from AmazonCategory where cid=?");
		query.setString(0, cid);
		AmazonCategory amazonCategory = (AmazonCategory) query.uniqueResult();
		session.delete(amazonCategory);
		transaction.commit();
		session.close();
	}
	

}

package com.amazon.dao.impl;

import java.sql.SQLException;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.amazon.bean.AmazonAdmin;
import com.amazon.bean.AmazonUser;
import com.amazon.dao.AmazonUserDao;
import com.amazon.utils.C3P0Utils;
import com.amazon.utils.HBMUtils;

public class AmazonUserDaoImpl implements AmazonUserDao {

	@Override
	public AmazonUser findByUsername(String username) {
		// TODO Auto-generated method stub
		QueryRunner query=new QueryRunner(C3P0Utils.getDataSource());
		AmazonUser amazonUser;
		try {
			amazonUser=query.query("select * from user where username=?", new BeanHandler<>(AmazonUser.class), username);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException();
		}
		return amazonUser;
	}

	public AmazonUser findByUsernameHbn(String username) {
		Session session = HBMUtils.getSession();
		Query query = session.createQuery("from AmazonUser where username=?");
		query.setString(0, username);
		AmazonUser amazonUser = (AmazonUser) query.uniqueResult();
		session.close();
		return amazonUser;	
	}
	
	@Override
	public void save(AmazonUser amazonUser) {
		// TODO Auto-generated method stub
		QueryRunner query=new QueryRunner(C3P0Utils.getDataSource());
		Object[] params={
				amazonUser.getUid(),amazonUser.getUsername(),amazonUser.getPassword(),amazonUser.getName(),
				amazonUser.getEmail(),amazonUser.getTelephone(),amazonUser.getBirthday(),amazonUser.getSex(),
				amazonUser.getState(),amazonUser.getCode()
		};
		try {
			query.update("insert into user values (?,?,?,?,?,?,?,?,?,?)", params );
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void saveByHbn(AmazonUser amazonUser) {
		Session session = HBMUtils.getSession();
		Transaction transaction = session.beginTransaction();
		session.save(amazonUser);
		transaction.commit();
		session.close();		
	}
	

	@Override
	public void updateState(String code) {
		// TODO Auto-generated method stub
		QueryRunner query=new QueryRunner(C3P0Utils.getDataSource());
		Object[] params = {1,null, code};
		try {
			query.update("update user set state = ?,code = ? where code = ?", params );
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public AmazonAdmin findByAdminUsername(String username) {
		// TODO Auto-generated method stub
		QueryRunner query=new QueryRunner(C3P0Utils.getDataSource());
		AmazonAdmin admin;
		try {
			admin=query.query("select * from admin where username=?", new BeanHandler<>(AmazonAdmin.class), username);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException();
		}
		
		return admin;
	}
	
	public AmazonAdmin findByAdminUsernameHbn(String username) {
		Session session = HBMUtils.getSession();
		Query query = session.createQuery("from AmazonAdmin where username=?");
		query.setString(0, username);
		AmazonAdmin amazonAdmin = (AmazonAdmin) query.uniqueResult();
		return amazonAdmin;	
	}
	

}

package com.amazon.dao.impl;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import com.amazon.bean.AmazonOrderItem;
import com.amazon.bean.AmazonOrders;
import com.amazon.bean.AmazonProduct;
import com.amazon.dao.AmazonOrderServiceDao;
import com.amazon.utils.C3P0Utils;

public class AmazonOrderServiceDaoImpl implements AmazonOrderServiceDao {
	@Override
	public List<AmazonOrders> findAll(String uid, int beginIndex, int pageSize,String state) {
		System.out.println("state:"+state);
		//select * from orders where 1=1
		QueryRunner query = new QueryRunner(C3P0Utils.getDataSource());
		StringBuffer sql1=new StringBuffer("select * from orders where 1=1 ");
		List list1=new ArrayList<>();
		if(state!=null && !("".equals(state.trim())) && !("0".equals(state.trim()))){
			sql1.append("and state=? ");
			list1.add(Integer.parseInt(state));
		}
		sql1.append("and uid=? ");
		sql1.append("order by ordertime desc ");
		sql1.append("limit ?,?");
		list1.add(uid);
		list1.add(beginIndex);
		list1.add(pageSize);
		String sql=sql1.toString();
		Object[] params = list1.toArray();
		List<AmazonOrders> list = null;
		try {
			list=query.query(sql,new BeanListHandler<AmazonOrders>(AmazonOrders.class),params);
			System.out.println(sql);
			for (AmazonOrders amazonOrders : list) {
				List<Map<String, Object>> list2 = query.query("SELECT * FROM orderitem o, product p WHERE o.pid = p.pid AND oid = ?", new MapListHandler(),amazonOrders.getOid());
				for (Map<String, Object> map : list2) {
					AmazonProduct product = new AmazonProduct();
					AmazonOrderItem orderitem= new AmazonOrderItem();
						BeanUtils.populate(product, map);
						BeanUtils.populate(orderitem, map);
						System.out.println("product"+product);
						orderitem.setProduct(product);
						orderitem.setOid(amazonOrders.getOid());
						amazonOrders.getOrderItems().add(orderitem);
				}
			}
		} catch (SQLException |IllegalAccessException | InvocationTargetException e) {
			
			e.printStackTrace();
		}
		System.out.println(list);
		return list;
	}
	
	public AmazonOrders findByOid(String oid) {
		QueryRunner query = new QueryRunner(C3P0Utils.getDataSource());
		AmazonOrders order=null;
		try {
			order = query.query("select * from orders where oid = ? ", new BeanHandler<>(AmazonOrders.class), oid);
			List<Map<String, Object>> list = query.query("select * from orderitem o, product p where o.pid = p.pid and o.oid = ?", new MapListHandler(), oid);
			for (Map<String, Object> map : list) {
				AmazonProduct product = new AmazonProduct();
				AmazonOrderItem orderitem = new AmazonOrderItem();
				BeanUtils.populate(product, map);
				BeanUtils.populate(orderitem, map);
				orderitem.setProduct(product);
				order.getOrderItems().add(orderitem);
			}
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		return order;
	}

	@Override
	public int getCountByState(String state,String uid) {
		QueryRunner query = new QueryRunner(C3P0Utils.getDataSource());
		StringBuffer sql =new  StringBuffer("select count(*) from orders  where 1=1 ");
		List list=new ArrayList<>();
		if(state !=null){
			sql.append("and state = ? ");
			list.add(state);
		}
		if(uid !=null&&!"".equals(uid)){
			sql.append("and uid =? ");
			list.add(uid);
		}
		String sql1=sql.toString();
		Object[] argms=list.toArray();
		int count = 0 ;
		try {
			count =((Long) query.query(sql1, new ScalarHandler(),argms)).intValue();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}

	@Override
	public void save(Connection conn, AmazonOrders order) {
		QueryRunner query= new QueryRunner();
		String sql="insert into orders value(?,?,?,?,?,?,?,?)";
		Object[] param={
			order.getOid(),order.getOrdertime(),order.getTotal(),
			order.getState(),order.getAddress(),order.getName(),
			order.getTelephone(),order.getUid()
		};
		try {
			query.update(conn,sql,param);
			String sql2="insert into orderitem value(?,?,?,?,?)";
			List<AmazonOrderItem> list = order.getOrderItems();
			for (AmazonOrderItem orderItem : list) {
				Object[] param2={
						orderItem.getItemid(),orderItem.getCount(),
						orderItem.getSubtotal(),orderItem.getPid(),
						orderItem.getOid()
				};
			query.update(conn,sql2,param2);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void pay(AmazonOrders order) {
		QueryRunner query= new QueryRunner(C3P0Utils.getDataSource());
		if(order.getAddress()!=null)
			try {
				query.update("update orders set state = ? , address = ? , name = ? , telephone = ?  where oid = ? ",order.getState(),order.getAddress(),order.getName(),order.getTelephone(),order.getOid());
			} catch (SQLException e) {
				
				e.printStackTrace();
			}
		else{
			try {
				query.update("update orders set state = ? , name = ? , telephone = ?  where oid = ? ",order.getState(),order.getName(),order.getTelephone(),order.getOid());
			} catch (SQLException e) {
				
				e.printStackTrace();
			}
		}
	}

	@Override
	public List<AmazonOrders> adminfindAll(int beginIndex,int pageSize,String state) {
		QueryRunner query= new QueryRunner(C3P0Utils.getDataSource());
		List<AmazonOrders> list=null;
		StringBuffer sql = new StringBuffer("select * from orders where 1=1");
		List list1=new ArrayList<>();
		if(state!=null && !("".equals(state.trim())) && !("0".equals(state.trim()))){
			sql.append(" and state=? ");
			list1.add(Integer.parseInt(state));
		}
		sql.append(" order by ordertime desc ");
		sql.append(" limit ?,?");
		list1.add(beginIndex);
		list1.add(pageSize);
		Object[] argms=list1.toArray();
		String sql1=sql.toString();
				try {
					list = query.query(sql1, new BeanListHandler<>(AmazonOrders.class),argms);
				} catch (SQLException e) {
					e.printStackTrace();
				}
			
		for (AmazonOrders amazonOrders : list) {
			try {
				List<Map<String, Object>> list2 = query.query("select * from orderitem o,product p where o.pid = p.pid and oid = ? ", new MapListHandler(), amazonOrders.getOid());
				for (Map<String, Object> map : list2) {
					AmazonProduct product= new AmazonProduct();
					AmazonOrderItem orderItem = new AmazonOrderItem();
					BeanUtils.populate(product, map);
					BeanUtils.populate(orderItem, map);
					orderItem.setProduct(product);
					amazonOrders.getOrderItems().add(orderItem);
				}
			} catch (Exception e) {
				
				e.printStackTrace();
			}
		}
		return list;
	}

	@Override
	public void update(String state,String oid) {
		QueryRunner query= new QueryRunner(C3P0Utils.getDataSource());
		try {
			query.update("update orders set state = ? where oid = ?",state,oid);
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
	}




}

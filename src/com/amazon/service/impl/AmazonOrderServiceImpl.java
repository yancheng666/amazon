package com.amazon.service.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.DbUtils;

import com.amazon.bean.AmazonOrders;
import com.amazon.bean.OrderPageBean;
import com.amazon.bean.PageBean;
import com.amazon.dao.AmazonOrderServiceDao;
import com.amazon.dao.impl.AmazonOrderServiceDaoImpl;
import com.amazon.service.AmazonOrderService;
import com.amazon.utils.C3P0Utils;

public class AmazonOrderServiceImpl implements AmazonOrderService {
	AmazonOrderServiceDao dao = new AmazonOrderServiceDaoImpl();

	@Override
	public OrderPageBean findAll(String uid, int currentPage,String state) {
		OrderPageBean pageBean=new OrderPageBean();
		int totalCount=dao.getCountByState(state,uid);
		int pageSize=4;
		int totalPage=0;
		if(totalCount%pageSize==0){
			totalPage=totalCount/pageSize;
		}else{
			totalPage=totalCount/pageSize+1;
		}
		if(currentPage>totalPage)
			currentPage=totalPage;
		if(currentPage<1)
			currentPage=1;
		int beginIndex=(currentPage-1)*pageSize;
		List<AmazonOrders> list=dao.findAll(uid,beginIndex,pageSize,state);
		pageBean.setCurrentPage(currentPage);
		pageBean.setList(list);
		pageBean.setTotalCount(totalCount);
		pageBean.setTotalPage(totalPage);
		if(state!=null && !("".equals(state.trim()))){
			pageBean.setState(Integer.parseInt(state));
		}
		return pageBean;
	}

	@Override
	public void save(AmazonOrders order) {
		Connection conn = C3P0Utils.getConnection();
		try {
			conn.setAutoCommit(false);
			dao.save(conn,order);
			DbUtils.commitAndCloseQuietly(conn);
		} catch (SQLException e) {
			DbUtils.rollbackAndCloseQuietly(conn);
			e.printStackTrace();
		}
		
	}

	@Override
	public void pay(AmazonOrders order) {
		dao.pay(order);
	}
	
	@Override
	public OrderPageBean adminfindAll(String state,int currentPage) {
		OrderPageBean pageBean=new OrderPageBean();
		int totalCount=dao.getCountByState(state,null);
		int pageSize=4;
		int totalPage=0;
		if(totalCount%pageSize==0){
			totalPage=totalCount/pageSize;
		}else{
			totalPage=totalCount/pageSize+1;
		}
		if(currentPage>totalPage)
			currentPage=totalPage;
		if(currentPage<1)
			currentPage=1;
		int beginIndex=(currentPage-1)*pageSize;
		List<AmazonOrders> list=dao.adminfindAll(beginIndex,pageSize,state);
		pageBean.setCurrentPage(currentPage);
		pageBean.setList(list);
		pageBean.setTotalCount(totalCount);
		pageBean.setTotalPage(totalPage);
		if(state!=null && !("".equals(state.trim()))){
			pageBean.setState(Integer.parseInt(state));
		}
		return pageBean;
	}

	@Override
	public void update(String state,String oid) {
		dao.update(state,oid);
	}

}

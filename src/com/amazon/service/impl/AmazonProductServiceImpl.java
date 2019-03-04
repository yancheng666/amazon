package com.amazon.service.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import com.amazon.bean.AmazonCategory;
import com.amazon.bean.AmazonProduct;
import com.amazon.bean.PageBean;
import com.amazon.dao.AmazonProductDao;
import com.amazon.dao.impl.AmazonProductDaoImpl;
import com.amazon.service.AmazonProductService;
import com.amazon.utils.C3P0Utils;

public class AmazonProductServiceImpl implements AmazonProductService {
	AmazonProductDao dao=new AmazonProductDaoImpl();
	@Override
	public AmazonProduct findByPid(String pid) {
		// TODO Auto-generated method stub
		return dao.findByPid(pid);
	}
	@Override
	public List<AmazonCategory> FindAllCategory() {
		return dao.FindAllCategory();
	}

	@Override
	public PageBean FindProductCidAndPage(int currentPage, String cid) {
		PageBean pageBean = new PageBean();
		//设置当前页
		if(currentPage < 1){
			currentPage = 1;
		}
		//设置每页显示的记录数
		int pageSize = 6;
		//查询总记录数
		int totalCount = getCount(cid);
		//通过总记录数 对 每页显示记录数取模来确定总页数
		int totalPage = 0;
		if((totalCount % pageSize) == 0){
			totalPage = totalCount / pageSize;   
		}else {
			totalPage = totalCount / pageSize + 1; 
		}
		// 当前页不能大于总页数
		if(currentPage > totalPage){
			currentPage = totalPage;
		}
		// 设置查询的其实位置 limit  beginIndex
		int  beginIndex = (currentPage - 1) * pageSize;
		List<AmazonProduct> list = dao.FindProductCidAndPage(cid,beginIndex,pageSize);
		// 封装数据
		pageBean.setCurrentPage(currentPage);
		pageBean.setPageSize(pageSize);
		pageBean.setTotalCount(totalCount);
		pageBean.setTotalPage(totalPage);
		pageBean.setList(list);
		pageBean.setCid(cid);
		return pageBean;
	}
	
	
	public int getCount(String cid){
		QueryRunner qr=new QueryRunner(C3P0Utils.getDataSource());
		Long count = null;
		try {
			count = (Long) qr.query("SELECT COUNT(*) FROM product WHERE cid=?", new ScalarHandler(),cid);
		} catch (Exception e) {
			e.printStackTrace();
		}
		int totalCount = 0;
		if(count != null){
			totalCount = count.intValue();
		}
		return totalCount;
	}

	@Override
	public AmazonProduct FindProductDesc(String pid) {
		return dao.FindProductDesc(pid);
	}
	@Override
	public List<AmazonProduct> FindHotProduct() {
		return dao. FindHotProduct();
	}


	@Override
	public PageBean FindAllProduct(int currentPage) {
		PageBean pageBean = new PageBean();
		if(currentPage < 1){
			currentPage = 1;
		}
		int pageSize = 6;
		int totalCount = getCount(0);
		int totalPage = 0;
		if((totalCount % pageSize) == 0){
			totalPage = totalCount / pageSize;   
		}else {
			totalPage = totalCount / pageSize + 1; 
		}
		if(currentPage > totalPage){
			currentPage = totalPage;
		}
		int  beginIndex = (currentPage - 1) * pageSize;
		List<AmazonProduct> list = dao.FindProductCidAndPage(beginIndex,pageSize);
		pageBean.setCurrentPage(currentPage);
		pageBean.setPageSize(pageSize);
		pageBean.setTotalCount(totalCount);
		pageBean.setTotalPage(totalPage);
		pageBean.setList(list);
		
		return pageBean;
	}

	private int getCount(int i) {
		QueryRunner qr=new QueryRunner(C3P0Utils.getDataSource());
		Long count = null;
		try {
			if(i==0)
			count = (Long) qr.query("SELECT COUNT(*) FROM product where pflag=0", new ScalarHandler());
			else if(i==1) count = (Long) qr.query("SELECT COUNT(*) FROM product where pflag=1", new ScalarHandler());
		} catch (Exception e) {
			e.printStackTrace();
		}
		int totalCount = 0;
		if(count != null){
			totalCount = count.intValue();
		}
		return totalCount;
	}

	@Override
	public AmazonProduct EditProductByPid(String pid) {
		return dao.EditProductByPid(pid);
	}

	@Override
	public void AddAmazonProduct(AmazonProduct amazonProduct) {
		dao.AddAmazonProduct(amazonProduct);
	}
	@Override
	public void DelProductByPid(String pid) {
		Connection conn = C3P0Utils.getConnection();
		try {
			conn.setAutoCommit(false);
			 dao.DelProductByPid(conn,pid);
			DbUtils.commitAndCloseQuietly(conn);
		} catch (SQLException e) {
			DbUtils.rollbackAndCloseQuietly(conn);
			e.printStackTrace();
		}
	}
 
	@Override
	public AmazonProduct FindProductByPid(String pid) {
		
		return dao.FindProductByPid(pid);
	}

	@Override
	public PageBean WarehouseProduct(int currentPage) {
		PageBean pageBean = new PageBean();
		if(currentPage < 1){
			currentPage = 1;
		}
		int pageSize = 6;
		int totalCount = getCount(1);
		int totalPage = 0;
		if((totalCount % pageSize) == 0){
			totalPage = totalCount / pageSize;   
		}else {
			totalPage = totalCount / pageSize + 1; 
		}
		if(currentPage > totalPage){
			currentPage = totalPage;
		}
		int  beginIndex = (currentPage - 1) * pageSize;
		List<AmazonProduct> list = dao.WarehouseProduct(beginIndex,pageSize);
		pageBean.setCurrentPage(currentPage);
		pageBean.setPageSize(pageSize);
		pageBean.setTotalCount(totalCount);
		pageBean.setTotalPage(totalPage);
		pageBean.setList(list);
		
		return pageBean;
	}
	@Override
	public void UpdateProductByPid(AmazonProduct amazonProduct) {
		Connection conn = C3P0Utils.getConnection();
		try {
			conn.setAutoCommit(false);
			dao.UpdateProductByPid(conn,amazonProduct);
			DbUtils.commitAndCloseQuietly(conn);
		} catch (SQLException e) {
			DbUtils.rollbackAndCloseQuietly(conn);
			e.printStackTrace();
		}
		
	}
	@Override
	public void UnShelve(String pid) {
		dao.UnShelve(pid);
	}
	@Override
	public List<AmazonProduct> SearchDownFrameByContext(String context) {
		
		return dao.SearchDownFrameByContext(context);
	}
	@Override
	public PageBean SearchByContext(String context) {
		PageBean pageBean = new PageBean();
		int currentPage=1;
		//设置当前页
		//设置每页显示的记录数
		int pageSize = 3;
		//查询总记录数
		//int totalCount = dao.getCount();
		int totalCount =getConditionCount(context);
		//通过总记录数 对 每页显示记录数取模来确定总页数
		int totalPage = 0;
		if((totalCount % pageSize) == 0){
			totalPage = totalCount / pageSize;   
		}else {
			totalPage = totalCount / pageSize + 1; 
		}
		// 当前页不能大于总页数
		if(currentPage > totalPage){
			currentPage = totalPage;
		}
		//设置当前页
		if(currentPage < 1){
			currentPage = 1;
		}
		// 设置查询的其实位置 limit  beginIndex
		int  beginIndex = (currentPage - 1) * pageSize;
		//List<Customer> list = dao.findByPage(beginIndex,pageSize);
		List<AmazonProduct> list = dao.findByConditionPage(beginIndex, pageSize, context);
		// 封装数据
		pageBean.setCurrentPage(currentPage);
		pageBean.setPageSize(pageSize);
		pageBean.setTotalCount(totalCount);
		pageBean.setTotalPage(totalPage);
		pageBean.setList(list);
		
		return pageBean;
	}
	private int getConditionCount(String context) {
		QueryRunner qr=new QueryRunner(C3P0Utils.getDataSource());
		Long count = null;
		String sql="SELECT COUNT(*) FROM product WHERE pname LIKE" +"'"+"%"+context+"%"+"'";
		System.out.println("111"+sql);
		try {
			count = (Long) qr.query(sql, new ScalarHandler());
		} catch (Exception e) {
			e.printStackTrace();
		}
		int totalCount = 0;
		if(count != null){
			totalCount = count.intValue();
		}
		return totalCount;
	}
	@Override
	public PageBean FindProductAllByPage(int currentPage, String context) {
		PageBean pageBean = new PageBean();
		//设置当前页
		//设置每页显示的记录数
		int pageSize = 3;
		//查询总记录数
		//int totalCount = dao.getCount();
		int totalCount =getConditionCount(context);
		//通过总记录数 对 每页显示记录数取模来确定总页数
		int totalPage = 0;
		if((totalCount % pageSize) == 0){
			totalPage = totalCount / pageSize;   
		}else {
			totalPage = totalCount / pageSize + 1; 
		}
		// 当前页不能大于总页数
		if(currentPage > totalPage){
			currentPage = totalPage;
		}
		//设置当前页
		if(currentPage < 1){
			currentPage = 1;
		}
		// 设置查询的其实位置 limit  beginIndex
		int  beginIndex = (currentPage - 1) * pageSize;
		//List<Customer> list = dao.findByPage(beginIndex,pageSize);
		List<AmazonProduct> list = dao.findByConditionPage(beginIndex, pageSize, context);
		// 封装数据
		pageBean.setCurrentPage(currentPage);
		pageBean.setPageSize(pageSize);
		pageBean.setTotalCount(totalCount);
		pageBean.setTotalPage(totalPage);
		pageBean.setList(list);
		
		return pageBean;
	}
	
	

	
	

}

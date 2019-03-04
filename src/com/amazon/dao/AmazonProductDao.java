package com.amazon.dao;

import java.sql.Connection;
import java.util.List;

import com.amazon.bean.AmazonCategory;
import com.amazon.bean.AmazonProduct;
import com.amazon.bean.PageBean;

public interface AmazonProductDao {

	AmazonProduct findByPid(String pid);
	
	
	//查询所有分类
		List<AmazonCategory> FindAllCategory();
		//根据cid查询该类别的所有商品,并分页展示
		List<AmazonProduct> FindProductCidAndPage(String cid,int beginIndex, int pageSize);
		//根据商品id查询商品
		AmazonProduct FindProductDesc(String pid);
		
		List<AmazonProduct> FindHotProduct();
		List<AmazonProduct> FindProductCidAndPage(int beginIndex, int pageSize);
		AmazonProduct EditProductByPid(String pid);
		void AddAmazonProduct(AmazonProduct amazonProduct);
		void DelProductByPid(Connection conn, String pid);
		AmazonProduct FindProductByPid(String pid);
		List<AmazonProduct> WarehouseProduct(int currentPage, int pageSize);
		void UpdateProductByPid(Connection conn, AmazonProduct amazonProduct);
		void UnShelve(String pid);

		//根据搜索显示下拉框的内容
		List<AmazonProduct> SearchDownFrameByContext(String context);

		//根据搜索框的内容模糊查询
		List<AmazonProduct> findByConditionPage(int beginIndex, int pageSize, String context);
		
}

package com.amazon.service;

import java.util.List;

import com.amazon.bean.AmazonCategory;
import com.amazon.bean.AmazonProduct;
import com.amazon.bean.PageBean;

public interface AmazonProductService {

	AmazonProduct findByPid(String pid);
	
	
	
	//查找所有商品分类
		List<AmazonCategory> FindAllCategory();
	    //根据cid查询该类别的所有商品
		PageBean FindProductCidAndPage(int currentPage, String cid);
		//根据商品id查询商品
		AmazonProduct FindProductDesc(String pid);
		List<AmazonProduct> FindHotProduct();
		PageBean FindAllProduct(int currentPage);
		AmazonProduct EditProductByPid(String pid);
		void AddAmazonProduct(AmazonProduct amazonProduct);
		void DelProductByPid(String pid);
	    AmazonProduct FindProductByPid(String pid);
		PageBean WarehouseProduct(int currentPage);
		void UpdateProductByPid(AmazonProduct amazonProduct);
		void UnShelve(String pid);


		//根据搜索显示下拉框的内容
		List<AmazonProduct> SearchDownFrameByContext(String context);


		//搜索页下面的页码展示	
		PageBean SearchByContext(String context);


		//搜索页下面的页码展示	
		PageBean FindProductAllByPage(int currentPage, String context); 
}

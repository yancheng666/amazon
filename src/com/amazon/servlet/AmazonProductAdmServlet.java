package com.amazon.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.amazon.bean.AmazonCategory;
import com.amazon.bean.AmazonProduct;
import com.amazon.bean.PageBean;
import com.amazon.service.AmazonProductService;
import com.amazon.service.impl.AmazonProductServiceImpl;
import com.amazon.utils.BaseServlet;


public class AmazonProductAdmServlet extends BaseServlet {
	
	public String FindAllProduct(HttpServletRequest request, HttpServletResponse response) {
		int currentPage = Integer.parseInt(request.getParameter("currentPage"));
		String pflag = request.getParameter("pflag");
		AmazonProductService service=new AmazonProductServiceImpl();
		PageBean pageBean=new PageBean();
		if(pflag==null){
			pageBean=service.FindAllProduct(currentPage);
		}else{
			pageBean=service.WarehouseProduct(currentPage);
		}
		request.setAttribute("pageBean", pageBean);
		request.setAttribute("pflag", pflag);
		return "/admin/product/list.jsp";		
	}

	public String EditProductByPid(HttpServletRequest request, HttpServletResponse response) {
		  String pid = request.getParameter("pid");
		  AmazonProductService service=new AmazonProductServiceImpl();
		  AmazonProduct amazonProduct=service.EditProductByPid(pid);
		  //查找下拉菜单的所有分类
		  List<AmazonCategory> list = service.FindAllCategory();
		  request.setAttribute("list", list);
		  request.setAttribute("amazonProduct", amazonProduct);	
		  return "/admin/product/edit.jsp";		
	}
	
	public String DelProductByPid(HttpServletRequest request, HttpServletResponse response) {
		  String pid = request.getParameter("pid");
		  AmazonProductService service=new AmazonProductServiceImpl();
		  service.DelProductByPid(pid);
		  return "ProductAdmServlet?method=FindAllProduct&currentPage=1";
				
	}
	
	public String UnShelveByPid(HttpServletRequest request, HttpServletResponse response){
		
		String pid = request.getParameter("pid");
		AmazonProductService service=new AmazonProductServiceImpl();
		System.out.println("pid"+pid);
		service.UnShelve(pid);
		return "ProductAdmServlet?method=FindAllProduct&currentPage=1";
	}
	


}

package com.amazon.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.amazon.bean.AmazonProduct;
import com.amazon.service.AmazonProductService;
import com.amazon.service.impl.AmazonProductServiceImpl;

public class AmazonIndex extends HttpServlet {
	/**
	 * 首页热门商品
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		AmazonProductService service=new AmazonProductServiceImpl();
		List<AmazonProduct> list=service.FindHotProduct();
		request.setAttribute("list", list);
		System.out.println(list);
		request.getRequestDispatcher("/index.jsp").forward(request, response);
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doGet(request, response);
	}

}

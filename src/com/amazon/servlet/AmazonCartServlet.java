package com.amazon.servlet;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.amazon.bean.AmazonCart;
import com.amazon.bean.AmazonCartItem;
import com.amazon.bean.AmazonProduct;
import com.amazon.service.AmazonProductService;
import com.amazon.service.impl.AmazonProductServiceImpl;
import com.amazon.utils.BaseServlet;

public class AmazonCartServlet extends BaseServlet {
	//获得购物车方法
	public AmazonCart getCart(HttpServletRequest request){
		//从Session域中获得购物车内容
		AmazonCart amazonCart=(AmazonCart) request.getSession().getAttribute("amazonCart");
				//如果为空，则创建一个新的购物车并写到Session域
				if(amazonCart==null){
					amazonCart=new AmazonCart();
					request.getSession().setAttribute("amazonCart", amazonCart);
				}
				return amazonCart;
	}
	//加入购物车方法
	public String addToCart(HttpServletRequest request,HttpServletResponse response){
		//获得商品的pid
		String pid=request.getParameter("pid");
		int amazonCount=Integer.parseInt(request.getParameter("amazonCount"));
		AmazonProductService service=new AmazonProductServiceImpl();
		AmazonProduct amazonProduct=service.findByPid(pid);
		AmazonCartItem amazonCartItem=new AmazonCartItem();
		amazonCartItem.setAmazonProduct(amazonProduct);
		amazonCartItem.setAmazonCount(amazonCount);
		AmazonCart amazonCart=getCart(request);
		amazonCart.add(amazonCartItem);
		return "/cart.jsp";
	}
	//删除购物项
	public String remove(HttpServletRequest request,HttpServletResponse response){
		String pid=request.getParameter("pid");
		AmazonCart amazonCart=getCart(request);
		amazonCart.remove(pid);
		return "/cart.jsp";
	}
	//清空购物车
	public String clear(HttpServletRequest request,HttpServletResponse response){
		AmazonCart amazonCart=getCart(request);
		amazonCart.clear();
		return "/cart.jsp";
	}
	public String showCart(HttpServletRequest request,HttpServletResponse response){
		AmazonCart amazonCart=getCart(request);
		return "/cart.jsp";
	}

}

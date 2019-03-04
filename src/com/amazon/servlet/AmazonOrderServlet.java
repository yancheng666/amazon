package com.amazon.servlet;

import java.io.IOException;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.amazon.bean.AmazonCart;
import com.amazon.bean.AmazonCartItem;
import com.amazon.bean.AmazonOrderItem;
import com.amazon.bean.AmazonOrders;
import com.amazon.bean.AmazonUser;
import com.amazon.bean.OrderPageBean;
import com.amazon.bean.PageBean;
import com.amazon.service.AmazonOrderService;
import com.amazon.service.impl.AmazonOrderServiceImpl;
import com.amazon.utils.BaseServlet;
import com.amazon.utils.UUIDUtils;

public class AmazonOrderServlet extends BaseServlet {

	 AmazonOrderService service = new AmazonOrderServiceImpl();
	 //查看用户所有订单
public String findAllOrder(HttpServletRequest request, HttpServletResponse response){
	  AmazonUser user  = (AmazonUser) request.getSession().getAttribute("existUser");
	  //   user.getUid()
	  
	  //获取当前页
	  int currentPage = Integer.parseInt(request.getParameter("currentPage"));
	  String state = request.getParameter("state");
	  OrderPageBean pageBean=service.findAll(user.getUid(),currentPage,state);
	  request.setAttribute("pageBean", pageBean);
	  return "/order_list.jsp";
}
	//从购物车保存订单
public String saveOrder(HttpServletRequest request, HttpServletResponse response){
	AmazonUser user = (AmazonUser) request.getSession().getAttribute("existUser");
	AmazonCart cart = (AmazonCart) request.getSession().getAttribute("amazonCart");
	//验证是否登录
	if(user==null){
		request.setAttribute("msg", "<h5 style='color:red'>请先登录</h5>");
		return "/login.jsp";
		}
	else{
		if(cart.getAmazonCartItems().isEmpty()){request.setAttribute("msg", "购物车空空如也，请先去购物");
		return "/cart.jsp";
		}else{
	AmazonOrders order = new AmazonOrders();
	order.setUid(user.getUid());
	order.setAddress(null);
	order.setOid(UUIDUtils.getUUID());
	order.setState(1);
	order.setOrdertime(new Date().toLocaleString());
	order.setTotal(cart.getAmazonTotal());
	order.setUser(user);
	Collection<AmazonCartItem> cartItems = cart.getAmazonCartItems();
	for (AmazonCartItem amazonCartItem : cartItems) {
		AmazonOrderItem orderItem=new AmazonOrderItem();
		orderItem.setItemid(UUIDUtils.getUUID());
		orderItem.setCount(amazonCartItem.getAmazonCount());
		orderItem.setOid(order.getOid());
		orderItem.setProduct(amazonCartItem.getAmazonProduct());
		orderItem.setSubtotal(amazonCartItem.getAmazonSubtotal());
		orderItem.setPid(orderItem.getProduct().getPid());
		order.getOrderItems().add(orderItem);
	}
	service.save(order);
	cart.clear();
	
	request.setAttribute("order", order);
	return "/order_info.jsp";
	}
	}
}

public String pay(HttpServletRequest request, HttpServletResponse response){
	String oid = request.getParameter("oid");
	String name = request.getParameter("name");
	String address = request.getParameter("address");
	String telephone = request.getParameter("telephone");
	int state =Integer.parseInt(request.getParameter("state")) ;
	AmazonOrders order = new AmazonOrders();
	if("".equals(address)||address!=null){
		order.setOid(oid);
		order.setName(name);
		order.setAddress(address);
		order.setState(state);
		order.setTelephone(telephone);
	}else{
		order.setOid(oid);
		order.setName(name);
		order.setState(state);
		order.setTelephone(telephone);
	}
	service.pay(order);
	return request.getContextPath()+"/amazonOrderServlet?method=findAllOrder&currentPage=1";
}
public String adminFindAllOrders(HttpServletRequest request, HttpServletResponse response){
	int  currentPage=Integer.parseInt(request.getParameter("currentPage"));
	String state = request.getParameter("state");
	OrderPageBean pageBean = service.adminfindAll(state,currentPage);
	request.setAttribute("pageBean", pageBean);
	return "/admin/order/list.jsp";
}

public String updateState(HttpServletRequest request, HttpServletResponse response){
	String oid = request.getParameter("oid");
	String state = request.getParameter("state");
	service.update(state,oid);
	return request.getContextPath()+"/amazonOrderServlet?method=adminFindAllOrders&currentPage=1";
}

public String updateState2(HttpServletRequest request, HttpServletResponse response){
	String oid = request.getParameter("oid");
	String state = request.getParameter("state");
	service.update(state,oid);
	return request.getContextPath()+"/amazonOrderServlet?method=findAllOrder&currentPage=1";
}

}
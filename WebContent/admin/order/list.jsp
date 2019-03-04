<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>订单列表</title>
    <link rel="stylesheet" href="css/bootstrap.min.css" type="text/css" />
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<meta http-equiv="content-type" content="text/html;charset=utf-8">
	<link rel="stylesheet" href="css/bootstrap.min.css" type="text/css" />
	<script src="js/jquery-1.11.3.min.js" type="text/javascript"></script>
	<script src="js/bootstrap.min.js" type="text/javascript"></script>
	<!-- 引入自定义css文件 style.css 
	<link rel="stylesheet" href="css/style.css" type="text/css" />-->
<style type="text/css">
	* {
		font-size: 11pt;
	}
	div {
		border: solid 2px rgb(78,78,78);
		width: 75px;
		height: 75px;
		text-align: center;
	}
	li {
		margin: 10px;
	}
</style>
  </head>
  
  <body style="background: rgb(254,238,189);">
<h1>我的订单</h1>

<table border="1" width="100%" cellspacing="0" background="black">
<c:forEach items="${pageBean.list}" var="order">
	<tr bgcolor="rgb(78,78,78)" bordercolor="rgb(78,78,78)" style="color: white;">
		<td colspan="6" style="color: white;">
			订单：${order.oid }&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;　成交时间：${order.ordertime }&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;　金额：<font color="red"><b>${order.total }</b></font>	
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<c:if test="${order.state==1 }"><font color="red"><b>未付款</b></font></c:if>
			<c:if test="${order.state==2 }"><font color="red"><a href="${ pageContext.request.contextPath }/amazonOrderServlet?method=updateState&oid=${order.oid }&state=3"><b>发货</b></a></font></c:if>
			<c:if test="${order.state==3 }"><font color="white"><b>未确认收货</b></font></c:if>
			<c:if test="${order.state==4 }"><font color="black"><b>订单完成</b></font></c:if>
		</td>
	</tr>
	<tr bgcolor="rgb(78,78,78)" bordercolor="rgb(78,78,78)" style="color: white;">
		<td colspan="8">收货人：${ order.name }&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		收货地址：${ order.address }&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		收货号码：${ order.telephone }&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		</td>
	</tr>
	<c:forEach items="${ order.orderItems }" var="orderitem">	
	<tr bordercolor="gray" align="center">
		<td width="15%">
			<div><img src="${ pageContext.request.contextPath }/${orderitem.product.pimage}" height="75"/></div>
		</td>
		<td>商品名：${orderitem.product.pname}</td>
		<td>单价：${orderitem.product.shop_price}元</td>
		<td>数量：${orderitem.count}</td>
		<td>小计：${orderitem.subtotal}元</td>
		<td>说明：${orderitem.product.pdesc}</td>
	</tr>
	</c:forEach>
  </c:forEach>
</table>
		<div style="width: 380px; margin: 0 auto; margin-top: 50px; border:none;">
		<ul class="pagination" style="text-align: center; margin-top: 10px;">
			
			<c:forEach    begin="${ pageBean.currentPage - 5 > 0 ? pageBean.currentPage - 5 : 1}" end="${ pageBean.currentPage + 4 > pageBean.totalPage ? pageBean.totalPage : pageBean.currentPage + 4 }" var="i" varStatus="vs">
				<!-- 当前页不能以链接的形式显示 -->
				<c:if test="${ pageBean.currentPage == i }">
					<li><a href="javascript:void(0);">${ i }</a></li>
				</c:if>
				<c:if test="${ pageBean.currentPage != i }">
					<c:if test="${pageBean.state==0}">
						<li><a href="${pageContext.request.contextPath}/amazonOrderServlet?method=adminFindAllOrders&currentPage=${ i }">${ i }</a></li>
					</c:if>
					<c:if test="${ pageBean.state!=0}">
						<li><a href="${pageContext.request.contextPath}/amazonOrderServlet?method=adminFindAllOrders&state=${pageBean.state}&currentPage=${ i }">${ i }</a></li>
					</c:if>
				</c:if>		
			</c:forEach>
			
		</ul>
		<h3 style="margin-left:-130px">共${pageBean.totalCount}条订单</h3>
		
	</div>
  </body>
</html>

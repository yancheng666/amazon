<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>

<head>
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>会员登录</title>
<link rel="stylesheet" href="css/bootstrap.min.css" type="text/css" />
<script src="js/jquery-1.11.3.min.js" type="text/javascript"></script>
<script src="js/bootstrap.min.js" type="text/javascript"></script>
<!-- 引入自定义css文件 style.css -->

<style>
body {
	margin-top: 20px;
	margin: 0 auto;
}

.carousel-inner .item img {
	width: 100%;
	height: 300px;
}
</style>
</head>

<body>


	<!-- 引入header.jsp -->
	<jsp:include page="/header.jsp"></jsp:include>

	<div class="container">
		<div class="row">
			<div style="margin: 0 auto; margin-top: 10px; width: 950px;">
				<strong>我的订单</strong>
				<table class="table table-bordered">
					<c:forEach items="${pageBean.list}" var="order">
					<tbody>
						<tr class="success">
							<th colspan="5">订单编号:${order.oid}
							&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;
								<c:if test="${order.state==1 }">未付款</c:if>
								<c:if test="${order.state==2 }">未发货</c:if>
								<c:if test="${order.state==3 }"><a href="${ pageContext.request.contextPath }/amazonOrderServlet?method=updateState2&oid=${order.oid }&state=4">确认收货</a></c:if>
								<c:if test="${order.state==4 }">已收货</c:if>
								</th>
						</tr>
						<tr class="warning">
							<th>图片</th>
							<th>商品</th>
							<th>价格</th>
							<th>数量</th>
							<th>小计</th>
						</tr>
						<c:forEach items="${order.orderItems}" var="orderitem">
						<tr class="active">
							<td width="60" width="40%"><input type="hidden" name="id"value="22"> 
							<a href="#"><img src="${orderitem.product.pimage }"  width="70" height="60"></a>
							</td>
							<td width="30%"><a href="${pageContext.request.contextPath}/productServlet?method=FindProductDesc&pid=${orderitem.product.pid}"> ${orderitem.product.pname }</a></td>
							<td width="20%">${orderitem.product.shop_price}</td>
							<td width="10%">${orderitem.count}</td>
							<td width="15%">${order.total}</td>
						</tr>
						</c:forEach>
					</tbody>
					</c:forEach>
				</table>
			</div>
		</div>
	<c:if test="${pageBean.totalCount>0 }">
		<div style="width: 380px; margin: 0 auto; margin-top: 50px;">
		<ul class="pagination" style="text-align: center; margin-top: 10px;">
			<li ><a href="#" aria-label="Previous"><span
					aria-hidden="true">&laquo;</span></a></li>
			<c:forEach    begin="${ pageBean.currentPage - 5 > 0 ? pageBean.currentPage - 5 : 1}" end="${ pageBean.currentPage + 4 > pageBean.totalPage ? pageBean.totalPage : pageBean.currentPage + 4 }" var="i" varStatus="vs">
				<!-- 当前页不能以链接的形式显示 -->
				<c:if test="${ pageBean.currentPage == i }">
					<li><a href="javascript:void(0);">${ i }</a></li>
				</c:if>
				<c:if test="${ pageBean.currentPage != i }">
					<li><a href="${ pageContext.request.contextPath }/amazonOrderServlet?method=findAllOrder&currentPage=${ i }">${ i }</a></li>
				</c:if>		
			</c:forEach>
			<li><a href="#" aria-label="Next"> <span aria-hidden="true">&raquo;</span>
			</a></li>
		</ul>
	</div>
	</c:if>
	<c:if test="${pageBean.totalCount==0 }"><h1>您的订单空空如也。请先去<a href="index.jsp">购物</a></h1></c:if>
	</div>
	
	

	<!-- 引入footer.jsp -->
	<jsp:include page="/footer.jsp"></jsp:include>
	
</body>

</html>
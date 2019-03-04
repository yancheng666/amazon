<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<HTML>
<HEAD>
<meta http-equiv="Content-Language" content="zh-cn">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="${pageContext.request.contextPath}/css/Style1.css"
	rel="stylesheet" type="text/css" />
<script type="text/javascript">
			function addProduct(){
				window.location.href = "${pageContext.request.contextPath}/admin/product/add.jsp";
			}
			function UnShelve(pid,ff){
				var flag = false;
				if(ff=="")
				flag=confirm("您确定要下架该商品吗？");
				else{flag=confirm("您确定要上架该商品吗？");}
				if(flag==true){
					window.location.href = "${ pageContext.request.contextPath }/ProductAdmServlet?method=UnShelveByPid&pid="+pid;
				}
			}
		</script>
</HEAD>
<body>
	<br>
	<form id="Form1" name="Form1"
		action="${pageContext.request.contextPath}/user/list.jsp"
		method="post">
		<table cellSpacing="1" cellPadding="0" width="100%" align="center"
			bgColor="#f5fafe" border="0">
			<TBODY>
				<tr>
					<td class="ta_01" align="center" bgColor="#afd1f3"><strong>商品列表</strong>
					</TD>
				</tr>
				<tr>
					<td class="ta_01" align="right">
					<c:if test="${ empty pflag }">
						<button type="button" id="add" name="add" value="添加"
							class="button_add" onclick="addProduct()">
							&#28155;&#21152;</button>
					</c:if>
					</td>
				</tr>
				<tr>
					<td class="ta_01" align="center" bgColor="#f5fafe">
						<table cellspacing="0" cellpadding="1" rules="all"
							bordercolor="gray" border="1" id="DataGrid1"
							style="BORDER-RIGHT: gray 1px solid; BORDER-TOP: gray 1px solid; BORDER-LEFT: gray 1px solid; WIDTH: 100%; WORD-BREAK: break-all; BORDER-BOTTOM: gray 1px solid; BORDER-COLLAPSE: collapse; BACKGROUND-COLOR: #f5fafe; WORD-WRAP: break-word">
							<tr
								style="FONT-WEIGHT: bold; FONT-SIZE: 12pt; HEIGHT: 25px; BACKGROUND-COLOR: #afd1f3">

								<td align="center" width="18%">序号</td>
								<td align="center" width="17%">商品图片</td>
								<td align="center" width="17%">商品名称</td>
								<td align="center" width="17%">商品价格</td>
								<td align="center" width="17%">是否热门</td>
								<td width="7%" align="center">编辑</td>
								<td width="7%" align="center">
								<c:if test="${not empty pflag}">上</c:if>
								<c:if test="${empty pflag}">下</c:if>
								架</td>
							</tr>
							<c:forEach items="${pageBean.list}" var="AmazonProduct" varStatus="v">
							<tr onmouseover="this.style.backgroundColor = 'white'"
								onmouseout="this.style.backgroundColor = '#F5FAFE';">
								<td style="CURSOR: hand; HEIGHT: 22px" align="center"
									width="18%">${v.count}</td>
								<td style="CURSOR: hand; HEIGHT: 22px" align="center"
									width="17%"><img width="40" height="45" src="${pageContext.request.contextPath}/${AmazonProduct.pimage}"></td>
								<td style="CURSOR: hand; HEIGHT: 22px" align="center"
									width="17%">${AmazonProduct.pname }</td>
								<td style="CURSOR: hand; HEIGHT: 22px" align="center"
									width="17%">${AmazonProduct.shop_price }</td>
								<c:if test="${AmazonProduct.is_hot==1}">
								<td style="CURSOR: hand; HEIGHT: 22px" align="center"
									width="17%">是</td>
								</c:if>
								<c:if test="${AmazonProduct.is_hot==0}">
								<td style="CURSOR: hand; HEIGHT: 22px" align="center"
									width="17%">否</td>
								</c:if>
								<td align="center" style="HEIGHT: 22px"><a
									href="${ pageContext.request.contextPath }/ProductAdmServlet?method=EditProductByPid&pid=${AmazonProduct.pid}">
										<img
										src="${pageContext.request.contextPath}/images/i_edit.gif"
										border="0" style="CURSOR: hand">
								</a></td>

								<td align="center" style="HEIGHT: 22px"><a href="javascript:void(0);" onclick="UnShelve('${AmazonProduct.pid}','${pflag}')"> 
								<img src="${pageContext.request.contextPath}/images/i_del.gif"
										width="16" height="16" border="0" style="CURSOR: hand">
								</a></td>
							</tr>
							
							</c:forEach>
							
						</table>
					</td>
				</tr>
              
			</TBODY>
		</table>
	</form>
	<div align="center">
			当前是：第&ensp;${ pageBean.currentPage }&ensp;页&ensp;/&ensp;共&ensp;${ pageBean.totalPage }&ensp;页
			&ensp;&ensp;
			<c:if test="${ pageBean.currentPage > 1 }">
				<a href="${pageContext.request.contextPath}/ProductAdmServlet?method=FindAllProduct&currentPage=1">[首页]</a>&ensp;
				<a href="${pageContext.request.contextPath}/ProductAdmServlet?method=FindAllProduct&currentPage=${pageBean.currentPage - 1}">[上一页]</a>&ensp;
			</c:if>
			<!-- 显示数字 -->
			<c:forEach begin="${ pageBean.currentPage - 5 > 0 ? pageBean.currentPage - 5 : 1}" end="${ pageBean.currentPage + 4 > pageBean.totalPage ? pageBean.totalPage : pageBean.currentPage + 4 }" var="i" varStatus="vs">
				<!-- 当前页不能以链接的形式显示 -->
				<c:if test="${ pageBean.currentPage == i }">
					&ensp;${ i }&ensp;
				</c:if>
				<c:if test="${ pageBean.currentPage != i }">
					&ensp;<a href="${pageContext.request.contextPath}/ProductAdmServlet?method=FindAllProduct&currentPage=${ i }">${ i }</a>&ensp;
				</c:if>
			</c:forEach>
			
			<c:if test="${ pageBean.currentPage < pageBean.totalPage }">
				<a href="${pageContext.request.contextPath}/ProductAdmServlet?method=FindAllProduct&currentPage=${pageBean.currentPage + 1}">[下一页]</a>&ensp;
				<a href="${pageContext.request.contextPath}/ProductAdmServlet?method=FindAllProduct&currentPage=${pageBean.totalPage}">[末页]</a>
			</c:if>
			&ensp;&ensp;
			总记录数：${ pageBean.totalCount } &ensp;条
			
		</div>
</body>
</HTML>


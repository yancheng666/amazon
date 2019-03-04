<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<!-- 登录 注册 购物车... -->
<div class="container-fluid">
	<div class="col-md-4">
		<img src="img/amazon.png" width="198"/>
	</div>
	<div class="col-md-5">
		<img src="img/header.png" />
	</div>
	<div class="col-md-3" style="padding-top:20px">
		<c:if test="${empty existUser}">
		<ol class="list-inline">
			<li><a href="login.jsp">登录</a></li>
			<li><a href="register.jsp">注册</a></li>
			<li><a href="cart.jsp">购物车</a></li>
		</ol>
		</c:if>
		<c:if test="${ not empty existUser }">
		
		<ol class="list-inline">
			<li>您好：${ existUser.name }</li>
			<li><a href="cart.jsp">购物车</a></li>
			<li><a href="${ pageContext.request.contextPath }/amazonOrderServlet?method=findAllOrder&currentPage=1">我的订单</a></li>
			<li><a href="${pageContext.request.contextPath}/userServlet?method=logout">退出</a></li>
		</ol>
		</c:if>
	</div>
</div>
<script type="text/javascript" src="js/jquery-1.11.3.min.js"></script>
<script type="text/javascript">
   $(function(){
	   var url="${pageContext.request.contextPath}/productServlet";
	   var sendData={
		         "method":"FindAllCategory",
		      }
	   $.post(url,sendData,function(backData,textStatus,ajax){
		   var data=ajax.responseText;
		   var json = eval("("+data+")");
	       for(var i = 0; i < json.length; i++){
	    	   var li="<li><a href='${pageContext.request.contextPath}/productServlet?method=FindProductCidAndPage&currentPage=1&cid="+json[i].cid+"'>"+json[i].cname+"</a></li>";
			   $(".navbar-nav").append(li);    	   
	       }
	   })
	   
	   $("#input1").blur(function(){
		   
		   
		   
		   
	   })
	   
   });
   
   function Search(){
		 //1.得到文本框的值
	       var context=document.getElementById("input1").value;
	       var div1=document.getElementById("div1");
	       //2.得到异步对象
	       var xmlhttp=getXMLHttpRequest();                   
	       //3.设置监听
	       xmlhttp.onreadystatechange=function(){
	         //判断状态
	         if(xmlhttp.readyState==4&&xmlhttp.status==200){
	           if(""!=context.trim()){
	            var data=xmlhttp.responseText;  
	            div1.innerHTML=data;
	            div1.style.display="block";
	           }else{
	             div1.style.display="none";
	           }
	         
	         }
	       
	       }
	       //4.打开连接
	       xmlhttp.open("GET","${pageContext.request.contextPath}/productServlet?method=SearchDownFrameByContext&context="+context, true);
	       xmlhttp.send(null);
	   }
   
   
   
   //创建  异步对象
	function getXMLHttpRequest() {
		var xmlhttp;
		if (window.XMLHttpRequest) {
			// code for IE7+, Firefox, Chrome, Opera, Safari
			xmlhttp = new XMLHttpRequest();
		} else {// code for IE6, IE5
			xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
		}
		return xmlhttp;
	}
</script>
<!-- 导航条 -->
<div class="container-fluid">
	<nav class="navbar navbar-inverse">
		<div class="container-fluid">
			<!-- Brand and toggle get grouped for better mobile display -->
			<div class="navbar-header">
				<button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1" aria-expanded="false">
					<span class="sr-only">Toggle navigation</span>
					<span class="icon-bar"></span>
					<span class="icon-bar"></span>
					<span class="icon-bar"></span>
				</button>
				<a class="navbar-brand" href="${pageContext.request.contextPath}/amazonIndex">首页</a>
			</div>

			<div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
				<ul class="nav navbar-nav" id="aaa">
					
				</ul>
				<form action="${pageContext.request.contextPath}/productServlet" method="post" class="navbar-form navbar-right" role="search">
				 <input type="hidden" name="method" value="FindAllProductBySearch">
				<div class="form-group" style="position:relative;">
						<input type="text" id="input1" name="context" class="form-control" placeholder="Search" onkeyup="Search()">
						<div id="div1" style="display:none;position:absolute;top:35px;left:0;width: 200px;border: 1px solid black; z-index:999; background-color: white;">
                  		</div>
					</div>
					<button type="submit" class="btn btn-default">搜索</button>
				</form>
			</div>
			
		</div>
	</nav>
</div>

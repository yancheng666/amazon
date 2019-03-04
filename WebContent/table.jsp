<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<style>
   a:HOVER {
	background-color: red;
	text-decoration: none;
}
</style>
<table border="0"  width="300">
<c:forEach items="${ list }" var="w">
<tr align="left">
<a href="${pageContext.request.contextPath}/productServlet?method=FindProductDesc&pid=${w.pid}" style="color: black;">${ w.pname }</a><br />
</tr>
</c:forEach>
</table>
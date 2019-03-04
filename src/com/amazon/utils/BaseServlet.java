package com.amazon.utils;

import java.io.IOException;
import java.lang.reflect.Method;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class BaseServlet extends HttpServlet {
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		//从页面获取方法名
		String methodName=request.getParameter("method");
		//使用反射获得子类的字节码文件对象
		Class clazz=this.getClass();
		//当methodName不等于null和空串的时候
		if(methodName!=null && !("".equals(methodName.trim()))){
			try {
				//通过反射获取同名方法的method对象
				Method method=clazz.getMethod(methodName,HttpServletRequest.class,HttpServletResponse.class);
				System.out.println(method);
				//反向执行同名方法
//				System.out.println(method.invoke(this, request,response));
				String path=(String)method.invoke(this, request,response) ;
				
				if(path!=null){
					if(path.contains(request.getContextPath())){
						System.out.println(1);
						//重定向
						response.sendRedirect(path);
					}else{
						System.out.println(2);
						//转发
						request.getRequestDispatcher(path).forward(request, response);
					}
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
		}else{
			//设置错误提示信息
			request.setAttribute("errMSG", "方法名不能为空或者空串");
			//转发到错误页面
			request.getRequestDispatcher("/error.jsp").forward(request, response);
		}
	}
}
package com.amazon.servlet;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.amazon.bean.AmazonCategory;
import com.amazon.service.AmazonCategoryService;
import com.amazon.service.impl.AmazonCategoryServiceImpl;
import com.amazon.utils.BaseServlet;
import com.amazon.utils.UUIDUtils;

/**
 * Servlet implementation class AmazonAdminCategoryServlet
 */
public class AmazonAdminCategoryServlet extends BaseServlet {
	//后台查看所有分类
	public String findAll(HttpServletRequest request,HttpServletResponse response){
		AmazonCategoryService service = new AmazonCategoryServiceImpl();
		//调用获得所有分类方法获得所有分类
		List<AmazonCategory> list=service.findAll();
		//写到request域中
		request.setAttribute("list", list);
		//转发
		return "/admin/category/list.jsp";
	}
	
	//添加分类
	public String add(HttpServletRequest request,HttpServletResponse response){
		//从页面中获取cname，分类名称
		String cname=request.getParameter("cname");
		AmazonCategory category=new AmazonCategory();
		//判断cname非空
		if(cname!=null && !"".equals(cname)){
			//设置Cid
			category.setCid(UUIDUtils.getUUID());
			//设置名称
			category.setCname(cname);
			AmazonCategoryService service = new AmazonCategoryServiceImpl();
			//调用添加方法写入数据库
			service.add(category);
		}else{
			//空的话显示错误内容
			request.setAttribute("msg", "请输入类目名称");
			//转发
			return "/admin/category/add.jsp";
		}
		//调用方法再查一次并在页面上显示
		return findAll(request,response);
	}
	//去修改页面，回显数据
	public String toEdit(HttpServletRequest request,HttpServletResponse response){
		//获取Cid
		String cid=request.getParameter("cid");
		AmazonCategoryService service = new AmazonCategoryServiceImpl();
		//通过Cid得到分类项
		AmazonCategory category=service.findByCid(cid);
		//写到域中
		request.setAttribute("category", category);
		//转发到修改页面
		return "/admin/category/edit.jsp";
	}
	//修改分类数据
	public String update(HttpServletRequest request,HttpServletResponse response){
		//获得id和名字
		String cid=request.getParameter("cid");
		String cname=request.getParameter("cname");
		//创建新的category对象
		AmazonCategory category=new AmazonCategory();
		//写到category中
		category.setCid(cid);
		category.setCname(cname);
		AmazonCategoryService service = new AmazonCategoryServiceImpl();
		//调用修改方法
		service.update(category);
		//转发给findAll
		return findAll(request,response);
	}
	//删除分类
	public String detele(HttpServletRequest request,HttpServletResponse response){
		//获得要删除的分类的cid
		String cid=request.getParameter("cid");
		AmazonCategoryService service = new AmazonCategoryServiceImpl();
		//调用删除方法删除
		service.delete(cid);
		//转发给findAll显示
		return findAll(request,response);
	}
}

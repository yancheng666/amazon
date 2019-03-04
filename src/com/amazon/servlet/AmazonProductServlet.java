package com.amazon.servlet;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.amazon.bean.AmazonCategory;
import com.amazon.bean.AmazonProduct;
import com.amazon.bean.PageBean;
import com.amazon.service.AmazonProductService;
import com.amazon.service.impl.AmazonProductServiceImpl;
import com.amazon.utils.BaseServlet;

import net.sf.json.JSONArray;

/**
 * Servlet implementation class AmazonProductServlet
 */
public class AmazonProductServlet extends BaseServlet {
	/**
	 * 查找所有分类
	 */
	Set<String> CookieList=new HashSet<>();
	String context;
	public String FindAllCategory(HttpServletRequest request, HttpServletResponse response)  {
		AmazonProductService service=new AmazonProductServiceImpl();
	    List<AmazonCategory> list=service.FindAllCategory();
	    //如果要取消某些字段，就必须使用带配置的方法
	    JSONArray jsonArray = JSONArray.fromObject(list);
	    try {
			response.getWriter().write(jsonArray.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
    /**
     * 根据cid查找该类的商品并分页
     */
	public String FindProductCidAndPage(HttpServletRequest request, HttpServletResponse response)  {
		int currentPage = Integer.parseInt(request.getParameter("currentPage"));
		String cid = request.getParameter("cid");
		AmazonProductService service=new AmazonProductServiceImpl();
		PageBean pageBean=service.FindProductCidAndPage(currentPage,cid);
		request.setAttribute("pageBean", pageBean);
		
		Set<AmazonProduct> CooKieList=new HashSet<>();
		for (String pid : CookieList) {
			AmazonProduct amazonProduct=service.FindProductByPid(pid);	
			CooKieList.add(amazonProduct);
		}
		request.getSession().setAttribute("CooKieList",CooKieList);
		return "/product_list.jsp";	
	}
	
	public String FindProductDesc(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String pid = request.getParameter("pid");
		AmazonProductService service=new AmazonProductServiceImpl();
		AmazonProduct amazonProduct=service.FindProductDesc(pid);
		request.setAttribute("amazonProduct", amazonProduct);
		CookieList.add(pid);
		return "/product_info.jsp";		
		
	}
	
	public String SearchDownFrameByContext(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String context=request.getParameter("context");
		 AmazonProductService service=new AmazonProductServiceImpl();
		 List<AmazonProduct> list=service.SearchDownFrameByContext(context); 
		 request.setAttribute("list", list);
		 return "/table.jsp";
		
	}
	/**
	 * 搜索后展示的页面
	 */
	public String FindAllProductBySearch(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 context = request.getParameter("context");
		 AmazonProductService service=new AmazonProductServiceImpl();
		 PageBean pageBean=service.SearchByContext(context);
		 System.out.println("aa"+context);
		 request.setAttribute("pageBean", pageBean);
		 return "/search_list.jsp";
		
		
	}
	
	//搜索页下面的页码展示
	public String FindProductAllByPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int currentPage = Integer.parseInt(request.getParameter("currentPage"));
		AmazonProductService service=new AmazonProductServiceImpl();
		PageBean pageBean=service.FindProductAllByPage(currentPage,context);
		request.setAttribute("pageBean", pageBean);	
		return "/search_list.jsp";
		
		
	}
	
}

package com.amazon.servlet;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.amazon.bean.AmazonProduct;
import com.amazon.service.AmazonProductService;
import com.amazon.service.impl.AmazonProductServiceImpl;
import com.amazon.utils.UUIDUtils;

/**
 * Servlet implementation class AmazonFileUpload
 */
public class AmazonFileUpload extends HttpServlet {
	

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doPost(request, response);
		
		
	}

	
	@SuppressWarnings("resource")
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
        response.setContentType("text/html,charset=utf-8");
        
        Map<String, String> map=new HashMap<String, String>();
        String fileName=null;
        //1.创建工具
        FileItemFactory factory=new DiskFileItemFactory();
        //2.创建解析类对象
        ServletFileUpload Upload=new ServletFileUpload(factory);
        //设置上传文件大小为1M
        Upload.setFileSizeMax(1024*1024*5);
        //3.解析request得到List<FileItem>
        try {
			List<FileItem> list = Upload.parseRequest(request);
			//4.遍历集合
			for (FileItem fileItem : list) {
		    //5.判断是否是普通表单项
				if(fileItem.isFormField()){
					//普通表单项
					String name = fileItem.getFieldName();
					String value = fileItem.getString("UTF-8");
					System.out.println(name+":"+value);
					//添加到map中
					map.put(name, value);
				}else{
					//文件上传项
				  fileName = fileItem.getName();
				  if(fileName!=null && !"".equals(fileName.trim())){
					  //获取图片存放的真实路径
					  String realPath = this.getServletContext().getRealPath("upload_image");
					  InputStream in = fileItem.getInputStream();
					  OutputStream out = new FileOutputStream(realPath+"\\"+fileName);
					  int len=0;
					  byte[] b=new byte[8192];
					  while ((len=in.read(b))!=-1) {
                         out.write(b, 0, len);					
					}
					  in.close();
					  out.close();
				  }
					
				}
				
			}
			
			//封装商品数据
			AmazonProduct amazonProduct=new AmazonProduct();
			BeanUtils.populate(amazonProduct, map);
			SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd");
			String data = dateFormat.format(new Date());
			System.out.println(amazonProduct);
			if(fileName!=null && !"".equals(fileName.trim())){
				amazonProduct.setPimage("upload_image/"+fileName);				
			}
			
			//调用业务层
			AmazonProductService service=new AmazonProductServiceImpl();
			service.UpdateProductByPid(amazonProduct);
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} 
        
        // 重定向
		response.sendRedirect(request.getContextPath() + "/ProductAdmServlet?method=FindAllProduct&currentPage=1");
		
	}

}

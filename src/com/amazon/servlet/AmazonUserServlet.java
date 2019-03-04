package com.amazon.servlet;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;

import com.amazon.bean.AmazonAdmin;
import com.amazon.bean.AmazonUser;
import com.amazon.service.AmazonUserService;
import com.amazon.service.impl.AmazonUserServiceImpl;
import com.amazon.utils.BaseServlet;
import com.amazon.utils.Encode;
import com.amazon.utils.RSAUtils;
import com.amazon.utils.UUIDUtils;

public class AmazonUserServlet extends BaseServlet {
	String publicKeyExponent;  //公钥指数 
	String publicKeyModulus;  //公钥模
	String privateKeyExponent;  //私钥指数
	String privateKeyModulus;  //私钥模
	RSAPublicKey rsaPublicKey;  //公钥
	RSAPrivateKey rsaPrivateKey;  //私钥 
	String password;              //解密后的密码
	//异步校验用户名
	public String checkUsername(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username = request.getParameter("username");
		System.out.println(username);
		if(username!=null&&!"".equals(username)){
		AmazonUserService service=new AmazonUserServiceImpl();
		AmazonUser existUser = service.findByUsername(username);
		if(existUser!=null){
			//说明用户名重复,不可注册
			response.getWriter().write("1");
			
		}else {
			//说明用户名不重复，不能注册
			response.getWriter().write("2");
		}
		
		
		}else{
			//用户名为空
			response.getWriter().write("3");
		}
		
		try {
			//非对称加密
			KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
			keyPairGenerator.initialize(512);
			KeyPair keyPair = keyPairGenerator.generateKeyPair();
			rsaPublicKey = (RSAPublicKey) keyPair.getPublic();
			rsaPrivateKey = (RSAPrivateKey) keyPair.getPrivate();
			privateKeyExponent = rsaPrivateKey.getPrivateExponent().toString();
			privateKeyModulus = rsaPrivateKey.getModulus().toString();
			//私钥保存在session中，用于解密
			request.getSession().setAttribute("privateKey", rsaPrivateKey);
			//公钥信息保存在页面，用于加密
			publicKeyExponent = rsaPublicKey.getPublicExponent().toString(16);
			publicKeyModulus = rsaPublicKey.getModulus().toString(16);
			request.setAttribute("publicKeyExponent", publicKeyExponent);
			request.setAttribute("publicKeyModulus", publicKeyModulus);
		} catch (Exception e) {
			e.printStackTrace();
		}	
		return null;
	}
	//注册
	public String regist(HttpServletRequest request,HttpServletResponse response) throws Exception{
		Thread.currentThread().sleep(1000);
		AmazonUser amazonUser=new AmazonUser();
		ServletContext context=getServletContext();
		//获得输入的验证码
		String quickmark=request.getParameter("quickmark");
		//获得系统的验证码
		String amazonQuickMark=(String) context.getAttribute("amazonQuickMark");
		AmazonUserService service=new AmazonUserServiceImpl();
		try {
			if(amazonQuickMark.equalsIgnoreCase(quickmark)){
			BeanUtils.populate(amazonUser, request.getParameterMap());
			amazonUser.setPassword(password);
			amazonUser.setUid(UUIDUtils.getUUID());
			amazonUser.setCode(UUIDUtils.getUUID());
			amazonUser.setState(0);
			amazonUser.setSex(request.getParameter("inlineRadioOptions"));
			amazonUser.setTelephone("10086");
			service.save(amazonUser);
			return request.getContextPath()+"/login.jsp";
			}else{
				request.setAttribute("MSG", "验证码输入错误！");
				return "register.jsp";
			}
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		
		return "/register.jsp";
	}
	//登录
	public String login(HttpServletRequest request,HttpServletResponse response){
		AmazonUser amazonUser=new AmazonUser();
		AmazonUser existUser=null;
		try {
			
			BeanUtils.populate(amazonUser, request.getParameterMap());
			String aa = request.getParameter("password");
			aa = Encode.xor(aa);
			aa = Encode.md5Encode(aa.getBytes());
			amazonUser.setPassword(aa);
			//获得输入的验证码
			String quickmark=request.getParameter("quickmark");
			AmazonUserService service=new AmazonUserServiceImpl();
			if(!"".equals(amazonUser.getUsername())&&amazonUser.getUsername()!=null&&!"".equals(amazonUser.getPassword())&&amazonUser.getPassword()!=null)
			existUser=service.findByUsername(amazonUser.getUsername());
			else{
				request.setAttribute("MSG", "用户名或密码错误，请重新登录！");
			}
			// 获取用户是否记住用户名的标识
			String isSave = request.getParameter("isSave");
			ServletContext context=getServletContext();
		
			//获得系统的验证码
			String amazonQuickMark=(String) context.getAttribute("amazonQuickMark");
			if(amazonQuickMark.equalsIgnoreCase(quickmark)){
			if(existUser!=null){
				if(0==existUser.getState()){
					request.setAttribute("MSG", "您还没有激活账号，请先去<a href='toMail.jsp'><font size='7'>邮箱</font></a>激活");
					return "/login.jsp";
				}else{
					if((existUser.getUsername().equals(amazonUser.getUsername())) &&(existUser.getPassword().equals(amazonUser.getPassword())) ){
						if(amazonQuickMark.equalsIgnoreCase(quickmark)){
							request.getSession().setAttribute("existUser", existUser);
							if("ok".equals(isSave)){
								Cookie cookie = new Cookie("existName", existUser.getUsername());
								cookie.setMaxAge(60 * 60);
								response.addCookie(cookie);
							}
							String autoLogin = request.getParameter("autoLogin");
							if("ok".equals(autoLogin)){
								Cookie cookie1 = new Cookie("autoLogin",existUser.getUsername() +"#amazon"+existUser.getPassword());
								cookie1.setMaxAge(60 * 60 * 24);
								response.addCookie(cookie1);
							}
						}else{
							request.setAttribute("MSG", "验证码输入错误，请重新登录！");
							return "/login.jsp";
						}
						return "/index.jsp";
					}else{
						request.setAttribute("MSG", "用户名或密码错误，请重新登录！");
						return "/login.jsp";
					}
					
				}
			}
			
			else{
			request.setAttribute("MSG", "用户名或密码错误，请重新登录！");
			return "/login.jsp";
			}
			}else{
				request.setAttribute("MSG", "验证码输入错误，请重新登录！");
				return "/login.jsp";
			}
		} catch (IllegalAccessException | InvocationTargetException e) {
			e.printStackTrace();
		} 
			request.setAttribute("MSG", "用户名或密码错误，请重新登录！");
			return "/login.jsp";

	}
	//激活
	public String active(HttpServletRequest request,HttpServletResponse response){
		String code=request.getParameter("code");
		AmazonUserService service=new AmazonUserServiceImpl();
		service.updateState(code);
		return request.getContextPath();
	}
	public String logout(HttpServletRequest request,HttpServletResponse response){
	    	request.getSession().removeAttribute("existUser");;
			return request.getContextPath()+"/index.jsp";
	    	
	    }
	public String adminLogin(HttpServletRequest request,HttpServletResponse response){
		AmazonAdmin admin=new AmazonAdmin();
		try {
			BeanUtils.populate(admin, request.getParameterMap());
			AmazonUserService service=new AmazonUserServiceImpl();
			AmazonAdmin existAdmin=service.findByAdminUsername(admin.getUsername());
		if(admin.getUsername()!=null && !"".equals(admin.getUsername())){
			if(existAdmin!=null){
				if((admin.getUsername().equals(existAdmin.getUsername())) && (admin.getPassword().equals(existAdmin.getPassword()))){
					request.getSession().setAttribute("existAdmin", existAdmin);
					return "/admin/home.jsp";
				}else{
					request.setAttribute("msg", "用户名或密码错误！");
					return "/admin/index.jsp";
				}
			}else{
				request.setAttribute("msg", "不存在此用户！");
				return "/admin/index.jsp";
			}
		}
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public String adminLogout(HttpServletRequest request,HttpServletResponse response){
    	request.getSession().removeAttribute("existAdmin");
		return request.getContextPath()+"/admin/index.jsp";
    	
    }
	
	//传给前端公钥模和公钥指数
	public String encode(HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.getWriter().write(publicKeyExponent+","+publicKeyModulus);
		return null;
	}
	
	//返回给后台加密后的密码，再解密
	public String encodepassword(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String encode = request.getParameter("encode");
		System.out.println("密文："+encode);		
		/**
		 * 私钥解密
		 */
		String decryptByPrivateKey = RSAUtils.decryptByPrivateKey(encode, rsaPrivateKey);
		System.out.println("解密后："+decryptByPrivateKey);
		String xor = Encode.xor(decryptByPrivateKey);
		String md5Encode = Encode.md5Encode(xor.getBytes());
		password=md5Encode;
		
		return null;
		
	}

}

package com.amazon.service.impl;

import javax.mail.MessagingException;

import com.amazon.bean.AmazonAdmin;
import com.amazon.bean.AmazonUser;
import com.amazon.dao.AmazonUserDao;
import com.amazon.dao.impl.AmazonUserDaoImpl;
import com.amazon.service.AmazonUserService;
import com.amazon.utils.MailUtils;

public class AmazonUserServiceImpl implements AmazonUserService {
	AmazonUserDao dao=new AmazonUserDaoImpl();
	@Override
	public AmazonUser findByUsername(String username) {
		return dao.findByUsername(username);
	}
	@Override
	public void save(AmazonUser amazonUser) {
		dao.save(amazonUser);
		String emailMsg = "恭喜您注册成功，请尽快激活账号："
+ "<a href='http://169.254.152.229:8080/amazon/userServlet?method=active&code="+amazonUser.getCode()+"'><font size='6'>"+amazonUser.getCode()+"</font></a>";
		try {
			MailUtils.sendMail(amazonUser.getEmail(), emailMsg);
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}
	@Override
	public void updateState(String code) {
		dao.updateState(code);
	}
	@Override
	public AmazonAdmin findByAdminUsername(String username) {
		return dao.findByAdminUsername(username);
	}

	

}

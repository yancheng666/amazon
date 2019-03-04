package com.amazon.utils;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

//hibernate工具类
public class HBMUtils {
    private static SessionFactory factory;    //会话工厂
  
    static {
    	//1.加载xml文件并读取内容获取数据
      Configuration config=new Configuration().configure();
        //2.构建session工厂
      factory=config.buildSessionFactory();
      
    }
 
    
    //创建session对象
    public static Session getSession(){
    	
    	return  factory.openSession();
    }
    
    //线程绑定的session
    public static Session getCurrentSession(){
    	
    	return factory.getCurrentSession();
    }
  
}

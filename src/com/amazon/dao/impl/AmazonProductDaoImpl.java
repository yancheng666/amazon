package com.amazon.dao.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.amazon.bean.AmazonCategory;
import com.amazon.bean.AmazonProduct;
import com.amazon.bean.PageBean;
import com.amazon.dao.AmazonProductDao;
import com.amazon.utils.C3P0Utils;

public class AmazonProductDaoImpl implements AmazonProductDao {

	@Override
	public AmazonProduct findByPid(String pid) {
		// TODO Auto-generated method stub
		QueryRunner query=new QueryRunner(C3P0Utils.getDataSource());
		AmazonProduct amazonProduct;
		try {
			amazonProduct=query.query("select * from product where pid=? ", new BeanHandler<>(AmazonProduct.class), pid);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException();
		}
		return amazonProduct;
	}

	@Override
	public List<AmazonCategory> FindAllCategory() {
		QueryRunner qr=new QueryRunner(C3P0Utils.getDataSource());
		List<AmazonCategory> list;
		try {
			list = qr.query("SELECT * FROM category", new BeanListHandler<>(AmazonCategory.class));
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		return list;
	}

	@Override
	public List<AmazonProduct> FindProductCidAndPage(String cid, int beginIndex, int pageSize) {
		QueryRunner qr=new QueryRunner(C3P0Utils.getDataSource());
		List<AmazonProduct> list;
		try {
			list = qr.query("SELECT * FROM product WHERE cid=? and pflag=0 LIMIT ?,?;", new BeanListHandler<>(AmazonProduct.class),cid,beginIndex,pageSize);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		return list;
	}
	@Override
	public AmazonProduct FindProductDesc(String pid) {
		QueryRunner qr=new QueryRunner(C3P0Utils.getDataSource());
		AmazonProduct amazonProduct;
		try {
			amazonProduct = qr.query("SELECT * FROM product WHERE pid=?", new BeanHandler<>(AmazonProduct.class), pid);		
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		return amazonProduct;
	}
	

	@Override
	public List<AmazonProduct> FindHotProduct() {
		QueryRunner qr=new QueryRunner(C3P0Utils.getDataSource());
		List<AmazonProduct> list;
		try {
         list = qr.query("SELECT * FROM product WHERE is_hot=1", new BeanListHandler<>(AmazonProduct.class));
		} catch (SQLException e) {	
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		return list;
	}

	

	@Override
	public List<AmazonProduct> FindProductCidAndPage(int beginIndex, int pageSize) {
		QueryRunner qr=new QueryRunner(C3P0Utils.getDataSource());
		List<AmazonProduct> list;
		try {
			list = qr.query("SELECT * FROM product where pflag=0 LIMIT ?,?;", new BeanListHandler<>(AmazonProduct.class),beginIndex,pageSize);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		return list;
	}

	@Override
	public AmazonProduct EditProductByPid(String pid) {
		QueryRunner qr=new QueryRunner(C3P0Utils.getDataSource());
		AmazonProduct amazonProduct;
		try {
		    amazonProduct = qr.query("SELECT * FROM product WHERE pid=?", new BeanHandler<>(AmazonProduct.class), pid);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		return amazonProduct;
	}
	

	@Override
	public void AddAmazonProduct(AmazonProduct amazonProduct) {
		QueryRunner qr = new QueryRunner(C3P0Utils.getDataSource());
		System.out.println(amazonProduct);
		String sql="INSERT INTO product VALUES (?,?,?,?,?,?,?,?,?,?)";
		Object[] params={amazonProduct.getPid(),amazonProduct.getPname(),amazonProduct.getMarket_price(),
				amazonProduct.getShop_price(),amazonProduct.getPimage(),amazonProduct.getPdate(),
				amazonProduct.getIs_hot(),amazonProduct.getPdesc(),amazonProduct.getPflag(),amazonProduct.getCid()};
		try {
			qr.update(sql, params);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	

	@Override
	public void DelProductByPid(Connection conn,String pid) {
		QueryRunner qr = new QueryRunner();
		try {
		    qr.update(conn,"UPDATE product SET cid=NULL WHERE pid=?",pid);
			qr.update(conn,"DELETE FROM product WHERE pid=?", pid);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
    
	@Override
	public AmazonProduct FindProductByPid(String pid) {
		QueryRunner qr = new QueryRunner(C3P0Utils.getDataSource());
		AmazonProduct amazonProduct;
		try {
		  amazonProduct = qr.query("SELECT * FROM product WHERE pid=?",new BeanHandler<>(AmazonProduct.class),pid);
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		
		return amazonProduct;
	}

	@Override
	public List<AmazonProduct> WarehouseProduct(int currentPage, int pageSize) {
		QueryRunner qr = new QueryRunner(C3P0Utils.getDataSource());
		List<AmazonProduct> list=null;
		try {
			list = qr.query("select * from product where pflag = 1 LIMIT ?,?;", new BeanListHandler<>(AmazonProduct.class),currentPage,pageSize);
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		return list;
	}


	@Override
	public void UpdateProductByPid(Connection conn, AmazonProduct amazonProduct) {
		QueryRunner qr = new QueryRunner();
		Object[] argms={
				amazonProduct.getPname(),amazonProduct.getMarket_price(),amazonProduct.getShop_price(),
				amazonProduct.getPimage(),amazonProduct.getPdate(),amazonProduct.getIs_hot(),
				amazonProduct.getPdesc(),amazonProduct.getPflag(),amazonProduct.getCid(),amazonProduct.getPid()
		};
		try {
			qr.update(conn,"update product set pname = ?,market_price = ?,shop_price = ?, pimage = ?,pdate=?, is_hot= ? , pdesc= ? ,pflag = ?, cid = ? where pid = ?", argms);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void UnShelve(String pid) {
		QueryRunner qr = new QueryRunner(C3P0Utils.getDataSource());
		AmazonProduct p = findByPid(pid);
		try {
			if(p.getPflag()==0)
			qr.update("update product set pflag = 1 where pid = ? ",pid);
			else{
			qr.update("update product set pflag = 0 where pid = ? ",pid);

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<AmazonProduct> SearchDownFrameByContext(String context) {
		System.out.println("11"+context);
		QueryRunner qr=new QueryRunner(C3P0Utils.getDataSource());
		List<AmazonProduct> list;
		try {
			list = qr.query("SELECT * FROM product WHERE pname LIKE ?", new BeanListHandler<>(AmazonProduct.class), context+"%");
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		System.out.println(list);
		return list;
	}

	@Override
	public List<AmazonProduct> findByConditionPage(int beginIndex, int pageSize, String context) {
		QueryRunner qr=new QueryRunner(C3P0Utils.getDataSource());
		String sql="SELECT * FROM product WHERE pname LIKE"+"'"+"%"+context+"%"+"'"+"LIMIT"+" "+beginIndex+","+pageSize;
		System.out.println("2"+sql);
		List<AmazonProduct> list;
		try {
		   list = qr.query(sql, new BeanListHandler<>(AmazonProduct.class));
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		
		return list;
	}


}

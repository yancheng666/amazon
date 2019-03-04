package com.amazon.bean;
/**
 * 
 * 
FieldTypeComment
pidvarchar(32) NOT NULL
pnamevarchar(50) NULL
market_pricedouble NULL
shop_pricedouble NULL
pimagevarchar(200) NULL
pdatedate NULL
is_hotint(11) NULL
pdescvarchar(255) NULL
pflagint(11) NULL
cidvarchar(32) NULL
 *
 */
public class AmazonProduct {
private String pid;
private String pname;
private Double market_price;
private Double shop_price;
private String pimage;
private String pdate;
private int is_hot;
private String pdesc;
private int pflag;
private String cid;
public String getPid() {
	return pid;
}
public void setPid(String pid) {
	this.pid = pid;
}
public String getPname() {
	return pname;
}
public void setPname(String pname) {
	this.pname = pname;
}
public Double getMarket_price() {
	return market_price;
}
public void setMarket_price(Double market_price) {
	this.market_price = market_price;
}
public Double getShop_price() {
	return shop_price;
}
public void setShop_price(Double shop_price) {
	this.shop_price = shop_price;
}
public String getPimage() {
	return pimage;
}
public void setPimage(String pimage) {
	this.pimage = pimage;
}
public String getPdate() {
	return pdate;
}
public void setPdate(String pdate) {
	this.pdate = pdate;
}
public int getIs_hot() {
	return is_hot;
}
public void setIs_hot(int is_hot) {
	this.is_hot = is_hot;
}
public String getPdesc() {
	return pdesc;
}
public void setPdesc(String pdesc) {
	this.pdesc = pdesc;
}
public int getPflag() {
	return pflag;
}
public void setPflag(int pflag) {
	this.pflag = pflag;
}
public String getCid() {
	return cid;
}
public void setCid(String cid) {
	this.cid = cid;
}
@Override
public String toString() {
	return "AmazonProduct [pid=" + pid + ", pname=" + pname + ", market_price=" + market_price + ", shop_price="
			+ shop_price + ", pimage=" + pimage + ", pdate=" + pdate + ", is_hot=" + is_hot + ", pdesc=" + pdesc
			+ ", pflag=" + pflag + ", cid=" + cid + "]";
}


}

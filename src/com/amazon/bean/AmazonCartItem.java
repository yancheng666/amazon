package com.amazon.bean;

public class AmazonCartItem {
private String CI_id;
private AmazonProduct amazonProduct;
private int amazonCount;
private Double amazonSubtotal;

public String getCI_id() {
	return CI_id;
}
public void setCI_id(String cI_id) {
	CI_id = cI_id;
}
public AmazonProduct getAmazonProduct() {
	return amazonProduct;
}
public void setAmazonProduct(AmazonProduct amazonProduct) {
	this.amazonProduct = amazonProduct;
}
public int getAmazonCount() {
	return amazonCount;
}
public void setAmazonCount(int amazonCount) {
	this.amazonCount = amazonCount;
}
public Double getAmazonSubtotal() {
	return amazonCount*amazonProduct.getShop_price();
}
//public void setAmazonSubtotal(Double amazonSubtotal) {
//	this.amazonSubtotal = amazonSubtotal;
//}
@Override
public String toString() {
	return "AmazonCartItem [amazonProduct=" + amazonProduct + ", amazonCount=" + amazonCount + ", amazonSubtotal="
			+ amazonSubtotal + "]";
}


}

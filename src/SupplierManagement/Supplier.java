package SupplierManagement;

import java.util.Date;

//거래처 정보 엔티티 클래스

public class Supplier {
	private int supplierID;
	private String supplierName;
	private String supplierTel;
	private Date supplierPersonalDay;
	private String supplierProduct;
	private int productPrice;
	
	public Supplier(int supplierID, String supplierName, String supplierTel, Date supplierPersonalDay, String supplierProduct, int productPrice){
		this.supplierID=supplierID;
		this.supplierName=supplierName;
		this.supplierTel=supplierTel;
		this.supplierPersonalDay=supplierPersonalDay;
		this.supplierProduct=supplierProduct;
		this.productPrice=productPrice;
	}
	
	public int getSupplierID() { return supplierID; }
	public String getSupplierName() { return supplierName; }
	public String getSupplierTel() { return supplierTel; }
	public Date getSupplierPersonalDay() { return supplierPersonalDay; }
	public String getSupplierProduct() { return supplierProduct; }
	public int getProductPrice() { return productPrice; }
	
	public void setSupplierID(int supplierID) { this.supplierID=supplierID; }
	public void setSupplierName(String supplierName) { this.supplierName=supplierName; }
	public void setSupplierTel(String supplierTel) { this.supplierTel=supplierTel; }
	public void setSupplierPersonalDay(Date supplierPersonalDay) { this.supplierPersonalDay=supplierPersonalDay; }
	public void setSupplierProduct(String supplierProduct) { this.supplierProduct=supplierProduct; }
	public void setProductPrice(int productPrice) { this.productPrice=productPrice; }
}

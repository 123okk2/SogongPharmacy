package ReceivingManagement;

import java.util.Date;

//입고 정보 엔티티 클래스

public class Receiving {
	private int receivingID;
	private int barcode;
	private int quantity;
	private Date receivingDate;
	private Date expirationDate;
	private int supplierID;
	private int productID;
	
	public Receiving(int receivingID, int barcode, int quantity, Date receivingDate, Date expirationDate, int supplierID, int productID){
		this.receivingID=receivingID;
		this.barcode=barcode;
		this.quantity=quantity;
		this.receivingDate=receivingDate;
		this.expirationDate=expirationDate;
		this.supplierID=supplierID;
		this.productID=productID;
	}
	
	public int getReceivingID() { return receivingID; }
	public int getBarcode() { return barcode; }
	public int getQuantity() { return quantity; }
	public Date getReceivingDate() { return receivingDate; }
	public Date getExpirationDate() { return expirationDate; }
	public int getSupplierID() { return supplierID; }
	public int getProductID() { return productID; }
	
	public void setReceivingID(int receivingID) { this.receivingID=receivingID; }
	public void setBarcode(int barcode) { this.barcode=barcode; }
	public void setQuantity(int quantity) { this.quantity=quantity; }
	public void setReceivingDate(Date receivingDate) { this.receivingDate=receivingDate; }
	public void setExpirationDate(Date expirationDate) { this.expirationDate=expirationDate; }
	public void setSupplierID(int supplierID) { this.supplierID=supplierID; }
	public void setProductID(int productID) { this.productID=productID; }
	
	public String toString() {
		return receivingID+" "+barcode+" "+quantity+" "+receivingDate+" "+expirationDate+" "+supplierID+" "+productID;
	}
}

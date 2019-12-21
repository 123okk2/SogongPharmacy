package ClosingManagement;

import java.util.Date;

public class salesPerform {
	private Date salesDate;
	private int productID;
	private int salesvolumePerDay;
	public salesPerform(Date salesDate,int productID, int salesvolumePerDay)
	{
		this.salesDate=salesDate;
		this.productID=productID;
		this.salesvolumePerDay=salesvolumePerDay;
	}
	public Date getSalesDate() {return salesDate;}
	public void setSalesDate(Date salesDate) { this.salesDate=salesDate;}
	public int getProductID() {return productID;}
	public void setProductID(int productID) {this.productID=productID;}
	public int getSalesVolumePerDay() {return salesvolumePerDay;}
	public void setSalesvolumePerDay(int salesvolumePerDay) {this.salesvolumePerDay = salesvolumePerDay;}
}

package StockManagement;

import java.util.Date;

//재고 현황 엔티티 클래스

public class StockCondition {
	private int productID;
	private int quantity;
	private int properStock;
	
	public StockCondition(int productID, int quantity, int properStock){
		this.productID=productID;
		this.quantity=quantity;
		this.properStock=properStock;
	}
	
	public int getProductID() { return productID; }
	public int getQuantity() { return quantity; }
	public int getProperStock() { return properStock; }
	
	public void setProductID(int productID) { this.productID=productID; }
	public void setQuantity(int quantity) { this.quantity=quantity; }
	public void setProperStock(int properStock) { this.properStock=properStock; }
	
}
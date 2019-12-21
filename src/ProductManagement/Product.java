package ProductManagement;

public class Product {
	private int productId;
	private String productName;
	private int productPrice;
	private int properStock;
	private int dngrProduct; //위험약픔   1: 위험약품, 0: 일반약품
	
	public Product(String productName, int productPrice, int properStock, int dngrProduct){
		this.productName = productName;
		this.productPrice = productPrice;
		this.properStock = properStock;
		this.dngrProduct = dngrProduct;
	}
	
	public Product(int productId, String productName, int productPrice, int properStock, int dngrProduct){
		this.productId = productId;
		this.productName = productName;
		this.productPrice = productPrice;
		this.properStock = properStock;
		this.dngrProduct = dngrProduct;
	}
	

	public int getProductID() {return productId;}
	public String getProductName() {return productName;}
	public int getProductPrice() {return productPrice;}
	public int getProperStock() {return properStock;}
	public int getDngrProduct() {return dngrProduct;}
	
	public void setProductID(int productID) {this.productId = productID;}
	public void setProductName(String productName) {this.productName = productName;}
	public void setProductPrice(int productPrice) {this.productPrice = productPrice;}
	public void setProperStock(int properStock) {this.properStock = properStock;}
	public void setDngrProduct(int dngrProduct) {this.dngrProduct = dngrProduct;}
}

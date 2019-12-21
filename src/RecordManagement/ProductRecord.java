package RecordManagement;

public class ProductRecord extends Record {
	private int productID;
	private String productName;
	
	public ProductRecord(int managementID, int productID, String productName) {
		super(managementID);
		this.productID = productID;
		this.productName = productName;
	}
	
	public int getProductID() {
		return productID;
	}
	public String getProductName() {
		return productName;
	}
}

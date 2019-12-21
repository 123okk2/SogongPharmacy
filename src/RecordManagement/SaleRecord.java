package RecordManagement;

public class SaleRecord extends Record {
	private String productName;
	private int quantity;
	
	public SaleRecord(int managementID, String productName, int quantity) {
		super(managementID);
		this.productName = productName;
		this.quantity = quantity;
	}
	
	public String getProductName() {
		return productName;
	}
	public int getQuantity() {
		return quantity;
	}
}
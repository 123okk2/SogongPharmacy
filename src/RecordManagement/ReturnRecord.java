package RecordManagement;

public class ReturnRecord extends Record {
	private String productName;
	private String supplierName;
	
	public ReturnRecord(int managementID, String productName, String supplierName) {
		super(managementID);
		this.productName = productName;
		this.supplierName = supplierName;
	}
	
	public String getProductName() {
		return productName;
	}
	public String getSupplierName() {
		return supplierName;
	}
}
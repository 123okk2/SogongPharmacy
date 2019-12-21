package RecordManagement;

import java.util.Date;

public class StockRecord extends Record {
	private String productName;
	private Date expirationDate;
	
	public StockRecord(int managementID, String productName, Date expirationDate) {
		super(managementID);
		this.productName = productName;
		this.expirationDate = expirationDate;
	}
	
	public String getProductName() {
		return productName;
	}
	public Date getExpirationDate() {
		return expirationDate;
	}
}
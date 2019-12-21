package RecordManagement;

import java.util.Date;

public class ReceivingRecord extends Record {
	private String productName;
	private int quantity;
	private Date receivingDate;
	
	public ReceivingRecord(int managementID, String productName, int quantity, Date receivingDate) {
		super(managementID);
		this.productName = productName;
		this.quantity = quantity;
		this.receivingDate = receivingDate;
	}
	
	public String getProductName() {
		return productName;
	}
	public int getQuantity() {
		return quantity;
	}
	public Date getReceivingDate() {
		return receivingDate;
	}
}
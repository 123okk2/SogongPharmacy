package ReturnDisposalManagement;

public class ReturnDisposal {
	private int returnDisposalID;
	private int barcode;
	private int supplierID;
	private int quantity;
	private String state;
	private String reason;

	public ReturnDisposal(int returnDisposalID, int barcode, int supplierID, int quantity, String state, String reason) {
		this.returnDisposalID = returnDisposalID;
		this.barcode = barcode;
		this.supplierID = supplierID;
		this.quantity = quantity;
		this.state = state;
		this.reason = reason;
	}
	
	public int getReturnDisposalID() {
		return returnDisposalID;
	}

	public void setReturnDisposalID(int returnDisposalID) {
		this.returnDisposalID = returnDisposalID;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getReason() {
		return reason;
	}

	public void setReturnReason(String reason) {
		this.reason = reason;
	}

	public int getBarcode() {
		return barcode;
	}

	public void setBarcode(int barcode) {
		this.barcode = barcode;
	}

	public int getSupplierID() {
		return supplierID;
	}

	public void setSupplierID(int supplierID) {
		this.supplierID = supplierID;
	}
}

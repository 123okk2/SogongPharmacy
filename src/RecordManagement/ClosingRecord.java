package RecordManagement;

import java.util.Date;

public class ClosingRecord extends Record {
	private Date date;
	private int salesPerDay;
	private int balance;
	
	public ClosingRecord(int managementID, Date date, int salesPerDay, int balance) {
		super(managementID);
		this.date = date;
		this.salesPerDay = salesPerDay;
		this.balance = balance;
	}

	public Date getDate() {
		return date;
	}
	public int getSalesPerDay() {
		return salesPerDay;
	}
	public int getBalance() {
		return balance;
	}
}
package CustomerManagement;

import java.sql.Date;

public class Customer {
	private int customerID;
	private String name;
	private String phoneNum;
	private Date birthdate;
	private boolean interest;
	
	public Customer(String n, String pn, Date bd) {
		name = n;
		phoneNum = pn;
		birthdate = bd;
	}
	public Customer(int cID, String n, String pn, Date bd, boolean i) {
		customerID = cID;
		name = n;
		phoneNum = pn;
		birthdate = bd;
		interest = i;
	}
	
	public int getCustomerID() { return customerID;}
	public String getName() { return name;}
	public String getPhoneNum() { return phoneNum;}
	public Date getBirthdate() { return birthdate;}
	public boolean getInterest() { return interest;}
	
	public void setCustomerID(int cID) { customerID = cID;}
	public void setName(String n) { name = n;}
	public void setPhoneNum(String pn) { phoneNum = pn;}
	public void setBirthdate(Date bd) { birthdate = bd;}
	public void setInterest(boolean i) { interest = i;}
}

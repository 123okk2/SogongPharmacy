package ClosingManagement;

import java.util.Date;
// ��������, �Ϻ� �����, �ܾ��� �����ϴ� closingInform ������ Ÿ��
public class closingInform {
	private Date closingDate;
	private int salesPerDay;
	private int balance;
	public closingInform(Date closingDate, int salesPerDay,int balance)
	{
		this.closingDate = closingDate;
		this.salesPerDay = salesPerDay;
		this.balance = balance;
	}
	public Date getClosingDate() { return  closingDate;}
	public int getSalesPerDay() { return salesPerDay;}
	public int getBalance() { return balance;}
	public void setClosingDate(Date closingDate) {this.closingDate = closingDate;}
	public void setSalesPerDay(int salsePerDay) {this.salesPerDay = salsePerDay;}
	public void setBalance(int balance) {this.balance = balance;}
}

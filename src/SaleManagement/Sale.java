package SaleManagement;

import java.util.Date;

public class Sale {
	private int saleid;
	private int managementid;
	private Date salesdate;
	private int approvalnum;
	
	public Sale() {}
	
	public Sale(int saleid, int managementid, Date salesdate, int approvalnum) {
		super();
		this.saleid = saleid;
		this.managementid = managementid;
		this.salesdate = salesdate;
		this.approvalnum = approvalnum;
	}
	public int getSaleid() {
		return saleid;
	}
	public void setSaleid(int saleid) {
		this.saleid = saleid;
	}
	public int getManagementid() {
		return managementid;
	}
	public void setManagementid(int managementid) {
		this.managementid = managementid;
	}
	public Date getSalesdate() {
		return salesdate;
	}
	public void setSalesdate(Date salesdate) {
		this.salesdate = salesdate;
	}
	public int getApprovalnum() {
		return approvalnum;
	}
	public void setApprovalnum(int approvalnum) {
		this.approvalnum = approvalnum;
	}
	
	
}

package SaleManagement;

public class SalesProduct {
	private int productquantity;
	private int saleid;
	private int productid;
	public SalesProduct() {}
	public SalesProduct(int productquantity, int saleid, int productid) {
		super();
		this.productquantity = productquantity;
		this.saleid = saleid;
		this.productid = productid;
	}
	public int getProductquantity() {
		return productquantity;
	}
	public void setProductquantity(int productquantity) {
		this.productquantity = productquantity;
	}
	public int getSaleid() {
		return saleid;
	}
	public void setSaleid(int saleid) {
		this.saleid = saleid;
	}
	public int getProductid() {
		return productid;
	}
	public void setProductid(int productid) {
		this.productid = productid;
	}
	
	
}

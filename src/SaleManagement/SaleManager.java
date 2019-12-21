package SaleManagement;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Vector;

import DBManagement.DBManager;
import ProductManagement.Product;
import StockManagement.Stock;


public class SaleManager {
	
	private DBManager dm;
	public SaleManager() {
	dm = new DBManager();
	
	}
	public Boolean RegisterSale(Sale sale) {
		
		return dm.doWithoutResult("insert into sale values('" + sale.getSaleid() + "','" + sale.getManagementid() 
		+ "' , '" + new SimpleDateFormat("yyyy-MM-dd").format(sale.getSalesdate()) +"' , '" + sale.getApprovalnum() +"')");
	}
	
	public Boolean RegisterSalesProduct(SalesProduct salesproduct) {
		
		return dm.doWithoutResult("insert into salesproduct values('" + salesproduct.getProductquantity() 
		+ "' , '" + salesproduct.getSaleid() + "' , '" + salesproduct.getProductid() +"')");
	}
	public Boolean DeleteSales(int saleid) {
		
		return dm.doWithoutResult("delete from sale where saleid = '" + saleid + "'");
	}
	public Boolean DeleteSalesProduct(int saleid) {
		
		return dm.doWithoutResult("delete from salesproduct where saleid = '" + saleid + "'");
	}
	public Boolean UpdateStockQuantity(int barcode,int quantity) {
		
		return dm.doWithoutResult("update stock set quantity = quantity - " + quantity + " where barcode = '" + barcode + "'");
	}
	
	
	public int inquiryStockQuantity(int barcode) {
		ResultSet rs = null;
		int quantity = 0;
		rs = dm.doWithResult("SELECT quantity from stock where barcode = '" + barcode + "'");
		try {
			while(rs.next()) {
				
				quantity = rs.getInt(1);
		
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		
		return quantity;
	}
	
	public Stock inquiryStockinfo(int productid) {
		ResultSet rs = null;
		
		rs = dm.doWithResult("SELECT * FROM stock where productid= '" + productid + "'");
		try {
			while(rs.next()) {
			return new Stock(rs.getInt(1),rs.getInt(2),rs.getDate(3),rs.getDate(4)
					,rs.getInt(5),rs.getInt(6));
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	public Product inquiryProductid(String ProductName) {
		ResultSet rs = null;
		
		rs = dm.doWithResult("SELECT * FROM product where productname = '" + ProductName + "'");
		try {
			while(rs.next()) {
			return new Product(rs.getInt(1),rs.getString(2),rs.getInt(3),rs.getInt(4),rs.getInt(5));
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	public Sale inquirySale(int SaleID) {
		ResultSet rs = null;
		
		rs = dm.doWithResult("SELECT * FROM sale where saleid = '" + SaleID + "'");
		try {
			while(rs.next()) {
			return new Sale(rs.getInt(1),rs.getInt(2),rs.getDate(3),rs.getInt(4));
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	public Product inquiryProductname(int productid) {
		ResultSet rs = null;
		
		rs = dm.doWithResult("SELECT * FROM product where productid = '" + productid + "'");
		try {
			while(rs.next()) {
			return new Product(rs.getInt(1),rs.getString(2),rs.getInt(3),rs.getInt(4),rs.getInt(5));
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	public Vector<SalesProduct> inquirySalesProduct(int SaleID) {
		ResultSet rs = null;
		Vector<SalesProduct> salesproduct = new Vector();
		rs = dm.doWithResult("SELECT * FROM salesproduct where saleid = '" + SaleID + "'");
		try {
			while(rs.next()) {
				salesproduct.add(new SalesProduct(rs.getInt(1),rs.getInt(2),rs.getInt(3)));
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return salesproduct;
	}
	public Vector<Sale> inquiryAllSale() {
		ResultSet rs = null;
		Vector<Sale> saleinfo = new Vector();
		rs = dm.doWithResult("SELECT * FROM sale;");
		try {
			while(rs.next()) {
			saleinfo.add(new Sale(rs.getInt(1),rs.getInt(2),rs.getDate(3),rs.getInt(4)));
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return saleinfo;
	}
	
	 public boolean isDngrProduct(int productID) { //DngrProduct : 위험약품
	      int chk = 0;
	      String query;
	      query = "SELECT dngrProduct FROM product WHERE productID = '" + productID +"';";
	      ResultSet rs = dm.doWithResult(query);

	      try {
	         while(rs.next()) {
	        	 
	            chk = rs.getInt("dngrProduct");
	            System.out.println(chk);
	         }
	         dm.closeDB();         
	         
	         if(chk == 0) return false;         
	      } catch (SQLException e) {
	         // TODO Auto-generated catch block
	         e.printStackTrace();
	      }
	      return true;
	   }
	
	
	
	
	
}
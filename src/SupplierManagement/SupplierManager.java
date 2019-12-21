package SupplierManagement;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.JOptionPane;

import DBManagement.DBManager;

//거래처 관리 컨트롤 클래스

public class SupplierManager {
	DBManager dm = new DBManager();
	
	public int getSupplierID(String name) {
		int supplierID = 0;
		
		try {
			ResultSet rs=dm.doWithResult("SELECT * From Supplier where supplierName='"+name+"';");
			while(rs.next()) {
				supplierID=rs.getInt("supplierID");
			}
			dm.closeDB();
		}
		catch(SQLException e) {	
			e.printStackTrace();
	}
		return supplierID;
	}
	
	public String getSupplierName(int ID) {
		String supplierName = "";
		
		try {
			ResultSet rs=dm.doWithResult("SELECT * From Supplier where supplierID='"+ID+"';");
			while(rs.next()) {
				supplierName=rs.getString("supplierName");
			}
			dm.closeDB();
		}
		catch(SQLException e) {	
			e.printStackTrace();
	}
		return supplierName;
	}
	
	public Boolean RegisterSupplier(Supplier supplierInfo) {
		//거래처 등록 
		return dm.doWithoutResult("INSERT INTO Supplier(supplierName, supplierTel, supplierPersonalDay, supplierProduct, productPrice) "
				+"VALUES('"+supplierInfo.getSupplierName()+"','"+supplierInfo.getSupplierTel()+"','"+new SimpleDateFormat("yyyy-MM-dd").format(supplierInfo.getSupplierPersonalDay())
				+"','"+supplierInfo.getSupplierProduct()+"','"+supplierInfo.getProductPrice()+"');");
	}
	
	public Boolean updateSupplier(String ownSupplierName, String supplierName, String supplierTel, Date supplierPersonalDay,
			String supplierProduct, int ProductPrice) {
		//거래처 수정
		return dm.doWithoutResult("UPDATE supplier set supplierName='"+ supplierName
			+"', supplierTel='"+supplierTel
			+"', supplierPersonalDay='"+new SimpleDateFormat("yyyy-MM-dd").format(supplierPersonalDay)
			+"', supplierProduct='"+supplierProduct+"', productPrice='"+ProductPrice
			+"' where supplierID='"+new SupplierManager().getSupplierID(ownSupplierName)+"';");
	}
	
	public ArrayList<Supplier> inquirySupplier(String SupplierProduct) {
		//거래처 조회
		//"SELECT * From Receiving where productID='"+inputName+"';"
		//
		ResultSet rs=null;
		ArrayList<Supplier> r=new ArrayList<>();
		
		if(SupplierProduct.equals("")) {
			try {
				rs=dm.doWithResult("SELECT * From Supplier;");
				while(rs.next()) {
					r.add(new Supplier(rs.getInt("supplierID"), rs.getString("supplierName")
							, rs.getString("supplierTel"), rs.getDate("supplierPersonalDay")
							, rs.getString("supplierProduct"), rs.getInt("productPrice")));
				}

				dm.closeDB();
			}
			catch(SQLException e) {	
					e.printStackTrace();
			}
		}
		else {
			try {
				rs=dm.doWithResult("SELECT * From Supplier where supplierID='"+SupplierProduct+"';");
				while(rs.next()) {
					r.add(new Supplier(rs.getInt("supplierID"), rs.getString("supplierName")
							, rs.getString("supplierTel"), rs.getDate("supplierPersonalDay")
							, rs.getString("supplierProduct"), rs.getInt("productPrice")));

				}

				dm.closeDB();
			}
			catch(SQLException e) {	
					e.printStackTrace();
			}
			
		}
		return r;
	}
	
	public Boolean deleteSupplier(String supplierName){
		//거래처 삭제
		//"delete from receiving where productid='"+productID+"' AND receivingdate='"+new SimpleDateFormat("yyyy-MM-dd").format(receivingDate)+"';"
		System.out.println(1);
		System.out.println(supplierName);
		return dm.doWithoutResult("delete from Supplier where supplierName='"+ supplierName+"';");
	}
}
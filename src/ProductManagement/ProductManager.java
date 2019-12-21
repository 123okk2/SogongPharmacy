package ProductManagement;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import DBManagement.DBManager;
import RecordManagement.*;

public class ProductManager {
	DBManager dm=new DBManager();

	public boolean registerProduct(Product productInfo) {
		return dm.doWithoutResult("insert into product(productID, productName, productPrice, properStock, dngrProduct)"
				+ " values('"+productInfo.getProductID()+"','"+productInfo.getProductName()+"','"+productInfo.getProductPrice()
				+"','"+productInfo.getProperStock()+"','"+productInfo.getDngrProduct()+"');");
	}
	//���蹮���� ���� �Ķ���ͷ� date �߰�
	public boolean deleteProduct(int productID) {
		//��� ���� ����
		return dm.doWithoutResult("delete from product where productID='"+productID+"';");
	}
	public boolean updateProduct(Product productInfo) {
		//��� ���� ����
		return dm.doWithoutResult("UPDATE product set productName='"+productInfo.getProductName()+"', productPrice='"+productInfo.getProductPrice()
		+"', properStock='"+productInfo.getProperStock()+"', dngrProduct='"+productInfo.getDngrProduct()+"' WHERE productID ='"+productInfo.getProductID()+"';");
	}
	public ArrayList<Product> inquiryProduct(String productNameInput) {
		// TODO Auto-generated method stub
		String query;

		if(productNameInput.equals("")) query = "SELECT * FROM product;";
		else query = "SELECT * From product where productName='"+productNameInput+"';";
		ResultSet rs = dm.doWithResult(query);

		ArrayList<Product> pList = new ArrayList<Product>();
		try {
			while(rs.next()) {
				pList.add(new Product(rs.getInt("productID"), rs.getString("productName"), rs.getInt("productPrice"), rs.getInt("properStock"), rs.getInt("dngrProduct")));
			}
			dm.closeDB();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return pList;
	}
	
	//�����ǰ���� �ƴ��� Ȯ���ϴ� �Լ�
	public boolean isDngrProduct(int productID) { //DngrProduct : �����ǰ
		int chk = 0;
		String query;
		query = "SELECT dngrProduct FROM product WHERE productID = '" + productID +"';";
		ResultSet rs = dm.doWithResult(query);

		try {
			while(rs.next()) {
				chk = rs.getInt("dngrProduct");
			}
			dm.closeDB();			
			
			if(chk == 0) return false;			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}
	
	//productID�� DB�� �������� �Լ�
	public int getProductID(String productName) {
		int ID = 0;
		String query;
		query = "SELECT productID FROM product WHERE productName = '" + productName +"';";
		ResultSet rs = dm.doWithResult(query);
		
		try {
			while(rs.next()) {
				ID = rs.getInt("productID");
			}
			dm.closeDB();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ID;
	}
	public String getProductName(int productID) {
		String Name=null;
		String query;
		query = "SELECT productName FROM product WHERE productID = '" + productID +"';";
		ResultSet rs = dm.doWithResult(query);
		
		try {
			while(rs.next()) {
				Name = rs.getString("productName");
			}
			dm.closeDB();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return Name;
	}
}


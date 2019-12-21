package CustomerManagement;

import java.sql.PreparedStatement; 
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.Date;

import DBManagement.DBManager;

public class CustomerManager {
	private DBManager dm;
	
	public CustomerManager() {
		dm = new DBManager();
	}
	
	public boolean registerCustomer(Customer c) {
		String name = c.getName();
		String phoneNum = c.getPhoneNum();
		java.sql.Date birthdate = c.getBirthdate();
		
		String query = "INSERT INTO customer(name, phoneNum, birthdate) "
				+ "values('" + name + "','" + phoneNum + "','"
				+ birthdate +"')";
		
		boolean result = false;
		try {
			result = dm.doWithoutResult(query);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public boolean updateCustomer(Customer c) {
		String name = c.getName();
		String phoneNum = c.getPhoneNum();
		java.sql.Date birthdate = c.getBirthdate();
		int customerID = c.getCustomerID();
		
		String query = "UPDATE customer SET name = '" + name +"', phoneNum = '"
				+ phoneNum + "', birthdate = '" + birthdate 
				+ "' WHERE customerID = '" + customerID + "'";
		
		boolean result = false;
		try {
			result = dm.doWithoutResult(query);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public ArrayList<Customer> inquiryCustomer(String name, String phoneNum, java.sql.Date birthdate) {
		String query;
		int conditionCnt = 0;
		
		// birthdate == 1900-01-01 일때  getDate() = 1
		if(name.equals("") && phoneNum.equals("") && (birthdate.getDate() == 1)) {
			query = "SELECT * FROM customer";
		}
		
		else {
			query = "SELECT * FROM customer WHERE ";
			
			if(name.equals("") == false) {
				query += "name = '" + name + "'";
				conditionCnt++;
			}
			
			if(phoneNum.equals("") == false) {
				if(conditionCnt > 0) query += " and ";
				query += "phoneNum = '" + phoneNum + "'";
				conditionCnt++;
			}
			
			
			// birthdate == 1900-01-01 일때  getDate() = 1
			if((birthdate.getDate() == 1) == false) {
				if(conditionCnt > 0) query += " and ";
				query += "birthdate = '" + birthdate + "'";
				conditionCnt++;
			}
		}
		
		
		
		/*String query = "SELECT * FROM customer WHERE name = '" + name + "' and phoneNum = '"
				+ phoneNum + "' and birthdate = '" + birthdate + "'";*/
		
		ArrayList<Customer> cList = new ArrayList<Customer>();
		ResultSet rs = dm.doWithResult(query);
		
		try {
			while(rs.next()) {
				cList.add(new Customer(rs.getInt("customerID"), rs.getString("name"), rs.getString("phoneNum"), rs.getDate("birthdate"), rs.getBoolean("interest")));
			}
			dm.closeDB();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return cList;
	}
	
	public boolean deleteCustomer(int customerID) {
		String query = "DELETE FROM customer WHERE customerID = '" + customerID + "'";
		
		boolean result = false;
		try {
			result = dm.doWithoutResult(query);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	/*public int isInterestCustomer(String name, String phoneNum, Date birthdate) {
		String query = "SELECT interest FROM customer WHERE customerID = '" + customerID + "'";
		
		ResultSet rs = null;
		try {
			rs = dm.doWithResult(query);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		
		
		int interest = 0;
		try {
			rs.next();
			
			interest = rs.getInt(1);
			
			dm.closeDB();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return interest;
	}*/
	
	public boolean updateCustomerInterestState(int customerID, int interest) {
		String query = "UPDATE customer SET interest = '" + interest + "' WHERE customerID = '" + customerID + "'";
		
		boolean result = false;
		try {
			result = dm.doWithoutResult(query);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
}

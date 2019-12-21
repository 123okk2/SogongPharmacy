package BaselineDataManagement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import DBManagement.DBManager;

public class SellerManager {
	DBManager dm = new DBManager();

	public Boolean RegisterSeller(Seller s) {
		// 코드추가
		return dm.doWithoutResult("INSERT INTO seller(name,phoneNum) VALUES('" + s.getName() + "','" + s.getPhoneNum() + "')");
	}

	public Boolean updateSeller(Seller s, Seller updateseller) {
		// 코드 수정
		return dm.doWithoutResult("UPDATE seller set name='"+updateseller.getName()+"', phoneNum='"+updateseller.getPhoneNum()+"' where name='"+s.getName()+"';");
				
	}

	public ArrayList<Seller> inquirySeller(String nameInput) {
		if (nameInput.equals("")) {
			ResultSet rs = null;
			ArrayList<Seller> sl = new ArrayList();
			rs = dm.doWithResult("SELECT * FROM seller;");
			try {
				while (rs.next()) {
					sl.add(new Seller(rs.getString("name"), rs.getString("phoneNum")));
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return sl;
			
		}
		else {
			ResultSet rs = null;
			ArrayList<Seller> sl = new ArrayList();
			rs = dm.doWithResult("SELECT * From seller where name rlike'" + nameInput + "';");
			try {
				while (rs.next()) {
					sl.add(new Seller(rs.getString("name"), rs.getString("phoneNum")));
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return sl;
		}
		
	}
	
	public Boolean deleteSeller(String name) {

		return dm.doWithoutResult("delete from seller where name='" + name + "';");
	}

}

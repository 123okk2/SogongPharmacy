package BaselineDataManagement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import DBManagement.DBManager;

public class CodeManager {
	DBManager dm = new DBManager();

	public Boolean RegisterCode(Code c) {
		// 코드추가
		return dm.doWithoutResult("INSERT INTO code(number,name) VALUES('" + c.getNumber() + "','" + c.getName() + "')");
	}

	public Boolean updateCode(Code c, Code updatec) {
		// 코드 수정
		return dm.doWithoutResult("UPDATE code set number='"+updatec.getNumber()+"', name='"+updatec.getName()+"' where number='"+c.getNumber()+"';");
				
	}

	public ArrayList<Code> inquiryCode(String nameInput) {
		
		if (nameInput.equals("")) {
			ResultSet rs = null;
			ArrayList<Code> cl = new ArrayList();
			rs = dm.doWithResult("SELECT * FROM code;");
			try {
				while (rs.next()) {
					cl.add(new Code(rs.getInt("number"), rs.getString("name")));
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return cl;
			
		}
		else {
			ResultSet rs = null;
			ArrayList<Code> cl = new ArrayList();
			rs = dm.doWithResult("SELECT * From code where name rlike '" + nameInput + "';");
			try {
				while (rs.next()) {
					cl.add(new Code(rs.getInt("number"), rs.getString("name")));
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return cl;
		}
		
	}

	public Boolean deleteCode(String name) {

		return dm.doWithoutResult("delete from code where name='" + name + "';");
	}

}

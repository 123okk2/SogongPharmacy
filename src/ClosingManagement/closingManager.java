package ClosingManagement;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import DBManagement.DBManager;
import ProductManagement.ProductManager;
import RecordManagement.ClosingRecord;
import RecordManagement.RecordManager;

public class closingManager {
	private DBManager DB;
	private Date date;

	public closingManager() {
		DB = new DBManager();
	}
	// ���������� DB�� ����ϴ� �Լ�
	public boolean registerClosingInform(int balance) {
		boolean re = true;
		date = new Date();
		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
		ResultSet check = DB.doWithResult("select EXISTS (select * from closingInform where closingDate = '"+format1.format(date.getTime())+"')"); // ���� ��ϵ� ���������� �ִ��� üũ
		try {
			if(check.next())
			{
				if(check.getBoolean("EXISTS (select * from closingInform where closingDate = '"+format1.format(date.getTime())+"')"))return false; // �����ϸ� false return �� �Լ� ����
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		ResultSet rs = DB.doWithResult(
				"select productID, salesdate, SUM(productquantity)productquantity from sale join salesproduct on sale.saleId = salesproduct.saleID where sale.salesdate = '"+ format1.format(date.getTime()) +"' group by productID");
		// DB�� sale�� salesproduct ���̺��� join�� ���� �Ǹŵ� ��ǰ�� ID, �Ǹ���, �Ǹŷ��� �޴µ� group by ID�� ���� ��ǰID�� ���� �Ǹű���� ������ ǰ�� ���� �Ǹŷ��� �˼��ְ���
		ArrayList<salesPerform> list = new ArrayList<salesPerform>();
		try {
			while (rs.next()) {
				int pI = rs.getInt("productID");
				int pq = rs.getInt("productquantity");
				list.add(new salesPerform (date, pI, pq));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for (int i = 0; i < list.size(); i++) {
			re = DB.doWithoutResult(
					"insert into salesPerform(salesDate,productID,salesvolumePerDay) values('"
							+ format1.format(list.get(i).getSalesDate().getTime()) + "'," + list.get(i).getProductID()+ "," + list.get(i).getSalesVolumePerDay() + ")");
			// ������ ���� �Ϻ� ǰ�� �Ǹŷ��� �ǸŽ���(salesPerform) ���̺� ������
			if (!re)
				return false;
		}
		rs = DB.doWithResult("select SUM(productPrice*salesvolumePerDay) as sale from product join salesperform on product.productId = salesperform.productID where salesDate = '"+format1.format(date.getTime())+"'");
		// ǰ�� �Ϻ� �Ǹŷ��� �����Ͽ� �Ϻ� (��)������� �����س�
		try {
			rs.next();
			DB.doWithoutResult("insert into closingInform(closingDate,salesPerDay,balance) values('"+ format1.format(date.getTime()) + "'," + rs.getInt("sale") + "," + balance + ")");
			// ��������� ��û�� ��¥, �����س� �Ϻ� �����, parameter�� ���� �ܾ��� �������� ���̺� �����
			new RecordManager().registerRecord(5, new ClosingRecord(0, date, rs.getInt("sale"), balance)); // �����̷� ����
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return re;
	}
	
// �Ϻ�(���Ϻ���)��ȸ �� ������ String 2���� �迭 ( ���̺� �����ϱ� ���� ) �� ��ȯ
	public String[][] inquiryDailyReport() {
		date = new Date();
		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
		ResultSet rs = DB.doWithResult("select salesperform.productID, salesvolumePerDay, productPrice*salesvolumePerDay as sale from product join salesperform on product.productId = salesperform.productID where salesDate ='" + format1.format(date.getTime()) + "' order by sale desc");
		// salesPerform ���̺�� product ���̺� ������ ���� sale ���̺� ������ �Ǹŵ� ��ǰ�� ������ ������ ǰ�� ID, �Ǹŷ�, �Ǹŷ�*������ ��ȯ���� ( �� �Ǹž� �������� )
		ArrayList<String[]> list = new ArrayList<String[]>();
		try {
			while (rs.next()) {
				String pID = Integer.toString(rs.getInt("productID"));
				String svPD = Integer.toString(rs.getInt("salesvolumePerDay"));
				String s = Integer.toString(rs.getInt("sale"));
				list.add(new String[] {pID, svPD, s});
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String[][] relist = new String[list.size()][];
		ProductManager PM = new ProductManager();
		for (int i = 0; i < list.size(); i++) {
				relist[i] = new String[] { PM.getProductName(Integer.parseInt(list.get(i)[0])), list.get(i)[1], list.get(i)[2] };
		// String 2���� �迭�� �ະ�� ' ��ǰ��, �Ǹŷ�, ����� ' �����͸� ����
		}
		try {
			rs.close();
			DB.closeDB();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return relist;
	}

	public String[][] inquiryMonthlyReport() {
		date = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.getActualMinimum(Calendar.DAY_OF_MONTH)); // ����� ù��
		Date startDay = calendar.getTime(); 
		calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.getActualMaximum(calendar.DAY_OF_MONTH)); // ����� ��������
		Date endDay = calendar.getTime(); 
		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
		ResultSet rs = DB.doWithResult("select salesperform.productID, SUM(salesvolumePerDay)salesvolumePerDay, SUM(productPrice*salesvolumePerDay)sale from product join salesperform on product.productId = salesperform.productID where salesDate >= '" + format1.format(startDay) + "' and salesDate <= '"+format1.format(endDay)+"' group by productID order by sale desc");
		// salesPerform ���̺�� product ���̺��� join �Ͽ� ���� ù���� �������� ������ ��ϵ� ������� �����͵��� productID�� �������� �Ǹŷ��� ������ product ���̺��� ���ݰ� ���� ���� ������� ������������ ������
		ArrayList<String[]> list = new ArrayList<String[]>();
		try {
			while (rs.next()) {
				String pID = Integer.toString(rs.getInt("productID"));
				String svPD = Integer.toString(rs.getInt("salesvolumePerDay"));
				String s = Integer.toString(rs.getInt("sale"));
				list.add(new String[] {pID, svPD, s});
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String[][] relist = new String[list.size()][];
		ProductManager PM = new ProductManager();
		for (int i = 0; i < list.size(); i++) {
				relist[i] = new String[] { PM.getProductName(Integer.parseInt(list.get(i)[0])), list.get(i)[1], list.get(i)[2] };
				// �Ϻ���ȸ���� �����ϰ� ' ��ǰ��, (���)�Ǹŷ�, (���)����� ' �� String 2���� �迭�� �ึ�� ����
		}
		try {
			rs.close();
			DB.closeDB();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return relist;
	}
	public boolean isClosed()
	{		
		date = new Date();
		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
		ResultSet check = DB.doWithResult("select EXISTS (select * from closingInform where closingDate = '"+format1.format(date.getTime())+"')");
		try {
			if(check.next())
			{
				if(check.getBoolean("EXISTS (select * from closingInform where closingDate = '"+format1.format(date.getTime())+"')"))return true; // �����ϸ� false return �� �Լ� ����
			}
			else return false;
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return false;
	}
}

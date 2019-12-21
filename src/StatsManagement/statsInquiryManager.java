package StatsManagement;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

import ClosingManagement.closingInform;
import ClosingManagement.salesPerform;
import DBManagement.DBManager;
import ProductManagement.ProductManager;

public class statsInquiryManager {
	private DBManager DB;
	private SimpleDateFormat format1;

	public statsInquiryManager() {
		DB = new DBManager();
		format1 = new SimpleDateFormat("yyyy-MM-dd");
	}

	// startDay ���� endDay���� ������� ( ��ǻ� �Ϻ� �������� ��ȸ )�� ��ȸ��.
	public String[][] inquirySalesPerform(Date startDay, Date endDay)
	{
		ResultSet rs = DB.doWithResult("select * from closingInform where closingDate >= '"+format1.format(startDay.getTime())+"' and closingDate <= '"+format1.format(endDay.getTime())+"'");
		ArrayList<closingInform> list = new ArrayList<closingInform>();
		try {
			while(rs.next())
			{
				Date cD = rs.getDate("closingDate");
				int sPD = rs.getInt("salesPerDay");
				int b = rs.getInt("balance");
				list.add(new closingInform(cD,sPD,b));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String[][] relist = new String[list.size()][];
		for(int i=0;i<list.size();i++)
		{
			relist[i] = new String[] { format1.format(list.get(i).getClosingDate()), Integer.toString(list.get(i).getSalesPerDay()), Integer.toString(list.get(i).getBalance()) };
		// '������, �����, �ܾ�'�� String 2���� �迭�� ���� 
		}
		return relist;
	}

	public String[][] inquiryMajorPerform(Date startDay, Date endDay) {
		ProductManager PM = new ProductManager();
		ResultSet rs = DB.doWithResult("select salesperform.productID, SUM(salesvolumePerDay)salesvolumePerDay, SUM(productPrice*salesvolumePerDay)sale from product join salesperform on product.productId = salesperform.productID where salesDate >= '"+format1.format(startDay.getTime())+"' and salesDate <= '"+format1.format(endDay.getTime())+"' group by productID order by sale desc");
		// �Ǹŷ�*��ǰ�ݾ��� ���� ���� ���� ���� product ���̺�� salesPerform ���̺��� �����Ͽ� startDay ���� endDay������ salesPerform�� ������ �޾ƿ� �Ⱓ �� ��ǰ���� �Ǹŷ�, �Ǹűݾ��� �����س� �������� ����
		ArrayList<String[]> list = new ArrayList<String[]>();
		try {
			while(rs.next())
			{
				String pI = Integer.toString(rs.getInt("productID"));
				String svPD = Integer.toString(rs.getInt("salesvolumePerDay"));
				String s = Integer.toString(rs.getInt("sale"));
				list.add(new String[] {pI, svPD, s});
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		rs = DB.doWithResult("select COUNT(saleID) from sale where salesDate >= '"+format1.format(startDay.getTime())+"' and salesDate <= '"+format1.format(endDay.getTime())+"'");
		// startDay�� endDay�� �Ⱓ ������ saleID�� �����, �� �ǸŰ� ��� �Ͼ���� = �Ⱓ�� �湮�� ���� ������� count��.
		int count = 0;
		try {
			rs.next();
			count = rs.getInt("COUNT(saleID)");
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String[][] relist = new String[list.size()+1][]; // �迭�� ���������� �� �湮�ڼ��� �ֱ����� ���� ������� +1��
		for (int i = 0; i < list.size(); i++) {
				relist[i] = new String[] { PM.getProductName(Integer.parseInt(list.get(i)[0])) , list.get(i)[1], list.get(i)[2] };
		// ' ��ǰ��, �Ǹŷ�, �Ǹűݾ� '�� String 2���� ��Ŀ� �ະ�� ����
		}
		relist[list.size()] = new String[] { Integer.toString(count) };
		// String 2���� �迭�� ������ �࿡ �ѹ湮�ڼ� count�� ����
		try {
			rs.close();
			DB.closeDB();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return relist;
	}

	public String[][] inquiryRealtimeDashboard() {
		ProductManager PM = new ProductManager();
		Date date = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_WEEK_IN_MONTH)+1); // ������ ù�� (������) ����
		Date WstartDay = calendar.getTime();
		calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_WEEK_IN_MONTH)+8); // ������ �������� (�Ͽ���) ����
		Date WendDay = calendar.getTime();
		calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.getActualMinimum(Calendar.DAY_OF_MONTH)); // �̴��� ù�� ����
		Date MstartDay = calendar.getTime();
		calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.getActualMaximum(calendar.DAY_OF_MONTH)); // �̴��� �������� ����
		Date MendDay = calendar.getTime();
		
		String querySOD = "select SUM(productPrice*productquantity) as sale from ( select productID, salesdate, SUM(productquantity)productquantity from sale join salesproduct on sale.saleId = salesproduct.saleID where sale.salesdate = '"+format1.format(date.getTime())+"' group by productID )result join product on result.productID = product.productID";
		// ���������� ���� sale�� salesproduct ���̺��� join�Ͽ� (productID, salesDate, productquantity) ���̺��� ������ �̸� product ���̺�� join ���� productquantity�� productPrice�� ���� ������ ��� SUM�� ��, �� ������ �ǽð� ������� ��ȸ��
		String querySOW = "select SUM(productPrice*salesvolumePerDay) as sale from salesPerform join product on salesperform.productID = product.productID where salesDate >= '"+format1.format(WstartDay)+"' and salesDate <= '"+format1.format(WendDay)+"' and not salesDate in ('"+format1.format(date.getTime())+"')";
		// ������ �����ص� ���� �����ϰ� ���� �������� ������ salesPerform�� product ���̺��� join�Ͽ� �Ⱓ���� salesPerform�� productID�� ���� grouping �ϰ� ��ǰ�� price�� productquantity�� ���� ������ ��� SUM �Ѱ�, �� ������ �Ǹž��� ��ȸ�ؿ�
		// �̶� where������ not in �� ���� ������ salesperform column���� �������� �ʴµ�, �̴� ���� ������ �ǽð� ��ú��带 �̿��Ұ�� sale ���̺� ���� ���� �ߺ��Ǿ� ���� ������� �ߺ����� �������� �ֱ� ���� ( �̴� �̴��� ����� ��ȸ�ÿ��� ���� )
		String querySOM = "select SUM(productPrice*salesvolumePerDay) as sale from salesPerform join product on salesperform.productID = product.productID where salesDate >= '"+format1.format(MstartDay)+"' and salesDate <= '"+format1.format(MendDay)+"' and not salesDate in ('"+format1.format(date.getTime())+"')";
		// ������ �����ص� ���� �����ϰ� ���� �������� ������ sales.... �̵��� ������ ������ ����װ� ����
		String queryTop5 = "select productID, SUM(productquantity)productquantity from (select productID ,SUM(salesvolumePerDay)productquantity from salesPerform where salesDate >= '"+format1.format(MstartDay)+"' and salesDate <= '"+format1.format(MendDay)+"' and not salesDate in ('"+format1.format(date.getTime())+"') group by productID union select productID, SUM(productquantity)productquantity from sale join salesproduct on sale.saleId = salesproduct.saleID where sale.salesdate = '"+format1.format(date.getTime())+"' group by productID)result group by productID order by productquantity desc limit 5";
		// �̴��� �ִ� �Ǹ�ǰ�� Top5�� ��ȸ��, 1:salesPerform ���̺��� ��� ù ��~ ��� ������ �������� ( �� ���� �����ʹ� not in���� ���� ) �����͵� productID�� �����͵� grouping �� productquantity ������ ���̺� ����, 2:sale ���̺�� salesproduct ���̺��� join�ϰ�, salesdate�� ������ column�� productID�� �����͵� grouping �� productquantity ��ħ , 3:�� �����̺��� union �Ͽ� ����(�ǽð�) + �̴��� ǰ�� �Ǹŷ��� ��ȸ�ϰ� �Ǹŷ� ������������ ���� ���� 5���� ��ȸ��
		int saleOfDay, saleOfWeek, saleOfMonth = 0;
		ResultSet rs = DB.doWithResult(querySOD);
		String[][] relist = new String[8][]; // ������ �����, ������ �����, �̴��� �����  + �ִ��Ǹ�ǰ�� Top5�� ���� 5ĭ�� ����
		try {
			rs.next();
			saleOfDay = rs.getInt("sale");
			rs = DB.doWithResult(querySOW);
			rs.next();
			saleOfWeek = rs.getInt("sale")+saleOfDay; // ������ ����� ( ���� ���� ) + ������ ����� ( �ǽð� )
			rs = DB.doWithResult(querySOM);
			rs.next();
			saleOfMonth = rs.getInt("sale")+saleOfDay; // �̴��� ����� ( ���� ���� ) + ������ �����  ( �ǽð� )
			relist[0] = new String[] { Integer.toString(saleOfDay) };
			relist[1] = new String[] { Integer.toString(saleOfWeek) };
			relist[2] = new String[] { Integer.toString(saleOfMonth) };
			rs = DB.doWithResult(queryTop5);
			int count = 3;
			while(rs.next()){
					relist[count++] = new String[] { PM.getProductName(rs.getInt("productID")), Integer.toString(rs.getInt("productquantity"))};
			}
			if(count!=7)
			{
				for(;count<relist.length;count++)
					relist[count] = new String[] {" ", " "};
			}
			DB.closeDB();
			rs.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return relist;
	}
}

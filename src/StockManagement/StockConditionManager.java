package StockManagement;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import DBManagement.DBManager;
import ProductManagement.ProductManager;

public class StockConditionManager {

	DBManager dm=new DBManager();
	
	public ArrayList<StockCondition> StockinquiryStockConditionList(String nameInput) {
		//��ü ��� ��ȸ
		ResultSet rs=null;
		ArrayList<StockCondition> sc=new ArrayList<>();
		if(nameInput.equals("")) {
			try {
				rs=dm.doWithResult("SELECT * FROM STOCKCONDITION;");
				while(rs.next()) {
					sc.add(new StockCondition(rs.getInt("productid"), rs.getInt("quantity"), rs.getInt("properstock")));
				}
				dm.closeDB();
			}
			catch(SQLException e) {	
				e.printStackTrace();
			}
		}
		else {
			try {
				rs=dm.doWithResult("SELECT * FROM STOCKCONDITION WHERE productID='"+new ProductManager().getProductID(nameInput)+"';");
				while(rs.next()) {
					sc.add(new StockCondition(rs.getInt("productid"), rs.getInt("quantity"), rs.getInt("properstock")));
				}
				dm.closeDB();
			}
			catch(SQLException e) {	
				e.printStackTrace();
			}
			if(sc.size()==0) JOptionPane.showMessageDialog(null, "�˻� ���ǿ� ��ġ�ϴ� �׸��� �������� �ʽ��ϴ�.", "�׸� ������", JOptionPane.INFORMATION_MESSAGE);
		}
		return sc;
	}
	public ArrayList<StockCondition> inquiryLessStockConditionList(String nameInput) {
		//���� ��� �̸� ��ȸ
		ResultSet rs=null;
		ArrayList<StockCondition> sc=new ArrayList<>();
		if(nameInput.equals("")) {
			try {
				rs=dm.doWithResult("SELECT * FROM STOCKCONDITION WHERE quantity < properstock;");
				while(rs.next()) {
					sc.add(new StockCondition(rs.getInt("productid"), rs.getInt("quantity"), rs.getInt("properstock")));
				}
				dm.closeDB();
			}
			catch(SQLException e) {	
				e.printStackTrace();
			}
		}
		else {
			try {
				rs=dm.doWithResult("SELECT * FROM STOCKCONDITION WHERE productID='"+new ProductManager().getProductID(nameInput)+"' and quantity < properstock;");
				while(rs.next()) {
					sc.add(new StockCondition(rs.getInt("productid"), rs.getInt("quantity"), rs.getInt("properstock")));
				}
				dm.closeDB();
			}
			catch(SQLException e) {	
				e.printStackTrace();
			}
			if(sc.size()==0) JOptionPane.showMessageDialog(null, "�˻� ���ǿ� ��ġ�ϴ� �׸��� �������� �ʽ��ϴ�.", "�׸� ������", JOptionPane.INFORMATION_MESSAGE);
		}
		return sc;
	}
	//�Ķ���� ����. productName ����
	public Boolean registerStockCondition(StockCondition stockConditionInfo) {
		//��� ���
		return dm.doWithoutResult("insert into stockcondition(productid, properstock)"
				+ " values('"+stockConditionInfo.getProductID()+"','"+stockConditionInfo.getProperStock()+"');");
	}
	public Boolean updateStockCondition(String productName, StockCondition stockConditionInfo) {
		//��� ����
		return dm.doWithoutResult("update stockcondition set quantity='"+stockConditionInfo.getQuantity()+"', productID='"+stockConditionInfo.getProductID()
		+"', properstock='"+stockConditionInfo.getProperStock()+"' where productID='"+new ProductManager().getProductID(productName));
	}
	public boolean updateStockCondition(StockCondition stockConditionInfo) {
	      //��� ���� (��ǰ ������)
	      return dm.doWithoutResult("update stockcondition set properstock='"+stockConditionInfo.getProperStock()+"' where productID='"+stockConditionInfo.getProductID()+"';");
	}
	public Boolean deleteStockCondition(String productName) {
		//��� ����
		return dm.doWithoutResult("delete from Stockcondition where productID='"+new ProductManager().getProductID(productName)+"';");
	}
	//��� �߰�/���ҽ� ���. �Լ� �߰�
	public Boolean updateStockCondition(String productName, int cnt) {
		return dm.doWithoutResult("update stockcondition set quantity=quantity+"+cnt+" where productID='"+new ProductManager().getProductID(productName)+"';");
	}
}
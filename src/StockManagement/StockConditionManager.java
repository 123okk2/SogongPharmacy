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
		//전체 재고 조회
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
			if(sc.size()==0) JOptionPane.showMessageDialog(null, "검색 조건에 일치하는 항목이 존재하지 않습니다.", "항목 미존재", JOptionPane.INFORMATION_MESSAGE);
		}
		return sc;
	}
	public ArrayList<StockCondition> inquiryLessStockConditionList(String nameInput) {
		//적정 재고 미만 조회
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
			if(sc.size()==0) JOptionPane.showMessageDialog(null, "검색 조건에 일치하는 항목이 존재하지 않습니다.", "항목 미존재", JOptionPane.INFORMATION_MESSAGE);
		}
		return sc;
	}
	//파라미터 변경. productName 삭제
	public Boolean registerStockCondition(StockCondition stockConditionInfo) {
		//재고 등록
		return dm.doWithoutResult("insert into stockcondition(productid, properstock)"
				+ " values('"+stockConditionInfo.getProductID()+"','"+stockConditionInfo.getProperStock()+"');");
	}
	public Boolean updateStockCondition(String productName, StockCondition stockConditionInfo) {
		//재고 수정
		return dm.doWithoutResult("update stockcondition set quantity='"+stockConditionInfo.getQuantity()+"', productID='"+stockConditionInfo.getProductID()
		+"', properstock='"+stockConditionInfo.getProperStock()+"' where productID='"+new ProductManager().getProductID(productName));
	}
	public boolean updateStockCondition(StockCondition stockConditionInfo) {
	      //재고 수정 (상품 수정시)
	      return dm.doWithoutResult("update stockcondition set properstock='"+stockConditionInfo.getProperStock()+"' where productID='"+stockConditionInfo.getProductID()+"';");
	}
	public Boolean deleteStockCondition(String productName) {
		//재고 삭제
		return dm.doWithoutResult("delete from Stockcondition where productID='"+new ProductManager().getProductID(productName)+"';");
	}
	//재고 추가/감소시 사용. 함수 추가
	public Boolean updateStockCondition(String productName, int cnt) {
		return dm.doWithoutResult("update stockcondition set quantity=quantity+"+cnt+" where productID='"+new ProductManager().getProductID(productName)+"';");
	}
}
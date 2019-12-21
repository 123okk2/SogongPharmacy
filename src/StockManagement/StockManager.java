package StockManagement;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.JOptionPane;

import ProductManagement.ProductManager;
import DBManagement.DBManager;
import RecordManagement.*;

public class StockManager {

	RecordManager rmr=new RecordManager();
	DBManager dm=new DBManager();

	public Stock getStock(int barcode) {
		Stock s;
		ResultSet rs=null;
		try {
			rs=dm.doWithResult("SELECT * FROM STOCK WHERE barcode = '"+barcode+"';");
			if(rs.next()) {
			s=new Stock(rs.getInt("barcode"), rs.getInt("quantity")
					,rs.getDate("receivingDate"), rs.getDate("expirationdate")
					,rs.getInt("supplierID"), rs.getInt("productID"));
			dm.closeDB();
			return s;
			}
		}
		catch(SQLException e) {	
			e.printStackTrace();
		}
		return new Stock(0,0,new Date(),new Date(),0,0);
	}
	
	public ArrayList<Stock> inquiryExpireSoonStockList(String nameInput) {
		//������� �ӹڻ�ǰ ��ȸ

		Date d=new Date(new Date().getYear(), new Date().getMonth()+1, new Date().getDay()+12);
		String now2=new SimpleDateFormat("yyyy-MM-dd").format(d);
		System.out.println(now2);
		
		ResultSet rs=null;
		ArrayList<Stock> s=new ArrayList<>();
		if(nameInput.equals("")) {
			try {
				if(nameInput.equals("")) {
					rs=dm.doWithResult("SELECT * FROM STOCK WHERE expirationdate < '"+now2+"';");
					while(rs.next()) {
						System.out.println(rs.getDate("receivingDate"));
						s.add(new Stock(rs.getInt("barcode"), rs.getInt("quantity")
								,rs.getDate("receivingDate"), rs.getDate("expirationdate")
								,rs.getInt("supplierID"), rs.getInt("productID")));
					}
					dm.closeDB();
				}
			}
			catch(SQLException e) {	
				e.printStackTrace();
			}
		}
		else {
			try {
				if(nameInput.equals("")) {
					rs=dm.doWithResult("SELECT * FROM STOCK WHERE expirationdate < '"+now2+"' and quantity < properstock;");
					while(rs.next()) {
						System.out.println(rs.getDate("receivingDate"));
						s.add(new Stock(rs.getInt("barcode"), rs.getInt("quantity")
								,rs.getDate("receivingDate"), rs.getDate("expirationdate")
								,rs.getInt("supplierID"), rs.getInt("productID")));
					}
					dm.closeDB();
				}
			}
			catch(SQLException e) {	
				e.printStackTrace();
			}
			if(s.size()==0) JOptionPane.showMessageDialog(null, "�˻� ���ǿ� ��ġ�ϴ� �׸��� �������� �ʽ��ϴ�.", "�׸� ������", JOptionPane.INFORMATION_MESSAGE);
		}
		
		return s;
	}
	public Boolean registerStock(Stock stockInfo) {
		//��� ���� ���
		StockRecord stockRecord = new StockRecord(Record.REGISTER, new ProductManager().getProductName(stockInfo.getProductID()), stockInfo.getExpirationDate());
		boolean chk = rmr.registerRecord(RecordManager.STOCK_RECORD, stockRecord);
		if(chk==false) return chk;
		
		return dm.doWithoutResult("insert into stock(barcode, quantity, receivingdate, expirationdate, supplierid, productid)"
				+ " values('"+stockInfo.getBarcode()+"','"+stockInfo.getQuantity()
				+"','"+new SimpleDateFormat("yyyy-MM-dd").format(stockInfo.getReceivingDate())+"','"+new SimpleDateFormat("yyyy-MM-dd").format(stockInfo.getExpirationDate())
				+"','"+stockInfo.getSupplierID()+"','"+stockInfo.getProductID()+"');");
	}
	//���蹮���� ���� �Ķ���ͷ� date �߰�
	public Boolean deleteStock(int productID, int barcode, Date receivingDate, Date expirationDate) {
		//��� ���� ����
		//�̷� ��� ������ �Ķ���� ����
		StockRecord stockRecord = new StockRecord(Record.DELETE, new ProductManager().getProductName(productID), expirationDate);
		boolean chk = rmr.registerRecord(RecordManager.STOCK_RECORD, stockRecord);
		if(chk==false) return chk;
		
		return dm.doWithoutResult("delete from Stock where barcode='"+barcode+"' and receivingDate='"+new SimpleDateFormat("yyyy-MM-dd").format(receivingDate)+"';");
	}
	public Boolean updateStock(int barcode, Date receivingDate, Stock stockInfo) {
		//��� ���� ����
		StockRecord stockRecord = new StockRecord(Record.UPDATE, new ProductManager().getProductName(stockInfo.getProductID()), stockInfo.getExpirationDate());
		boolean chk = rmr.registerRecord(RecordManager.STOCK_RECORD, stockRecord);
		if(chk==false) return chk;
		
		
		return dm.doWithoutResult("UPDATE stock set barcode='"+stockInfo.getBarcode()+"', quantity='"+stockInfo.getQuantity()
		+"', receivingdate='"+new SimpleDateFormat("yyyy-MM-dd").format(stockInfo.getReceivingDate())+"', expirationDate='"+new SimpleDateFormat("yyyy-MM-dd").format(stockInfo.getExpirationDate())
		+"', barcode='"+stockInfo.getBarcode()+"', productID='"+stockInfo.getProductID()+"', supplierID='"
		+stockInfo.getSupplierID()+"' where barcode='"+barcode+"' and receivingDate='"+new SimpleDateFormat("yyyy-MM-dd").format(receivingDate)+"';");
	}

}
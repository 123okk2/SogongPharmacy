package RecordManagement;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import DBManagement.DBManager;

public class RecordManager {
	public final static int PRODUCT_RECORD = 0;
	public final static int RECEIVING_RECORD = 1;
	public final static int SALE_RECORD = 2;
	public final static int STOCK_RECORD = 3;
	public final static int RETURN_RECORD = 4;
	public final static int CLOSING_RECORD = 5;
	
	DBManager dm = new DBManager();
	
	public boolean registerRecord(int value, Record record) { // 이력 등록
		String query = "INSERT INTO ";
		switch(value) {
		case 0: // 상품 이력
			query += "ProductRecord(managementID, productID, productName, recordTime) VALUES ("
					+ record.getManagementID()+","
					+ ((ProductRecord)record).getProductID()+",'"
					+ ((ProductRecord)record).getProductName()+"','"
					+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(record.getRecordTime())+"')";
			break;
		case 1: // 입고 이력
			query += "ReceivingRecord(managementID, productName, quantity, receivingDate, recordTime) VALUES ("
					+ record.getManagementID()+",'"
					+ ((ReceivingRecord)record).getProductName()+"',"
					+ ((ReceivingRecord)record).getQuantity()+",'"
					+ new SimpleDateFormat("yyyy-MM-dd").format(((ReceivingRecord)record).getReceivingDate())+"','"
					+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(record.getRecordTime())+"')";
			break;
		case 2: // 판매 이력
			query += "SaleRecord(managementID, productName, quantity, recordTime) VALUES ("
					+ record.getManagementID()+",'"
					+ ((SaleRecord)record).getProductName()+"',"
					+ ((SaleRecord)record).getQuantity()+",'"
					+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(record.getRecordTime())+"')";
			break;
		case 3: // 재고 이력
			query += "StockRecord(managementID, productName, expirationDate, recordTime) VALUES ("
					+ record.getManagementID()+",'"
					+ ((StockRecord)record).getProductName()+"','"
					+ new SimpleDateFormat("yyyy-MM-dd").format(((StockRecord)record).getExpirationDate())+"','"
					+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(record.getRecordTime())+"')";
			break;
		case 4: // 반품 이력
			query += "ReturnRecord(managementID, productName, supplierName, recordTime) VALUES ("
					+ record.getManagementID()+",'"
					+ ((ReturnRecord)record).getProductName()+"','"
					+ ((ReturnRecord)record).getSupplierName()+"','"
					+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(record.getRecordTime())+"')";
			break;
		case 5: // 마감 이력
			query += "ClosingRecord(managementID, date, salesPerDay, balance, recordTime) VALUES ("
					+ record.getManagementID()+",'"
					+ new SimpleDateFormat("yyyy-MM-dd").format(((ClosingRecord)record).getDate())+"',"
					+ ((ClosingRecord)record).getSalesPerDay()+","
					+ ((ClosingRecord)record).getBalance()+",'"
					+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(record.getRecordTime())+"')";
			break;
		}
		
		return dm.doWithoutResult(query);
	}
	
	public boolean deleteRecord() { // 이력 삭제
		String query;
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.YEAR, -5);
		Date minDate = calendar.getTime();
		query = "DELETE FROM ProductRecord WHERE recordTime<'"+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(minDate)+"'";
		if(!dm.doWithoutResult(query)) return false;
		query = "DELETE FROM ReceivingRecord WHERE recordTime<'"+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(minDate)+"'";
		if(!dm.doWithoutResult(query)) return false;
		query = "DELETE FROM SaleRecord WHERE recordTime<'"+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(minDate)+"'";
		if(!dm.doWithoutResult(query)) return false;
		query = "DELETE FROM StockRecord WHERE recordTime<'"+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(minDate)+"'";
		if(!dm.doWithoutResult(query)) return false;
		query = "DELETE FROM ReturnRecord WHERE recordTime<'"+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(minDate)+"'";
		if(!dm.doWithoutResult(query)) return false;
		query = "DELETE FROM ClosingRecord WHERE recordTime<'"+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(minDate)+"'";
		if(!dm.doWithoutResult(query)) return false;
		
		return true;
	}
	
	public Record[] inquiryRecordList(int value, Date term1, Date term2) throws SQLException { // 이력 조회
		deleteRecord(); // 5년이 지난 이력 모두 삭제
		
		String query = "SELECT * FROM ";
		ResultSet rs = null;
		Record[] recordList = null;

		switch(value) {
		case 0: // 상품 이력
			query += "ProductRecord WHERE recordTime BETWEEN '"
					+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(term1)+"' AND '"
					+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(term2)+"'";
			rs = dm.doWithResult(query);
			rs.last();
			recordList = new ProductRecord[rs.getRow()]; // resultSet크기 만큼의 recordList 생성
			rs.beforeFirst();
			ProductRecord pdRecord;
			for(int i=0; rs.next(); i++) {
				pdRecord = new ProductRecord(
						rs.getInt("managementID"),
						rs.getInt("productID"),
						rs.getString("productName"));
				pdRecord.setRecordTime(rs.getTimestamp("recordTime"));
				recordList[i] = pdRecord;
			}
			break;
		case 1: // 입고 이력
			query += "ReceivingRecord WHERE recordTime BETWEEN '"
					+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(term1)+"' AND '"
					+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(term2)+"'";
			rs = dm.doWithResult(query);
			rs.last();
			recordList = new ReceivingRecord[rs.getRow()]; // resultSet크기 만큼의 recordList 생성
			rs.beforeFirst();
			ReceivingRecord rcRecord;
			for(int i=0; rs.next(); i++) {
				rcRecord = new ReceivingRecord(
						rs.getInt("managementID"),
						rs.getString("productName"),
						rs.getInt("quantity"),
						rs.getDate("receivingDate"));
				rcRecord.setRecordTime(rs.getTimestamp("recordTime"));
				recordList[i] = rcRecord;
			}
			break;
		case 2: // 판매 이력
			query += "SaleRecord WHERE recordTime BETWEEN '"
					+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(term1)+"' AND '"
					+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(term2)+"'";
			rs = dm.doWithResult(query);
			rs.last();
			recordList = new SaleRecord[rs.getRow()]; // resultSet크기 만큼의 recordList 생성
			rs.beforeFirst();
			SaleRecord slRecord;
			for(int i=0; rs.next(); i++) {
				slRecord = new SaleRecord(
						rs.getInt("managementID"),
						rs.getString("productName"),
						rs.getInt("quantity"));
				slRecord.setRecordTime(rs.getTimestamp("recordTime"));
				recordList[i] = slRecord;
			}
			break;
		case 3: // 재고 이력
			query += "StockRecord WHERE recordTime BETWEEN '"
					+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(term1)+"' AND '"
					+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(term2)+"'";
			rs = dm.doWithResult(query);
			rs.last();
			recordList = new StockRecord[rs.getRow()]; // resultSet크기 만큼의 recordList 생성
			rs.beforeFirst();
			StockRecord stRecord;
			for(int i=0; rs.next(); i++) {
				stRecord = new StockRecord(
						rs.getInt("managementID"),
						rs.getString("productName"),
						rs.getDate("expirationDate"));
				stRecord.setRecordTime(rs.getTimestamp("recordTime"));
				recordList[i] = stRecord;
			}
			break;
		case 4: // 반품 이력
			query += "ReturnRecord WHERE recordTime BETWEEN '"
					+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(term1)+"' AND '"
					+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(term2)+"'";
			rs = dm.doWithResult(query);
			rs.last();
			recordList = new ReturnRecord[rs.getRow()]; // resultSet크기 만큼의 recordList 생성
			rs.beforeFirst();
			ReturnRecord rtRecord;
			for(int i=0; rs.next(); i++) {
				rtRecord = new ReturnRecord(
						rs.getInt("managementID"),
						rs.getString("productName"),
						rs.getString("supplierName"));
				rtRecord.setRecordTime(rs.getTimestamp("recordTime"));
				recordList[i] = rtRecord;
			}
			break;
		case 5: // 마감 이력
			query += "ClosingRecord WHERE recordTime BETWEEN '"
					+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(term1)+"' AND '"
					+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(term2)+"'";
			rs = dm.doWithResult(query);
			rs.last();
			recordList = new ClosingRecord[rs.getRow()]; // resultSet크기 만큼의 recordList 생성
			rs.beforeFirst();
			ClosingRecord csRecord;
			for(int i=0; rs.next(); i++) {
				csRecord = new ClosingRecord(
						rs.getInt("managementID"),
						rs.getDate("date"),
						rs.getInt("salesPerDay"),
						rs.getInt("balance"));
				csRecord.setRecordTime(rs.getTimestamp("recordTime"));
				recordList[i] = csRecord;
			}
			break;
		}
		
		dm.closeDB();
		return recordList;
	}
}
